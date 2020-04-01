package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.TrxJualDetDBModel;

import java.util.List;

@Dao
public interface TrxJualDetDao extends BaseDao<TrxJualDetDBModel> {

    @Query("SELECT * FROM TRXJUALDET WHERE NoBon =:noBon")
    List<TrxJualDetDBModel> getListByNoBon(String noBon);

    @Query("DELETE FROM TRXJUALDET")
    void truncate();
}
