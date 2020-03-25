package com.erebor.tomkins.pos.data.ui;

public class TransactionDetailUiModel {

    private final int indTrx;
    private final String artCode;
    private final String artName;
    private final String size;
    private final double hargaNormal;
    private final String eventName;
    private final int qty;
    private final double hargaJual;
    private final String note;

    public TransactionDetailUiModel(Integer indTrx, String artName, String artCode, String size, double hargaNormal, String eventName, Integer qty, double hargaJual, String note) {
        this.indTrx = indTrx;
        this.artCode = artCode;
        this.artName = artName;
        this.size = size;
        this.hargaNormal = hargaNormal;
        this.eventName = eventName;
        this.qty = qty;
        this.hargaJual = hargaJual;
        this.note = note;
    }

    public int getIndTrx() {
        return indTrx;
    }

    public String getArtName() {
        return artName;
    }

    public String getArtCode() {
        return artCode;
    }

    public String getSize() {
        return size;
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
