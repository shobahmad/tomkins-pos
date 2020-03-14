package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
                @Index(name = "KodeBrand", value = {"KodeBrand"}),
                @Index(name = "KodeGender", value = {"KodeGender"}),
        }
)
public class MsArtDBModel implements BaseDatabaseModel {
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
    @SerializedName("KodeArt")
    @Expose
    @ColumnInfo(name = "KodeArt")
    private String kodeArt;

    @NonNull
    @SerializedName("NamaArt")
    @Expose
    @ColumnInfo(name = "NamaArt")
    private String namaArt;

    @NonNull
    @SerializedName("Warna")
    @Expose
    @ColumnInfo(name = "Warna")
    private String warna;

    @NonNull
    @SerializedName("KodeBrand")
    @Expose
    @ColumnInfo(name = "KodeBrand")
    private String kodeBrand;

    @NonNull
    @SerializedName("KodeGender")
    @Expose
    @ColumnInfo(name = "KodeGender")
    private String kodeGender;

    @Nullable
    @SerializedName("Harga")
    @Expose
    @ColumnInfo(name = "Harga")
    private double harga;

    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

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

    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
