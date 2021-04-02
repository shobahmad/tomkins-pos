package com.erebor.tomkins.pos.repository.local;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.data.ui.ProductReceiveSummaryUiModel;

import java.util.Date;
import java.util.List;

public interface TrxTerimaLocalRepository {

    List<TrxTerimaDBModel> getAllTrxTerima();

    int getSyncUnuploadedCount();
    TrxTerimaDBModel getSyncFirstQueue();
    void uploaded(TrxTerimaDBModel trxTerimaDBModel);

    int getIncompleteTrxTerima();

    Date getLastUpdate();

    boolean saveTrxTerima(TrxTerimaDBModel trxTerimaDBModel);

    List<TrxTerimaStockModel> getTrxTerimaStock(String noDo);

    ProductReceiveSummaryUiModel getTrxTerimaSummary(String noDo);

    List<TrxTerimaStockModel> searchTrxTerimaStock(String noDo, String query);

    void update(String noDo, String kodeArt, String ukuran, int qty);
    void updateDateAndNote(String noDo, Date date, String note);
}
