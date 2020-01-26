package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;

import java.util.Date;

@Entity(tableName = "EVENTFREE")
public class EventFreeDBModel implements BaseDatabaseModel {

    /*
    CREATE TABLE [EVENTFREE](
  [KodeEvent] VARCHAR(10) PRIMARY KEY ON CONFLICT ROLLBACK NOT NULL,
  [TglDari] DATE,
  [TglSampai] DATE,
  [JmlFree] INTEGER)
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeEvent")
    private String kodeEvent;

    @Nullable
    @ColumnInfo(name = "TglDari")
    private Date tglDari;

    @Nullable
    @ColumnInfo(name = "TglSampai")
    private Date tglSampai;

    @Nullable
    @ColumnInfo(name = "JmlFree")
    private Integer jmlFree;

    @NonNull
    public String getKodeEvent() {
        return kodeEvent;
    }

    public void setKodeEvent(@NonNull String kodeEvent) {
        this.kodeEvent = kodeEvent;
    }

    public Date getTglDari() {
        return tglDari;
    }

    public void setTglDari(Date tglDari) {
        this.tglDari = tglDari;
    }

    public Date getTglSampai() {
        return tglSampai;
    }

    public void setTglSampai(Date tglSampai) {
        this.tglSampai = tglSampai;
    }

    public Integer getJmlFree() {
        return jmlFree;
    }

    public void setJmlFree(Integer jmlFree) {
        this.jmlFree = jmlFree;
    }
}
