package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "TRXOPNAME")
public class TrxOpnameDBModel {
    /*
    CREATE TABLE [TRXOPNAME](
  [KodeOpname] VARCHAR(20) PRIMARY KEY ON CONFLICT ROLLBACK,
  [TglOpname] DATE,
  [StatusOpname] SMALLINT)
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeOpname")
    private String kodeOpname;

    @ColumnInfo(name = "TglOpname")
    private Date TglOpname;

    @ColumnInfo(name = "StatusOpname")
    private Integer statusOpname;

    @NonNull
    public String getKodeOpname() {
        return kodeOpname;
    }

    public void setKodeOpname(@NonNull String kodeOpname) {
        this.kodeOpname = kodeOpname;
    }

    public Date getTglOpname() {
        return TglOpname;
    }

    public void setTglOpname(Date tglOpname) {
        TglOpname = tglOpname;
    }

    public Integer getStatusOpname() {
        return statusOpname;
    }

    public void setStatusOpname(Integer statusOpname) {
        this.statusOpname = statusOpname;
    }
}
