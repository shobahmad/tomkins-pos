package com.erebor.tomkins.pos.repository.local.impl;

import com.erebor.tomkins.pos.data.local.dao.StockReportDao;
import com.erebor.tomkins.pos.data.local.model.StockReportModel;
import com.erebor.tomkins.pos.repository.local.StockReportLocalRepository;

import java.util.List;

import javax.inject.Inject;

public class StockReportLocalRepositoryImpl implements StockReportLocalRepository {
    private final StockReportDao stockReportDao;
    public StockReportLocalRepositoryImpl(StockReportDao stockReportDao) {
        this.stockReportDao = stockReportDao;
    }

    @Override
    public List<StockReportModel> getStockLatest() {
        return stockReportDao.getLatestStock();
    }

    @Override
    public List<StockReportModel> getStock(String kodeArt, String ukuran, String gender) {
        /*
         art       ukuran       gender
         0          0           0           getLatestStock
         0          0           1           getStockParamGender
         0          1           0           getStockParamSize
         0          1           1           getStockParamSizeAndGender
         1          0           0           getStockParamKodeArt
         1          0           1           getStockParamArtAndGender
         1          1           0           getStockParamArtAndSize
         1          1           1           getStockParamAll
         */
        if (kodeArt == null && ukuran == null && gender == null) {
            return stockReportDao.getLatestStock();
        }
        if (kodeArt == null && ukuran == null && gender != null) {
            return stockReportDao.getStockParamGender(gender);
        }
        if (kodeArt == null && ukuran != null && gender == null) {
            return stockReportDao.getStockParamSize(ukuran);
        }
        if (kodeArt == null && ukuran != null && gender != null) {
            return stockReportDao.getStockParamSizeAndGender(ukuran, gender);
        }
        if (kodeArt != null && ukuran == null && gender == null) {
            return stockReportDao.getStockParamKodeArt("%"+kodeArt+"%");
        }
        if (kodeArt != null && ukuran == null && gender != null) {
            return stockReportDao.getStockParamArtAndGender("%"+kodeArt+"%", gender);
        }
        if (kodeArt != null && ukuran != null && gender == null) {
            return stockReportDao.getStockParamArtAndSize("%"+kodeArt+"%", ukuran);
        }
        if (kodeArt != null && ukuran != null && gender != null) {
            return stockReportDao.getStockParamAll("%"+kodeArt+"%", ukuran, gender);
        }

        return stockReportDao.getLatestStock();
    }
}
