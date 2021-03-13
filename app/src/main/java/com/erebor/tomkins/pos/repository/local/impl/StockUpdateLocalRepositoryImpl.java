package com.erebor.tomkins.pos.repository.local.impl;

import android.annotation.SuppressLint;

import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.repository.local.StockUpdateLocalRepository;

import java.util.Calendar;

public class StockUpdateLocalRepositoryImpl implements StockUpdateLocalRepository {

    private static final Object SYNC_STOCK = new Object();
    private final StokRealDao stokRealDao;

    public StockUpdateLocalRepositoryImpl(StokRealDao stokRealDao) {
        this.stokRealDao = stokRealDao;
    }

    @SuppressLint("CheckResult")
    @Override
    public void addStock(String kodeArt, String size, int qty) {
        synchronized (SYNC_STOCK) {
            StokRealDBModel stokRealDBModel = stokRealDao.getByKodeArtAndUkuran(kodeArt, size);
            if (stokRealDBModel == null) {
                return;
            }
            stokRealDBModel.setLastUpdate(Calendar.getInstance().getTime());
            stokRealDBModel.setUploaded(false);
            stokRealDBModel.setQtyStok(stokRealDBModel.getQtyStok() + qty);
            stokRealDao.update(stokRealDBModel).blockingGet();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void removeStock(String kodeArt, String size, int qty) {
        synchronized (SYNC_STOCK) {
            StokRealDBModel stokRealDBModel = stokRealDao.getByKodeArtAndUkuran(kodeArt, size);
            if (stokRealDBModel == null) {
                return;
            }
            stokRealDBModel.setLastUpdate(Calendar.getInstance().getTime());
            stokRealDBModel.setUploaded(false);
            stokRealDBModel.setQtyStok(stokRealDBModel.getQtyStok() - qty);
            stokRealDao.update(stokRealDBModel).blockingGet();
        }
    }
}
