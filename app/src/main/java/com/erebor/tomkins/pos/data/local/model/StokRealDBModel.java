package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "STOKREAL",
primaryKeys = {"KodeArt", "Ukuran"})
public class StokRealDBModel {
    /*
    CREATE TABLE [STOKREAL](
  [KodeArt] VARCHAR(20) NOT NULL REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5) NOT NULL,
  [QtyStok] INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY([KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

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

    @NonNull
    @ColumnInfo(name = "QtyStok", defaultValue = "0")
    private Integer qtyStok;

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

    @NonNull
    public Integer getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(@NonNull Integer qtyStok) {
        this.qtyStok = qtyStok;
    }
}
