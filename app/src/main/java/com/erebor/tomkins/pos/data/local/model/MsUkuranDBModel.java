package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "MSUKURAN",
primaryKeys = {"KodeGender", "Ukuran"})
public class MsUkuranDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [MSUKURAN](
  [KodeGender] VARCHAR(3) NOT NULL REFERENCES "MSGENDER"([KodeGender]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5) NOT NULL,
  [PanjangCM] VARCHAR(10),
  PRIMARY KEY([KodeGender], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @SerializedName("KodeGender")
    @Expose
    @ColumnInfo(name = "KodeGender")
    private String kodeGender;

    @NonNull
    @SerializedName("Ukuran")
    @Expose
    @ColumnInfo(name = "Ukuran")
    private String ukuran;

    @SerializedName("PanjangCM")
    @Expose
    @ColumnInfo(name = "PanjangCM")
    private String panjangCM;

    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

    @NonNull
    public String getKodeGender() {
        return kodeGender;
    }

    public void setKodeGender(@NonNull String kodeGender) {
        this.kodeGender = kodeGender;
    }

    @NonNull
    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(@NonNull String ukuran) {
        this.ukuran = ukuran;
    }

    public String getPanjangCM() {
        return panjangCM;
    }

    public void setPanjangCM(String panjangCM) {
        this.panjangCM = panjangCM;
    }

    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
