package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "TRXOPNAMEDET",
primaryKeys = {"KodeOpname", "KodeArt", "Ukuran"})
public class TrxOpnameDetDBModel {
    /*
    CREATE TABLE [TRXOPNAMEDET](
  [KodeOpname] VARCHAR(20) REFERENCES [TRXOPNAME]([KodeOpname]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [KodeArt] VARCHAR(20) REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5),
  [QtySistem] INTEGER DEFAULT 0,
  [QtyOpname] INTEGER DEFAULT 0,
  [QtySelisih] INTEGER DEFAULT 0,
  PRIMARY KEY([KodeOpname], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "KodeOpname")
    @ForeignKey(
            entity = TrxOpnameDBModel.class,
            parentColumns = "KodeOpname",
            childColumns = "KodeOpname",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    private String kodeOpname;

    @NonNull
    @ColumnInfo(name = "KodeArt")
    @ForeignKey(
            entity = MsArtDBModel.class,
            parentColumns = "KodeArt",
            childColumns = "KodeArt",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    private String ukuran;

    @ColumnInfo(name = "QtySistem", defaultValue = "0")
    private Integer qtySistem;

    @ColumnInfo(name = "QtyOpname", defaultValue = "0")
    private Integer qtyOpname;

    @ColumnInfo(name = "QtySelisih", defaultValue = "0")
    private Integer qtySelisih;

    @NonNull
    public String getKodeOpname() {
        return kodeOpname;
    }

    public void setKodeOpname(@NonNull String kodeOpname) {
        this.kodeOpname = kodeOpname;
    }

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public Integer getQtySistem() {
        return qtySistem;
    }

    public void setQtySistem(Integer qtySistem) {
        this.qtySistem = qtySistem;
    }

    public Integer getQtyOpname() {
        return qtyOpname;
    }

    public void setQtyOpname(Integer qtyOpname) {
        this.qtyOpname = qtyOpname;
    }

    public Integer getQtySelisih() {
        return qtySelisih;
    }

    public void setQtySelisih(Integer qtySelisih) {
        this.qtySelisih = qtySelisih;
    }
}
