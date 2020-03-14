//package com.erebor.tomkins.pos.data.local.model;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//
//import static androidx.room.ForeignKey.RESTRICT;
//
//@Entity(tableName = "EVENTFREEDET",
//        primaryKeys = {"KodeEvent", "KodeArt"},
//        foreignKeys = {
//                @ForeignKey(
//                        entity = EventFreeDBModel.class,
//                        parentColumns = "KodeEvent",
//                        childColumns = "KodeEvent",
//                        onDelete = RESTRICT,
//                        onUpdate = RESTRICT),
//                @ForeignKey(
//                        entity = MsArtDBModel.class,
//                        parentColumns = "KodeArt",
//                        childColumns = "KodeArt",
//                        onDelete = RESTRICT,
//                        onUpdate = RESTRICT)
//        }
//)
//public class EventFreeDetDBModel {
//    /*
//    CREATE TABLE [EVENTFREEDET](
//  [KodeEvent] VARCHAR(10) REFERENCES [EVENTFREE]([KodeEvent]) ON DELETE RESTRICT ON UPDATE RESTRICT,
//  [KodeArt] VARCHAR(20) NOT NULL REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
//  [HargaKhusus] MONEY DEFAULT 0,
//  [Diskon] FLOAT DEFAULT 0,
//  PRIMARY KEY([KodeEvent], [KodeArt]) ON CONFLICT ROLLBACK)
//     */
//    @NonNull
//    @ColumnInfo(name = "KodeEvent")
//    private String kodeEvent;
//
//    @NonNull
//    @ColumnInfo(name = "KodeArt")
//    private String kodeArt;
//
//    @ColumnInfo(name = "HargaKhusus", defaultValue = "0")
//    @Nullable
//    private double HargaKhusus;
//
//    @ColumnInfo(name = "Diskon", defaultValue = "0")
//    @Nullable
//    private double diskon;
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
//    public String getKodeArt() {
//        return kodeArt;
//    }
//
//    public void setKodeArt(@NonNull String kodeArt) {
//        this.kodeArt = kodeArt;
//    }
//
//    public double getHargaKhusus() {
//        return HargaKhusus;
//    }
//
//    public void setHargaKhusus(double hargaKhusus) {
//        HargaKhusus = hargaKhusus;
//    }
//
//    public double getDiskon() {
//        return diskon;
//    }
//
//    public void setDiskon(double diskon) {
//        this.diskon = diskon;
//    }
//}
