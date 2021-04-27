package com.erebor.tomkins.pos.data.local.model;

import androidx.room.DatabaseView;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;

import java.util.Date;

@DatabaseView("SELECT STOCK.NoDO noDo, ART.KodeArt kodeArt, ART.NamaArt namaArt, BARCODE.NoBarcode noBarcode, BARCODE.Ukuran ukuran," +
        " GENDER.Gender gender, ART.Warna warna, ART.Harga harga, STOCK.QtyDO qtyKirim, STOCK.QtyTerima qtyTerima, " +
        "STOCK.last_update lastUpdate, IFNULL(ART_GRADE.Grade, 'A') grade " +
        "FROM MSBARCODE BARCODE LEFT JOIN ART_GRADE ON BARCODE.NoBarcode = ART_GRADE.Barcode, MSART ART, TRXTERIMADET STOCK, MSGENDER GENDER " +
        "WHERE BARCODE.Ukuran = STOCK.Ukuran AND BARCODE.KodeArt = STOCK.KodeArt " +
        "  AND STOCK.KodeArt = ART.KodeArt " +
        "  AND ART.KodeGender = GENDER.KodeGender")
public class TrxTerimaStockModel {

    private String noDo;
    private String kodeArt;
    private String namaArt;
    private String noBarcode;
    private String ukuran;
    private String gender;
    private String warna;
    private double harga;
    private Integer qtyKirim;
    private Integer qtyTerima;
    private Date lastUpdate;
    private String grade;

    public String getNoDo() {
        return noDo;
    }

    public void setNoDo(String noDo) {
        this.noDo = noDo;
    }

    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(String kodeArt) {
        this.kodeArt = kodeArt;
    }

    public String getNamaArt() {
        return namaArt;
    }

    public void setNamaArt(String namaArt) {
        this.namaArt = namaArt;
    }

    public String getNoBarcode() {
        return noBarcode;
    }

    public void setNoBarcode(String noBarcode) {
        this.noBarcode = noBarcode;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public Integer getQtyKirim() {
        return qtyKirim;
    }

    public void setQtyKirim(Integer qtyKirim) {
        this.qtyKirim = qtyKirim;
    }

    public Integer getQtyTerima() {
        return qtyTerima;
    }

    public void setQtyTerima(Integer qtyTerima) {
        this.qtyTerima = qtyTerima;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
