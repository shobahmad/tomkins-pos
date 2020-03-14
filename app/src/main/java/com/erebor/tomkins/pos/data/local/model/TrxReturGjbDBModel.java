//package com.erebor.tomkins.pos.data.local.model;
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import java.sql.Date;
//
//@Entity(tableName = "TRXRETURGBJ")
//public class TrxReturGjbDBModel {
//    /*
//    CREATE TABLE [TRXRETURGBJ](
//  [KodeReturGBJ] VARCHAR(20) PRIMARY KEY ON CONFLICT ROLLBACK,
//  [TglReturGBJ] DATE,
//  [StatusRetur] SMALLINT,
//  [Catatan] TEXT)
//     */
//
//    @NonNull
//    @PrimaryKey
//    @ColumnInfo(name = "KodeReturGBJ")
//    private String kodeReturGBJ;
//
//    @ColumnInfo(name = "TglReturGBJ")
//    private Date TglReturGBJ;
//
//    @ColumnInfo(name = "StatusRetur")
//    private Integer statusRetur;
//
//    @ColumnInfo(name = "catatan")
//    private String Catatan;
//
//    @NonNull
//    public String getKodeReturGBJ() {
//        return kodeReturGBJ;
//    }
//
//    public void setKodeReturGBJ(@NonNull String kodeReturGBJ) {
//        this.kodeReturGBJ = kodeReturGBJ;
//    }
//
//    public Date getTglReturGBJ() {
//        return TglReturGBJ;
//    }
//
//    public void setTglReturGBJ(Date tglReturGBJ) {
//        TglReturGBJ = tglReturGBJ;
//    }
//
//    public Integer getStatusRetur() {
//        return statusRetur;
//    }
//
//    public void setStatusRetur(Integer statusRetur) {
//        this.statusRetur = statusRetur;
//    }
//
//    public String getCatatan() {
//        return Catatan;
//    }
//
//    public void setCatatan(String catatan) {
//        Catatan = catatan;
//    }
//}
