package com.erebor.tomkins.pos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.data.local.model.UserModel;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao extends BaseDao<UserModel> {

    @Override
    @Query("SELECT * FROM user ORDER BY LOWER(email)")
    Flowable<List<UserModel>> getAllData();

    @Override
    @Query("SELECT * FROM user WHERE id=:value")
    Flowable<UserModel> getByPrimaryKey(String value);
}
