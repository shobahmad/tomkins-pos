//package com.erebor.tomkins.pos.data.local.model;
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//
//import static androidx.room.ForeignKey.RESTRICT;
//
//@Entity(tableName = "TRXRETURGBJDET",
//primaryKeys = {"KodeReturGBJ", "KodeArt", "Ukuran"})
//public class TrxReturGjbDetDBModel {
//    /*
//    CREATE TABLE [TRXRETURGBJDET](
//  [KodeReturGBJ] VARCHAR(20) REFERENCES [TRXRETURGBJ]([KodeReturGBJ]) ON DELETE RESTRICT ON UPDATE RESTRICT,
//  [KodeArt] VARCHAR(20) REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
//  [Ukuran] VARCHAR(5),
//  [Qty] INTEGER,
//  PRIMARY KEY([KodeReturGBJ], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
//     */
//
//    @NonNull
//    @ForeignKey(
//            entity = TrxReturGjbDBModel.class,
//            parentColumns = "KodeReturGBJ",
//            childColumns = "KodeReturGBJ",
//            onDelete = RESTRICT,
//            onUpdate = RESTRICT
//    )
//    @ColumnInfo(name = "KodeReturGBJ")
//    private String kodeReturGBJ;
//
//    @NonNull
//    @ForeignKey(
//            entity = MsArtDBModel.class,
//            parentColumns = "KodeArt",
//            childColumns = "KodeArt",
//            onDelete = RESTRICT,
//            onUpdate = RESTRICT
//    )
//    @ColumnInfo(name = "KodeArt")
//    private String kodeArt;
//
//    @NonNull
//    @ColumnInfo(name = "Ukuran")
//    private String ukuran;
//
//    @ColumnInfo(name = "Qty")
//    private Integer qty;
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
//    @NonNull
//    public String getKodeArt() {
//        return kodeArt;
//    }
//
//    public void setKodeArt(@NonNull String kodeArt) {
//        this.kodeArt = kodeArt;
//    }
//
//    public String getUkuran() {
//        return ukuran;
//    }
//
//    public void setUkuran(String ukuran) {
//        this.ukuran = ukuran;
//    }
//
//    public Integer getQty() {
//        return qty;
//    }
//
//    public void setQty(Integer qty) {
//        this.qty = qty;
//    }
//}
