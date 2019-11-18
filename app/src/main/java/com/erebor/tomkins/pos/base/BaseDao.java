package com.erebor.tomkins.pos.base;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BaseDao<D extends BaseDatabaseModel> {

    Flowable<List<D>> getAllData();
    Flowable<D> getByPrimaryKey(String value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertReplace(D data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertAllReplace(List<D> listData);

    @Update
    Single<Integer> update(D data);

    @Delete
    Single<Integer> delete(D data);
}
