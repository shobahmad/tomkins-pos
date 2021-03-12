package com.erebor.tomkins.pos.repository.local;

import com.erebor.tomkins.pos.data.local.model.StockReportModel;

import java.util.List;

public interface StockReportLocalRepository {

    public List<StockReportModel> getStockLatest();
    public List<StockReportModel> getStock(String kodeArt, String ukuran, String gender);
}
