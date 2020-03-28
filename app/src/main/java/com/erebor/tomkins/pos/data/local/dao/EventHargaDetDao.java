package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.EventHargaDetDBModel;

import io.reactivex.Flowable;

@Dao
public interface EventHargaDetDao extends BaseDao<EventHargaDetDBModel> {

    @Query("SELECT * FROM EVENTHARGADET ORDER BY last_update DESC LIMIT 1")
    EventHargaDetDBModel getSyncLatest();

    @Query("SELECT * FROM EVENTHARGADET ORDER BY last_update DESC LIMIT 1")
    Flowable<EventHargaDetDBModel> getLatest();
}
