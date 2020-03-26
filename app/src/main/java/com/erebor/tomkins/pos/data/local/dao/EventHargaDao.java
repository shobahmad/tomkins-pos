package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.EventHargaDBModel;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface EventHargaDao extends BaseDao<EventHargaDBModel> {

    @Query("SELECT * FROM EVENTHARGA ORDER BY last_update DESC LIMIT 1")
    EventHargaDBModel getSyncLatest();

    @Query("SELECT * FROM EVENTHARGA ORDER BY last_update DESC LIMIT 1")
    Flowable<EventHargaDBModel> getLatest();
}
