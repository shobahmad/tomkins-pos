package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.data.local.model.EventDiscountModel;
import com.erebor.tomkins.pos.data.local.model.StockReportModel;

import java.util.List;

@Dao
public interface StockReportDao {
    /*
     art       ukuran       gender
     0          0           1           getStockParamGender
     0          0           0           getLatestStock
     0          1           1           getStockParamSizeAndGender
     0          1           0           getStockParamSize
     1          0           1           getStockParamArtAndGender
     1          0           0           getStockParamKodeArt
     1          1           1           getStockParamAll
     1          1           0           getStockParamArtAndSize
     */

    @Query("SELECT * FROM StockReportModel ORDER BY lastUpdate LIMIT 50")
    List<StockReportModel> getLatestStock();

    @Query("SELECT * FROM StockReportModel WHERE kodeArt = :kodeArt")
    List<StockReportModel> getStockParamKodeArt(String kodeArt);

    @Query("SELECT * FROM StockReportModel WHERE ukuran = :ukuran")
    List<StockReportModel> getStockParamSize(String ukuran);

    @Query("SELECT * FROM StockReportModel WHERE gender = :kodeGender")
    List<StockReportModel> getStockParamGender(String kodeGender);

    @Query("SELECT * FROM StockReportModel WHERE ukuran = :ukuran AND gender = :kodeGender")
    List<StockReportModel> getStockParamSizeAndGender(String ukuran, String kodeGender);

    @Query("SELECT * FROM StockReportModel WHERE kodeArt = :kodeArt AND gender = :kodeGender")
    List<StockReportModel> getStockParamArtAndGender(String kodeArt, String kodeGender);

    @Query("SELECT * FROM StockReportModel WHERE kodeArt = :kodeArt AND ukuran = :ukuran")
    List<StockReportModel> getStockParamArtAndSize(String kodeArt, String ukuran);

    @Query("SELECT * FROM StockReportModel WHERE kodeArt = :kodeArt AND ukuran = :ukuran AND gender = :kodeGender")
    List<StockReportModel> getStockParamAll(String kodeArt, String ukuran, String kodeGender);



}
