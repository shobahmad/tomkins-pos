package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.UserDBModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao extends BaseDao<UserDBModel> {

    @Override
    @Query("SELECT * FROM user ORDER BY LOWER(email)")
    Flowable<List<UserDBModel>> getAllData();

    @Override
    @Query("SELECT * FROM user WHERE id=:value")
    Flowable<UserDBModel> getByPrimaryKey(String value);
}
