package com.erebor.tomkins.pos.repository.local;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;

import java.util.Date;
import java.util.List;

public interface TrxTerimaLocalRepository {

    List<TrxTerimaDBModel> getAllTrxTerima();

    int getIncompleteTrxTerima();

    Date getLastUpdate();

    boolean saveTrxTerima(TrxTerimaDBModel trxTerimaDBModel);

    List<TrxTerimaStockModel> getTrxTerimaStock(String noDo);

    List<TrxTerimaStockModel> searchTrxTerimaStock(String noDo, String query);

    void update(String noDo, String kodeArt, String ukuran, int qty);
}
