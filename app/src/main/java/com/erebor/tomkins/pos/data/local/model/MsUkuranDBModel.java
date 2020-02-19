package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "MSUKURAN",
primaryKeys = {"KodeGender", "Ukuran"})
public class MsUkuranDBModel {
    /*
    CREATE TABLE [MSUKURAN](
  [KodeGender] VARCHAR(3) NOT NULL REFERENCES "MSGENDER"([KodeGender]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5) NOT NULL,
  [PanjangCM] VARCHAR(10),
  PRIMARY KEY([KodeGender], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "KodeGender")
    @ForeignKey(
            entity = MsGenderDBModel.class,
            parentColumns = "KodeGender",
            childColumns = "KodeGender",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    private String kodeGender;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    private String ukuran;

    @ColumnInfo(name = "PanjangCM")
    private String panjangCM;

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
}
