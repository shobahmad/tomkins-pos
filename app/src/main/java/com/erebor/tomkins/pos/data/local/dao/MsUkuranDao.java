package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.MsUkuranDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MsUkuranDao extends BaseDao<MsUkuranDBModel> {

    @Query("SELECT * FROM MSUKURAN ORDER BY KodeGender")
    Flowable<List<MsUkuranDBModel>> getListAll();

    @Query("SELECT * FROM MSUKURAN WHERE KodeGender=:value")
    MsUkuranDBModel getByKodeGender(String value);

    @Query("SELECT * FROM MSUKURAN ORDER BY last_update DESC LIMIT 1")
    MsUkuranDBModel getSyncLatest();

    @Query("SELECT * FROM MSUKURAN ORDER BY last_update DESC LIMIT 1")
    Flowable<MsUkuranDBModel> getLatest();

    @Query("DELETE FROM MSUKURAN")
    void truncate();
}