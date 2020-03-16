package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "MSBRAND")
public class MsBrandDBModel implements BaseDatabaseModel {
    @NonNull
    @PrimaryKey
    @SerializedName("KodeBrand")
    @Expose
    @ColumnInfo(name = "KodeBrand")
    private String kodeBrand;

    @NonNull
    @SerializedName("NamaBrand")
    @Expose
    @ColumnInfo(name = "NamaBrand")
    private String namaBrand;

    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

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

    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
