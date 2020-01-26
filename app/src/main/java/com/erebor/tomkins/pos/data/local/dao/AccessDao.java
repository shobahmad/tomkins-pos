package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.AccessDBModel;
import com.erebor.tomkins.pos.data.local.model.UserDBModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface AccessDao extends BaseDao<UserDBModel> {

    @Query("SELECT * FROM access ORDER BY user_id")
    Flowable<List<AccessDBModel>> getAllData();

    @Query("SELECT * FROM access WHERE id=:value")
    Single<AccessDBModel> getByPrimaryKey(String value);

    @Query("SELECT * FROM access WHERE user_id=:value")
    Single<AccessDBModel> getByUserId(String value);
}
