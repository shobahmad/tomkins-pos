package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;

import java.util.List;

@Dao
public interface TrxTerimaDao extends BaseDao<TrxTerimaDBModel> {

    @Query("SELECT * FROM TRXTERIMA ORDER BY TglKirimGBJ desc")
    List<TrxTerimaDBModel> getAll();

    @Query("SELECT * FROM TRXTERIMA WHERE StatusDO = 0 ORDER BY TglKirimGBJ desc")
    List<TrxTerimaDBModel> getAllIncomplete();

    @Query("SELECT * FROM TRXTERIMA ORDER BY TglKirimGBJ desc limit 1")
    TrxTerimaDBModel getLatest();

    @Query("DELETE FROM TRXTERIMA")
    void truncate();

    @Query("SELECT * FROM TRXTERIMA WHERE NoDO = :noDo")
    TrxTerimaDBModel getByNoDo(String noDo);

    @Query("SELECT COUNT(*) FROM TRXTERIMA WHERE isUploaded = 0")
    Integer getSyncUnuploadedCount();


    @Query("SELECT * FROM TRXTERIMA WHERE isUploaded = 0 order by lastUpdate asc limit 1")
    TrxTerimaDBModel getSyncFirstQueue();
}
