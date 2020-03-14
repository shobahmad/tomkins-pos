package com.erebor.tomkins.pos.base;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BaseDao<D extends BaseDatabaseModel> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertReplaceSync(D data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAllReplaceSync(List<D> listData);

    @Update
    Single<Integer> update(D data);

    @Delete
    Single<Integer> delete(D data);
}
