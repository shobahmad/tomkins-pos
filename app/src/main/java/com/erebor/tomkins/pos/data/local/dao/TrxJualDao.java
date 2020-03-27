package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.TrxJualDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TrxJualDao extends BaseDao<TrxJualDBModel> {

    @Query("SELECT COUNT(*) FROM TRXJUAL WHERE isUploaded = 0")
    Flowable<Integer> getUnuploadedCount();

    @Query("SELECT COUNT(*) FROM TRXJUAL WHERE isUploaded = 0")
    Integer getSyncUnuploadedCount();

    @Query("SELECT * FROM TRXJUAL WHERE isUploaded = 0 order by Tanggal asc limit 1")
    TrxJualDBModel getSyncFirstQueue();

    @Query("SELECT * FROM TRXJUAL WHERE isUploaded = 0")
    List<TrxJualDBModel> getUnsyncList();
}
