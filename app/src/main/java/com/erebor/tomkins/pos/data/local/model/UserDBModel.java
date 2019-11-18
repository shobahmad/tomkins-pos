package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;

@Entity(tableName = "user")
public class UserDBModel extends BaseDatabaseModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "position")
    private String position;

    public UserDBModel(@NonNull Integer id, @NonNull String email) {
        this.id = id;
        this.email = email;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@NonNull String displayName) {
        this.displayName = displayName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(@NonNull String position) {
        this.position = position;
    }

    @Override
    public String getPrimaryKeyValue() {
        return String.valueOf(id);
    }
}
