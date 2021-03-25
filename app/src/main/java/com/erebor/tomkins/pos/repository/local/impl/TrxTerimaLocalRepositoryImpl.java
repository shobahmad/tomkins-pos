package com.erebor.tomkins.pos.repository.local.impl;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaStockDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

public class TrxTerimaLocalRepositoryImpl implements TrxTerimaLocalRepository {
    private final TomkinsDatabase tomkinsDatabase;
    private final TrxTerimaDao trxTerimaDao;
    private final TrxTerimaDetDao trxTerimaDetDao;
    private final TrxTerimaStockDao trxTerimaStockDao;
    private final StokRealDao stokRealDao;


    public TrxTerimaLocalRepositoryImpl(TomkinsDatabase tomkinsDatabase,
                                        TrxTerimaDao trxTerimaDao, TrxTerimaDetDao trxTerimaDetDao,
                                        TrxTerimaStockDao trxTerimaStockDao, StokRealDao stokRealDao) {
        this.tomkinsDatabase = tomkinsDatabase;
        this.trxTerimaDao = trxTerimaDao;
        this.trxTerimaDetDao = trxTerimaDetDao;
        this.trxTerimaStockDao = trxTerimaStockDao;
        this.stokRealDao = stokRealDao;
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
    public int getIncompleteTrxTerima() {
        return trxTerimaDao.getAllIncomplete().size();
    }

    @Override
    public Date getLastUpdate() {
        TrxTerimaDBModel trxTerimaDBModel = trxTerimaDao.getLatest();
        if (trxTerimaDBModel == null) {
            return null;
        }
        return trxTerimaDBModel.getLastUpdate();
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
            stokRealDBModel.setLastUpdate(Calendar.getInstance().getTime());
            stokRealDBModel.setUploaded(false);
            stokRealDao.insertReplaceSync(stokRealDBModel);



            trxTerimaDBModel.setUploaded(false);
            trxTerimaDBModel.setLastUpdate(Calendar.getInstance(Locale.getDefault()).getTime());
            trxTerimaDBModel.setStatusDO(complete ? 1 : 0);
            trxTerimaDao.update(trxTerimaDBModel);
            return true;
        });
    }


}
