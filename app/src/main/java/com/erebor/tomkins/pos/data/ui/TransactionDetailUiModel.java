package com.erebor.tomkins.pos.data.ui;

public class TransactionDetailUiModel {

    private final String indTrx;
    private final String transTime;
    private final String artCode;
    private final String artName;
    private final String barcode;
    private final String size;
    private final String colour;
    private final double hargaNormal;
    private final String eventCode;
    private final int qty;
    private final double diskon;
    private final double hargaKhusus;
    private final double hargaJual;
    private final String note;
    private final boolean isUploaded;

    public TransactionDetailUiModel(String indTrx, String transTime, String artName, String artCode, String barcode, String size, String colour, double hargaNormal, String eventCode, Integer qty, double diskon, double hargaKhusus, double hargaJual, String note, boolean isUploaded) {
        this.indTrx = indTrx;
        this.transTime = transTime;
        this.artCode = artCode;
        this.artName = artName;
        this.barcode = barcode;
        this.size = size;
        this.colour = colour;
        this.hargaNormal = hargaNormal;
        this.eventCode = eventCode;
        this.qty = qty;
        this.diskon = diskon;
        this.hargaKhusus = hargaKhusus;
        this.hargaJual = hargaJual;
        this.note = note;
        this.isUploaded = isUploaded;
    }

    public String getIndTrx() {
        return indTrx;
    }

    public String getTransTime() {
        return transTime;
    }

    public String getArtName() {
        return artName;
    }

    public String getArtCode() {
        return artCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    public double getHargaNormal() {
        return hargaNormal;
    }

    public String getEventCode() {
        return eventCode;
    }

    public int getQty() {
        return qty;
    }

    public double getDiskon() {
        return diskon;
    }

    public double getHargaKhusus() {
        return hargaKhusus;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public double getSubTotal() {
        return hargaJual * qty;
    }

    public String getNote() {
        return note;
    }

    public boolean isUploaded() {
        return isUploaded;
    }
}
