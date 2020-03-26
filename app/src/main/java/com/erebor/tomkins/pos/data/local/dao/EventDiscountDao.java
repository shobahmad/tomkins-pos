package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.DownloadLastUpdateModel;
import com.erebor.tomkins.pos.data.local.model.EventDiscountModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface EventDiscountDao {
    @Query("SELECT * FROM EventDiscountModel WHERE kodeArt = :artCode")
    List<EventDiscountModel> getPrice(String artCode);
}
