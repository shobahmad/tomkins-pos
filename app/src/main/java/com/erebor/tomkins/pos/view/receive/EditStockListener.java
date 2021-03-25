package com.erebor.tomkins.pos.view.receive;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;

public interface EditStockListener {
    void onEditStock(TrxTerimaStockModel trxTerimaStockModel, int qty);
}
