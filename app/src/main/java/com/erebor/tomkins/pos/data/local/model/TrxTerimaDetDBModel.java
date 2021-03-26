package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "TRXTERIMADET",
primaryKeys = {"NoDO", "KodeArt", "Ukuran"})
public class TrxTerimaDetDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [TRXTERIMADET](
  [NoDO] VARCHAR(20) NOT NULL,
  [KodeArt] VARCHAR(20) NOT NULL,
  [Ukuran] VARCHAR(5),
  [QtyDO] INTEGER,
  [QtyTerima] INTEGER,
  PRIMARY KEY([NoDO], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "NoDO")
    @SerializedName("NoDO")
    @Expose
    private String noDO;

    @NonNull
    @ColumnInfo(name = "KodeArt")
    @SerializedName("KodeArt")
    @Expose
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    @SerializedName("Ukuran")
    @Expose
    private String ukuran;

    @ColumnInfo(name = "QtyDO")
    @SerializedName("QtyDO")
    @Expose
    private Integer qtyDO;

    @ColumnInfo(name = "QtyTerima")
    @SerializedName("QtyTerima")
    @Expose
    private Integer qtyTerima;


    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

    @NonNull
    public String getNoDO() {
        return noDO;
    }

    public void setNoDO(@NonNull String noDO) {
        this.noDO = noDO;
    }

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }

    @NonNull
    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(@NonNull String ukuran) {
        this.ukuran = ukuran;
    }

    public Integer getQtyDO() {
        return qtyDO;
    }

    public void setQtyDO(Integer qtyDO) {
        this.qtyDO = qtyDO;
    }

    public Integer getQtyTerima() {
        return qtyTerima;
    }

    public void setQtyTerima(Integer qtyTerima) {
        this.qtyTerima = qtyTerima;
    }

    @Override
    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
