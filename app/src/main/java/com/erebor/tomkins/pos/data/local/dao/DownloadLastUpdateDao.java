package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.data.local.model.DownloadLastUpdateModel;

import io.reactivex.Flowable;

@Dao
public interface DownloadLastUpdateDao {

    @Query("SELECT * FROM DownloadLastUpdateModel")
    Flowable<DownloadLastUpdateModel> getLatestInfo();
}
