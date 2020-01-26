package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "MSKONTER")
public class MsKonterDBModel {
    /*
    CREATE TABLE [MSKONTER](
  [KodeCounter] VARCHAR(10) PRIMARY KEY NOT NULL,
  [NamaKonter] VARCHAR(40),
  [AlamatKonter] VARCHAR(60),
  [Email] VARCHAR(60),
  [NoHPGateway] VARCHAR(20),
  [KodeDeptStore] VARCHAR(2) NOT NULL REFERENCES [MSDEPTSTORE]([KodeDeptStore]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [KodeLogin] VARCHAR(20),
  [PwdLogin] VARCHAR(20),
  [SysAktif] SMALLINT,
  [IDCounter] VARCHAR(4))
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeCounter")
    private String kodeCounter;

    @ColumnInfo(name = "NamaKonter")
    private String namaKonter;

    @ColumnInfo(name = "AlamatKonter")
    private String alamatKonter;

    @ColumnInfo(name = "Email")
    private String email;

    @ColumnInfo(name = "NoHPGateway")
    private String noHPGateway;

    @NonNull
    @ForeignKey(
            entity = MsDepStoreDBModel.class,
            parentColumns = "KodeDeptStore",
            childColumns = "KodeDeptStore",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    @ColumnInfo(name = "KodeDeptStore")
    private String kodeDeptStore;

    @ColumnInfo(name = "KodeLogin")
    private String kodeLogin;

    @ColumnInfo(name = "PwdLogin")
    private String pwdLogin;

    @ColumnInfo(name = "SysAktif")
    private Integer sysAktif;

    @ColumnInfo(name = "IDCounter")
    private String iDCounter;

    @NonNull
    public String getKodeCounter() {
        return kodeCounter;
    }

    public void setKodeCounter(@NonNull String kodeCounter) {
        this.kodeCounter = kodeCounter;
    }

    public String getNamaKonter() {
        return namaKonter;
    }

    public void setNamaKonter(String namaKonter) {
        this.namaKonter = namaKonter;
    }

    public String getAlamatKonter() {
        return alamatKonter;
    }

    public void setAlamatKonter(String alamatKonter) {
        this.alamatKonter = alamatKonter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHPGateway() {
        return noHPGateway;
    }

    public void setNoHPGateway(String noHPGateway) {
        this.noHPGateway = noHPGateway;
    }

    @NonNull
    public String getKodeDeptStore() {
        return kodeDeptStore;
    }

    public void setKodeDeptStore(@NonNull String kodeDeptStore) {
        this.kodeDeptStore = kodeDeptStore;
    }

    public String getKodeLogin() {
        return kodeLogin;
    }

    public void setKodeLogin(String kodeLogin) {
        this.kodeLogin = kodeLogin;
    }

    public String getPwdLogin() {
        return pwdLogin;
    }

    public void setPwdLogin(String pwdLogin) {
        this.pwdLogin = pwdLogin;
    }

    public Integer getSysAktif() {
        return sysAktif;
    }

    public void setSysAktif(Integer sysAktif) {
        this.sysAktif = sysAktif;
    }

    public String getiDCounter() {
        return iDCounter;
    }

    public void setiDCounter(String iDCounter) {
        this.iDCounter = iDCounter;
    }
}
