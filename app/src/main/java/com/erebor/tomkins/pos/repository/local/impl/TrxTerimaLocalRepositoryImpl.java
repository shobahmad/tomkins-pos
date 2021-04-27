package com.erebor.tomkins.pos.repository.local.impl;

import android.util.Log;

import com.erebor.tomkins.pos.data.field.DateDelivery;
import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.ArtGradeDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaStockDao;
import com.erebor.tomkins.pos.data.local.model.ArtGradeDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.data.ui.ProductReceiveSummaryUiModel;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrxTerimaLocalRepositoryImpl implements TrxTerimaLocalRepository {
    private final TomkinsDatabase tomkinsDatabase;
    private final TrxTerimaDao trxTerimaDao;
    private final TrxTerimaDetDao trxTerimaDetDao;
    private final TrxTerimaStockDao trxTerimaStockDao;
    private final StokRealDao stokRealDao;
    private final ArtGradeDao artGradeDao;
    private final MsBarcodeDao msBarcodeDao;


    public TrxTerimaLocalRepositoryImpl(TomkinsDatabase tomkinsDatabase,
                                        TrxTerimaDao trxTerimaDao, TrxTerimaDetDao trxTerimaDetDao,
                                        TrxTerimaStockDao trxTerimaStockDao, StokRealDao stokRealDao,
                                        ArtGradeDao artGradeDao, MsBarcodeDao msBarcodeDao) {
        this.tomkinsDatabase = tomkinsDatabase;
        this.trxTerimaDao = trxTerimaDao;
        this.trxTerimaDetDao = trxTerimaDetDao;
        this.trxTerimaStockDao = trxTerimaStockDao;
        this.stokRealDao = stokRealDao;
        this.artGradeDao = artGradeDao;
        this.msBarcodeDao = msBarcodeDao;
    }

    @Override
    public List<TrxTerimaDBModel> getAllTrxTerima() {
        List<TrxTerimaDBModel> trxTerimaDBModels = trxTerimaDao.getAll();
        for (TrxTerimaDBModel trxTerimaDBModel : trxTerimaDBModels) {
            trxTerimaDBModel.setListDetail(trxTerimaDetDao.getByNoDO(trxTerimaDBModel.getNoDO()));
        }
        return trxTerimaDBModels;
    }

    @Override
    public int getSyncUnuploadedCount() {
        return trxTerimaDao.getSyncUnuploadedCount();
    }

    @Override
    public TrxTerimaDBModel getSyncFirstQueue() {
        TrxTerimaDBModel trxTerimaDBModel = trxTerimaDao.getSyncFirstQueue();
        trxTerimaDBModel.setListDetail(trxTerimaDetDao.getByNoDO(trxTerimaDBModel.getNoDO()));
        return trxTerimaDBModel;
    }

    @Override
    public void uploaded(TrxTerimaDBModel trxTerimaDBModel) {
        trxTerimaDBModel.setUploaded(true);
        trxTerimaDBModel.setLastUpdate(Calendar.getInstance().getTime());
        trxTerimaDao.update(trxTerimaDBModel).blockingGet();
    }

    @Override
    public int getIncompleteTrxTerima() {
        return trxTerimaDao.getAllIncomplete().size();
    }

    @Override
    public Date getLastUpdate() {
        TrxTerimaDBModel trxTerimaDBModel = trxTerimaDao.getLatest();
        if (trxTerimaDBModel == null) {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.set(2021, 0, 1);
            return calendar.getTime();
        }
        return trxTerimaDBModel.getTglKirimGBJ();
    }

    @Override
    public boolean saveTrxTerima(TrxTerimaDBModel trxTerimaDBModel) {
        return tomkinsDatabase.runInTransaction(() -> {
            Long insertHeader = trxTerimaDao.insertReplaceSync(trxTerimaDBModel);
            if (insertHeader < 0) {
                return false;
            }
            for (TrxTerimaDetDBModel trxTerimaDetDBModel : trxTerimaDBModel.getListDetail()) {
                if (trxTerimaDetDBModel.getLastUpdate() == null) {
                    trxTerimaDetDBModel.setLastUpdate(Calendar.getInstance(Locale.getDefault()).getTime());
                }
                Long insertDetail = trxTerimaDetDao.insertReplaceSync(trxTerimaDetDBModel);
                if (insertDetail < 0) {
                    return false;
                }
            }
            return true;
        });
    }

    @Override
    public List<TrxTerimaStockModel> getTrxTerimaStock(String noDo) {
        return trxTerimaStockDao.getTrxTerimaStock(noDo);
    }

    @Override
    public ProductReceiveSummaryUiModel getTrxTerimaSummary(String noDo) {
        TrxTerimaDBModel trxTerimaDBModel = trxTerimaDao.getByNoDo(noDo);
        return new ProductReceiveSummaryUiModel(trxTerimaDBModel.getTglTerimaCnt(), trxTerimaDBModel.getCatatan());
    }

    @Override
    public List<TrxTerimaStockModel> searchTrxTerimaStock(String noDo, String query) {
        boolean isBarcode;
        try {
            Double.parseDouble(query.trim());
            isBarcode = true;
        } catch (NumberFormatException nfe) {
            isBarcode = false;
        }

        return isBarcode ? trxTerimaStockDao.getTrxTerimaStockBarcode(noDo, query) :
                trxTerimaStockDao.getTrxTerimaStockArticle(noDo, query);
    }

    @Override
    public void update(String noDo, String kodeArt, String ukuran, int qty) {
        tomkinsDatabase.runInTransaction(() -> {
            TrxTerimaDetDBModel trxTerimaDetDBModel = trxTerimaDetDao.getByKodeArtAndUkuran(noDo, kodeArt, ukuran);
            int qtyBeforeUpdate = trxTerimaDetDBModel.getQtyTerima();
            trxTerimaDetDBModel.setQtyTerima(qty);
            trxTerimaDetDao.update(trxTerimaDetDBModel).blockingGet();


            TrxTerimaDBModel trxTerimaDBModel = trxTerimaDao.getByNoDo(noDo);
            trxTerimaDBModel.setListDetail(trxTerimaDetDao.getByNoDO(trxTerimaDBModel.getNoDO()));
            boolean complete = true;
            for (TrxTerimaDetDBModel terimaDetDBModel : trxTerimaDBModel.getListDetail()) {
                if (terimaDetDBModel.getQtyDO() > terimaDetDBModel.getQtyTerima()) {
                    complete = false;
                    break;
                }
            }

            StokRealDBModel stokRealDBModel = stokRealDao.getByKodeArtAndUkuran(trxTerimaDetDBModel.getKodeArt(), trxTerimaDetDBModel.getUkuran());
            if (stokRealDBModel == null) {
                stokRealDBModel = new StokRealDBModel();
                stokRealDBModel.setLastUpdate(Calendar.getInstance().getTime());
                stokRealDBModel.setUploaded(false);
                stokRealDBModel.setKodeArt(trxTerimaDetDBModel.getKodeArt());
                stokRealDBModel.setUkuran(trxTerimaDetDBModel.getUkuran());
                stokRealDBModel.setQtyStok(0);
            }
            int qtyAdd = qty - qtyBeforeUpdate;
            stokRealDBModel.setQtyStok(stokRealDBModel.getQtyStok() + qtyAdd);
            Date date = Calendar.getInstance().getTime();
            stokRealDBModel.setLastUpdate(date);
            stokRealDBModel.setUploaded(false);
            stokRealDao.insertReplaceSync(stokRealDBModel);



            trxTerimaDBModel.setUploaded(false);
            trxTerimaDBModel.setLastUpdate(date);
            trxTerimaDBModel.setTglTerimaCnt(new DateDelivery(date));
            trxTerimaDBModel.setStatusDO(complete ? 1 : 0);
            trxTerimaDao.update(trxTerimaDBModel).blockingGet();
            return true;
        });
    }

    @Override
    public void updateGrade(String kodeArt, String ukuran, String grade) {
        tomkinsDatabase.runInTransaction(() -> {
            MsBarcodeDBModel msBarcodeDBModel = msBarcodeDao.getByArtUkuran(kodeArt, ukuran);
            if (msBarcodeDBModel == null) {
                throw new Exception("Barcode not found!");
            }
            ArtGradeDBModel artGradeDBModel = new ArtGradeDBModel(msBarcodeDBModel.getNoBarcode(), grade);
            artGradeDao.insertReplaceSync(artGradeDBModel);
            return true;
        });
    }

    @Override
    public void updateDateAndNote(String noDo, Date date, String note) {
        TrxTerimaDBModel trxTerimaDBModel = trxTerimaDao.getByNoDo(noDo);
        trxTerimaDBModel.setUploaded(false);
        trxTerimaDBModel.setLastUpdate(Calendar.getInstance().getTime());
        trxTerimaDBModel.setTglTerimaCnt(new DateDelivery(date));
        trxTerimaDBModel.setCatatan(note);
        trxTerimaDao.update(trxTerimaDBModel).blockingGet();
    }


}
