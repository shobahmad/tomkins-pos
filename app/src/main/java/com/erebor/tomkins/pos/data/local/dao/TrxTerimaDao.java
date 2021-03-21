package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;

import java.util.List;

@Dao
public interface TrxTerimaDao extends BaseDao<TrxTerimaDBModel> {

    @Query("SELECT * FROM TRXTERIMA ORDER BY TglKirimGBJ desc")
    List<TrxTerimaDBModel> getAll();

    @Query("SELECT * FROM TRXTERIMA ORDER BY TglKirimGBJ desc limit 1")
    TrxTerimaDBModel getLatest();

    @Query("DELETE FROM TRXTERIMA")
    void truncate();
}
