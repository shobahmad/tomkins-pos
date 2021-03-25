package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.data.local.model.StockReportModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;

import java.util.List;

@Dao
public interface TrxTerimaStockDao {

    @Query("SELECT * FROM TrxTerimaStockModel WHERE noDo = :noDo ORDER BY lastUpdate DESC")
    List<TrxTerimaStockModel> getTrxTerimaStock(String noDo);


}
