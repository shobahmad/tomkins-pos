package com.erebor.tomkins.pos.view.callback;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;

public interface EditStockListener {
    void onEditStock(TrxTerimaStockModel trxTerimaStockModel, int qty);
    void onEditGrade(TrxTerimaStockModel trxTerimaStockModel, String grade);
}
