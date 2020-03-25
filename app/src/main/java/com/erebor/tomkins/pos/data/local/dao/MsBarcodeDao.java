package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MsBarcodeDao extends BaseDao<MsBarcodeDBModel> {

    @Query("SELECT * FROM MSBARCODE ORDER BY NoBarcode")
    Flowable<List<MsBarcodeDBModel>> getListAll();

    @Query("SELECT * FROM MSBARCODE WHERE NoBarcode=:value")
    MsBarcodeDBModel getSyncByNoBarcode(String value);

    @Query("SELECT * FROM MSBARCODE WHERE NoBarcode=:value")
    Flowable<MsBarcodeDBModel> getByNoBarcode(String value);

    @Query("SELECT * FROM MSBARCODE ORDER BY last_update DESC LIMIT 1")
    MsBarcodeDBModel getSyncLatest();

    @Query("SELECT * FROM MSBARCODE ORDER BY last_update DESC LIMIT 1")
    Flowable<MsBarcodeDBModel> getLatest();
}
