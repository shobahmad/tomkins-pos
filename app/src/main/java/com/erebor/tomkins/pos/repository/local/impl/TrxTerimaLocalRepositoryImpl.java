package com.erebor.tomkins.pos.repository.local.impl;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrxTerimaLocalRepositoryImpl implements TrxTerimaLocalRepository {
    private final TomkinsDatabase tomkinsDatabase;
    private final TrxTerimaDao trxTerimaDao;
    private final TrxTerimaDetDao trxTerimaDetDao;
    private final StokRealDao stokRealDao;


    public TrxTerimaLocalRepositoryImpl(TomkinsDatabase tomkinsDatabase, TrxTerimaDao trxTerimaDao, TrxTerimaDetDao trxTerimaDetDao, StokRealDao stokRealDao) {
        this.tomkinsDatabase = tomkinsDatabase;
        this.trxTerimaDao = trxTerimaDao;
        this.trxTerimaDetDao = trxTerimaDetDao;
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
                Long insertDetail = trxTerimaDetDao.insertReplaceSync(trxTerimaDetDBModel);
                if (insertDetail < 0) {
                    return false;
                }

                if (trxTerimaDetDBModel.getQtyTerima() == 0) {
                    continue;
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
                stokRealDBModel.setQtyStok(stokRealDBModel.getQtyStok() + trxTerimaDetDBModel.getQtyTerima());
                stokRealDao.insertReplaceSync(stokRealDBModel);
            }
            return true;
        });
    }


}
