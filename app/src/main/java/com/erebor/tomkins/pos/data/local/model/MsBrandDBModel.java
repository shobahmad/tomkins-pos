package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MSBRAND")
public class MsBrandDBModel {
    /*
    CREATE TABLE [MSBRAND](
  [KodeBrand] VARCHAR(2) PRIMARY KEY ASC NOT NULL,
  [NamaBrand] VARCHAR(40) NOT NULL)
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeBrand")
    private String kodeBrand;

    @ColumnInfo(name = "NamaBrand")
    private String namaBrand;

    @NonNull
    public String getKodeBrand() {
        return kodeBrand;
    }

    public void setKodeBrand(@NonNull String kodeBrand) {
        this.kodeBrand = kodeBrand;
    }

    public String getNamaBrand() {
        return namaBrand;
    }

    public void setNamaBrand(String namaBrand) {
        this.namaBrand = namaBrand;
    }
}
