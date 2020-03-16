package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.MsBrandDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBrandDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MsBrandDao extends BaseDao<MsBrandDBModel> {

    @Query("SELECT * FROM MSBRAND ORDER BY KodeBrand")
    Flowable<List<MsBrandDBModel>> getListAll();

    @Query("SELECT * FROM MSBRAND WHERE KodeBrand=:value")
    MsBrandDBModel getByKodeBrand(String value);

    @Query("SELECT * FROM MSBRAND ORDER BY last_update DESC LIMIT 1")
    MsBrandDBModel getSyncLatest();

    @Query("SELECT * FROM MSBRAND ORDER BY last_update DESC LIMIT 1")
    Flowable<MsBrandDBModel> getLatest();
}
