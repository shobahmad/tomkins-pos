package com.erebor.tomkins.pos.data.ui;

public class TransactionDetailUiModel {

    private final String indTrx;
    private final String artCode;
    private final String artName;
    private final String barcode;
    private final String size;
    private final String colour;
    private final double hargaNormal;
    private final String eventName;
    private final int qty;
    private final double hargaJual;
    private final String note;

    public TransactionDetailUiModel(String indTrx, String artName, String artCode, String barcode, String size, String colour, double hargaNormal, String eventName, Integer qty, double hargaJual, String note) {
        this.indTrx = indTrx;
        this.artCode = artCode;
        this.artName = artName;
        this.barcode = barcode;
        this.size = size;
        this.colour = colour;
        this.hargaNormal = hargaNormal;
        this.eventName = eventName;
        this.qty = qty;
        this.hargaJual = hargaJual;
        this.note = note;
    }

    public String getIndTrx() {
        return indTrx;
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

    public String getEventName() {
        return eventName;
    }

    public int getQty() {
        return qty;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public String getNote() {
        return note;
    }
}
