package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "MSART",
        foreignKeys = {
            @ForeignKey(
                    entity = MsBrandDBModel.class,
                    parentColumns = "KodeBrand",
                    childColumns = "KodeBrand"
            ),
            @ForeignKey(
                    entity = MsGenderDBModel.class,
                    parentColumns = "KodeGender",
                    childColumns = "KodeGender",
                    onDelete = RESTRICT,
                    onUpdate = RESTRICT
            )
        },
        indices = {
                @Index(name = "ind_NamaArt", value = {"NamaArt"}),
        }
)
public class MsArtDBModel {
    /*
    CREATE TABLE [MSART](
  [KodeArt] VARCHAR(20) PRIMARY KEY ASC NOT NULL ON CONFLICT ABORT UNIQUE ON CONFLICT ABORT,
  [NamaArt] VARCHAR(40) NOT NULL,
  [Warna] VARCHAR(40) NOT NULL,
  [KodeBrand] VARCHAR(2) NOT NULL REFERENCES [MSBRAND]([KodeBrand]),
  [KodeGender] VARCHAR(3) NOT NULL REFERENCES [MSGENDER]([KodeGender]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Harga] MONEY)
     */

    /*
    CREATE INDEX [ind_NamaArt] ON [MSART]([NamaArt] ASC)
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeArt")
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "NamaArt")
    private String namaArt;

    @NonNull
    @ColumnInfo(name = "Warna")
    private String warna;

    @NonNull
    @ColumnInfo(name = "KodeBrand")
    private String kodeBrand;

    @NonNull
    @ColumnInfo(name = "KodeGender")
    private String kodeGender;

    @Nullable
    @ColumnInfo(name = "Harga")
    private double harga;

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }

    @NonNull
    public String getNamaArt() {
        return namaArt;
    }

    public void setNamaArt(@NonNull String namaArt) {
        this.namaArt = namaArt;
    }

    @NonNull
    public String getWarna() {
        return warna;
    }

    public void setWarna(@NonNull String warna) {
        this.warna = warna;
    }

    @NonNull
    public String getKodeBrand() {
        return kodeBrand;
    }

    public void setKodeBrand(@NonNull String kodeBrand) {
        this.kodeBrand = kodeBrand;
    }

    @NonNull
    public String getKodeGender() {
        return kodeGender;
    }

    public void setKodeGender(@NonNull String kodeGender) {
        this.kodeGender = kodeGender;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
