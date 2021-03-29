package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;

import java.util.List;

@Dao
public interface TrxTerimaStockDao {

    @Query("SELECT * FROM TrxTerimaStockModel WHERE noDo = :noDo")
    List<TrxTerimaStockModel> getTrxTerimaStock(String noDo);

    @Query("SELECT * FROM TrxTerimaStockModel WHERE noDo = :noDo and kodeArt = :article")
    List<TrxTerimaStockModel> getTrxTerimaStockArticle(String noDo, String article);

    @Query("SELECT * FROM TrxTerimaStockModel WHERE noDo = :noDo and noBarcode = :barcode")
    List<TrxTerimaStockModel> getTrxTerimaStockBarcode(String noDo, String barcode);


}
