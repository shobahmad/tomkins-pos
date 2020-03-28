package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StokRealDao extends BaseDao<StokRealDBModel> {

    @Query("SELECT * FROM STOKREAL ORDER BY KodeArt")
    Flowable<List<StokRealDBModel>> getListAll();

    @Query("SELECT * FROM STOKREAL WHERE KodeArt=:kodeArt AND Ukuran=:ukuran")
    StokRealDBModel getByKodeArtAndUkuran(String kodeArt, String ukuran);

    @Query("SELECT * FROM STOKREAL ORDER BY last_update DESC LIMIT 1")
    StokRealDBModel getSyncLatest();

    @Query("SELECT * FROM STOKREAL ORDER BY last_update DESC LIMIT 1")
    Flowable<StokRealDBModel> getLatest();
}
