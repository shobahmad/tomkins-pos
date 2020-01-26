package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "TRXJUAL")
public class TrxJualDBModel {
    /*
    CREATE TABLE [TRXJUAL](
  [NoBon] VARCHAR(20) PRIMARY KEY ON CONFLICT ROLLBACK NOT NULL,
  [Tanggal] DATE,
  [KodeSPG] VARCHAR(20))
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "NoBon")
    private String noBon;

    @ColumnInfo(name = "Tanggal")
    private Date tanggal;

    @ColumnInfo(name = "KodeSPG")
    private String kodeSPG;

    @NonNull
    public String getNoBon() {
        return noBon;
    }

    public void setNoBon(@NonNull String noBon) {
        this.noBon = noBon;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKodeSPG() {
        return kodeSPG;
    }

    public void setKodeSPG(String kodeSPG) {
        this.kodeSPG = kodeSPG;
    }
}
