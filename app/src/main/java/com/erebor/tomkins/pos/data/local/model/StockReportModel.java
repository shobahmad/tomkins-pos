package com.erebor.tomkins.pos.data.local.model;

import androidx.room.DatabaseView;

import java.util.Date;

@DatabaseView("SELECT  ART.KodeArt kodeArt, ART.NamaArt namaArt, BARCODE.NoBarcode noBarcode, BARCODE.Ukuran ukuran," +
        " GENDER.Gender gender, ART.Warna warna, ART.Harga harga, STOCK.QtyStok qtyStok, STOCK.last_update lastUpdate " +
        "FROM MSBARCODE BARCODE, MSART ART, STOKREAL STOCK, MSGENDER GENDER " +
        "WHERE BARCODE.Ukuran = STOCK.Ukuran AND BARCODE.KodeArt = STOCK.KodeArt " +
        "  AND STOCK.KodeArt = ART.KodeArt " +
        "  AND ART.KodeGender = GENDER.KodeGender")
public class StockReportModel {

    private String kodeArt;
    private String namaArt;
    private String noBarcode;
    private String ukuran;
    private String gender;
    private String warna;
    private double harga;
    private Integer qtyStok;
    private Date lastUpdate;

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

    public Integer getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(Integer qtyStok) {
        this.qtyStok = qtyStok;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
