package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MsArtDao extends BaseDao<MsArtDBModel> {

    @Query("SELECT * FROM MSART ORDER BY KodeArt")
    Flowable<List<MsArtDBModel>> getListAll();

    @Query("SELECT * FROM MSART WHERE KodeArt=:value")
    MsArtDBModel getByKodeArt(String value);

    @Query("SELECT * FROM MSART ORDER BY last_update DESC LIMIT 1")
    MsArtDBModel getSyncLatest();

    @Query("SELECT * FROM MSART ORDER BY last_update DESC LIMIT 1")
    Flowable<MsArtDBModel> getLatest();
}
