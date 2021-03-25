package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;

import java.util.List;

@Dao
public interface TrxTerimaDetDao extends BaseDao<TrxTerimaDetDBModel> {

    @Query("SELECT * FROM TRXTERIMADET WHERE NoDO = :noDO")
    List<TrxTerimaDetDBModel> getByNoDO(String noDO);

    @Query("SELECT * FROM TRXTERIMADET WHERE NoDO = :noDO AND KodeArt = :kodeArt AND Ukuran = :ukuran")
    TrxTerimaDetDBModel getByKodeArtAndUkuran(String noDO, String kodeArt, String ukuran);

    @Query("DELETE FROM TRXTERIMA")
    void truncate();
}
