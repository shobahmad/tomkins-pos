package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;

@Entity(tableName = "access")
public class AccessDBModel implements BaseDatabaseModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "user_id")
    private Integer userId;

    public AccessDBModel(@NonNull Integer id, @NonNull Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    public String getPrimaryKeyValue() {
        return String.valueOf(id);
    }
}
