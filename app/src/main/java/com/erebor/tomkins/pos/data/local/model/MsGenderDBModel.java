package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MSGENDER")
public class MsGenderDBModel {
    /*
    CREATE TABLE "MSGENDER"(
  [KodeGender] VARCHAR(3) PRIMARY KEY ON CONFLICT ROLLBACK NOT NULL UNIQUE,
  [Gender] VARCHAR(20) NOT NULL)
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeGender")
    private String kodeGender;

    @NonNull
    @ColumnInfo(name = "Gender")
    private String gender;

    @NonNull
    public String getKodeGender() {
        return kodeGender;
    }

    public void setKodeGender(@NonNull String kodeGender) {
        this.kodeGender = kodeGender;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }
}
