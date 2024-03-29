package com.erebor.tomkins.pos.data.local.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.erebor.tomkins.pos.data.field.DateDelivery;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@Entity(tableName = "TRXTERIMA")
public class TrxTerimaDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [TRXTERIMA](
  [NoDO] VARCHAR(20) PRIMARY KEY ASC ON CONFLICT ROLLBACK,
  [TglKirimGBJ] DATE,
  [TglTerimaCnt] DATE,
  [Catatan] TEXT,
  [StatusDO] SMALLINT)
     */

    @NonNull
    @PrimaryKey
    @Expose
    @ColumnInfo(name = "NoDO")
    @SerializedName("NoDO")
    private String noDO;

    @Expose
    @ColumnInfo(name = "TglKirimGBJ")
    @SerializedName("TglKirimGBJ")
    private DateDelivery tglKirimGBJ;

    @Expose
    @ColumnInfo(name = "TglTerimaCnt")
    @SerializedName("TglTerimaCnt")
    private DateDelivery tglTerimaCnt;

    @Expose
    @ColumnInfo(name = "Catatan")
    @SerializedName("Catatan")
    private String catatan;

    @Expose
    @ColumnInfo(name = "StatusDO")
    @SerializedName("StatusDO")
    private Integer statusDO;

    @Ignore
    @Expose
    @SerializedName("TRXTERIMADET")
    private List<TrxTerimaDetDBModel> listDetail;

    @Expose
    @ColumnInfo(name = "lastUpdate")
    private Date lastUpdate;

    @NonNull
    @ColumnInfo(name = "isUploaded", defaultValue = "0")
    private Boolean isUploaded;


    @NonNull
    public String getNoDO() {
        return noDO;
    }

    public void setNoDO(@NonNull String noDO) {
        this.noDO = noDO;
    }

    public DateDelivery getTglKirimGBJ() {
        return tglKirimGBJ;
    }

    public void setTglKirimGBJ(DateDelivery tglKirimGBJ) {
        this.tglKirimGBJ = tglKirimGBJ;
    }

    public DateDelivery getTglTerimaCnt() {
        return tglTerimaCnt;
    }

    public void setTglTerimaCnt(DateDelivery tglTerimaCnt) {
        this.tglTerimaCnt = tglTerimaCnt;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public Integer getStatusDO() {
        return statusDO;
    }

    public void setStatusDO(Integer statusDO) {
        this.statusDO = statusDO;
    }

    public List<TrxTerimaDetDBModel> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<TrxTerimaDetDBModel> listDetail) {
        this.listDetail = listDetail;
    }

    @Override
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public Date getLastUpdate() {
        return lastUpdate;
    }

    @NonNull
    public Boolean getUploaded() {
        return isUploaded;
    }

    public void setUploaded(@NonNull Boolean uploaded) {
        isUploaded = uploaded;
    }
}
