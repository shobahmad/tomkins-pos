package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.ArtGradeDBModel;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ArtGradeDao extends BaseDao<ArtGradeDBModel> {



    @Query("SELECT * FROM ART_GRADE")
    List<ArtGradeDBModel> getALl();

    @Query("SELECT * FROM ART_GRADE WHERE Barcode=:barcode")
    ArtGradeDBModel getByBarcode(String barcode);
}
