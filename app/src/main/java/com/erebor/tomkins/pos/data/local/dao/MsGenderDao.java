package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MsGenderDao extends BaseDao<MsGenderDBModel> {

    @Query("SELECT * FROM MSGENDER GROUP BY KodeGender ORDER BY KodeGender")
    List<MsGenderDBModel> getListAll();

    @Query("SELECT * FROM MSGENDER WHERE KodeGender=:value")
    MsGenderDBModel getByKodeGender(String value);

    @Query("SELECT * FROM MSGENDER ORDER BY last_update DESC LIMIT 1")
    MsGenderDBModel getSyncLatest();

    @Query("SELECT * FROM MSGENDER ORDER BY last_update DESC LIMIT 1")
    Flowable<MsGenderDBModel> getLatest();
}
