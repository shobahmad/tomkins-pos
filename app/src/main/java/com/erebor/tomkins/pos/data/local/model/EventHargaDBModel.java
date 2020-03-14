//package com.erebor.tomkins.pos.data.local.model;
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import java.util.Date;
//
//@Entity(tableName = "EVENTHARGA")
//public class EventHargaDBModel {
//    /*
//    CREATE TABLE [EVENTHARGA](
//  [KodeEvent] VARCHAR(10) PRIMARY KEY ASC ON CONFLICT ROLLBACK NOT NULL UNIQUE ON CONFLICT ROLLBACK,
//  [TglDari] DATE NOT NULL,
//  [TglSampai] DATE NOT NULL)
//     */
//
//    @NonNull
//    @PrimaryKey
//    @ColumnInfo(name = "KodeEvent")
//    private String kodeEvent;
//
//    @NonNull
//    @ColumnInfo(name = "TglDari")
//    private Date tglDari;
//
//    @NonNull
//    @ColumnInfo(name = "TglSampai")
//    private Date TglSampai;
//
//    @NonNull
//    public String getKodeEvent() {
//        return kodeEvent;
//    }
//
//    public void setKodeEvent(@NonNull String kodeEvent) {
//        this.kodeEvent = kodeEvent;
//    }
//
//    @NonNull
//    public Date getTglDari() {
//        return tglDari;
//    }
//
//    public void setTglDari(@NonNull Date tglDari) {
//        this.tglDari = tglDari;
//    }
//
//    @NonNull
//    public Date getTglSampai() {
//        return TglSampai;
//    }
//
//    public void setTglSampai(@NonNull Date tglSampai) {
//        TglSampai = tglSampai;
//    }
//}
