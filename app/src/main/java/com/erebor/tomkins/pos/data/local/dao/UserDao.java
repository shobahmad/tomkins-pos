package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.UserDBModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface UserDao extends BaseDao<UserDBModel> {

    @Query("SELECT * FROM user ORDER BY LOWER(email)")
    Flowable<List<UserDBModel>> getAllData();

    @Query("SELECT * FROM user WHERE id=:value")
    Single<UserDBModel> getByPrimaryKey(String value);
}
