package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.data.local.model.EventDiscountModel;

import java.util.List;

@Dao
public interface EventDiscountDao {
    @Query("SELECT * FROM EventDiscountModel WHERE kodeArt = :artCode")
    List<EventDiscountModel> getPrice(String artCode);
}
