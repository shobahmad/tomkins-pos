//package com.erebor.tomkins.pos.data.local.model;
//
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import java.sql.Date;
//
//@Entity(tableName = "TRXTERIMA")
//public class TrxTerimaDBModel {
//    /*
//    CREATE TABLE [TRXTERIMA](
//  [NoDO] VARCHAR(20) PRIMARY KEY ASC ON CONFLICT ROLLBACK,
//  [TglKirimGBJ] DATE,
//  [TglTerimaCnt] DATE,
//  [Catatan] TEXT,
//  [StatusDO] SMALLINT)
//     */
//
//    @NonNull
//    @PrimaryKey
//    @ColumnInfo(name = "NoDO")
//    private String noDO;
//
//    @ColumnInfo(name = "TglKirimGBJ")
//    private Date tglKirimGBJ;
//
//    @ColumnInfo(name = "TglTerimaCnt")
//    private Date tglTerimaCnt;
//
//    @ColumnInfo(name = "Catatan")
//    private String catatan;
//
//    @ColumnInfo(name = "StatusDO")
//    private Integer statusDO;
//
//
//    @NonNull
//    public String getNoDO() {
//        return noDO;
//    }
//
//    public void setNoDO(@NonNull String noDO) {
//        this.noDO = noDO;
//    }
//
//    public Date getTglKirimGBJ() {
//        return tglKirimGBJ;
//    }
//
//    public void setTglKirimGBJ(Date tglKirimGBJ) {
//        this.tglKirimGBJ = tglKirimGBJ;
//    }
//
//    public Date getTglTerimaCnt() {
//        return tglTerimaCnt;
//    }
//
//    public void setTglTerimaCnt(Date tglTerimaCnt) {
//        this.tglTerimaCnt = tglTerimaCnt;
//    }
//
//    public String getCatatan() {
//        return catatan;
//    }
//
//    public void setCatatan(String catatan) {
//        this.catatan = catatan;
//    }
//
//    public Integer getStatusDO() {
//        return statusDO;
//    }
//
//    public void setStatusDO(Integer statusDO) {
//        this.statusDO = statusDO;
//    }
//}
