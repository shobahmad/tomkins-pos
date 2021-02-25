package com.erebor.tomkins.pos.data.ui;

public class ArticleUiModel {

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
    private final boolean selected;

    public ArticleUiModel(String artName, String artCode, String barcode, String size, String colour, double hargaNormal, String eventCode, Integer qty, double diskon, double hargaKhusus, double harga, boolean selected) {
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
        this.hargaJual = harga;
        this.selected = selected;
    }


    public ArticleUiModel(ArticleUiModel articleUiModel, boolean selected) {
        this.artCode = articleUiModel.artCode;
        this.artName = articleUiModel.artName;
        this.barcode = articleUiModel.barcode;
        this.size = articleUiModel.size;
        this.colour = articleUiModel.colour;
        this.hargaNormal = articleUiModel.hargaNormal;
        this.eventCode = articleUiModel.eventCode;
        this.qty = articleUiModel.qty;
        this.diskon = articleUiModel.diskon;
        this.hargaKhusus = articleUiModel.hargaKhusus;
        this.hargaJual = articleUiModel.hargaJual;
        this.selected = selected;
    }

    public ArticleUiModel(ArticleUiModel articleUiModel, double hargaNormal, double diskon) {
        this.artCode = articleUiModel.artCode;
        this.artName = articleUiModel.artName;
        this.barcode = articleUiModel.barcode;
        this.size = articleUiModel.size;
        this.colour = articleUiModel.colour;
        this.hargaNormal = hargaNormal;
        this.eventCode = articleUiModel.eventCode;
        this.qty = articleUiModel.qty;
        this.diskon = diskon;
        this.hargaKhusus = articleUiModel.hargaKhusus;
        this.hargaJual = articleUiModel.hargaJual;
        this.selected = articleUiModel.selected;
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

    public double getHargaNormalKhusus() {
        if (hargaKhusus == 0) {
            return hargaNormal;
        }

        return hargaKhusus;
    }

    public boolean isSelected() {
        return selected;
    }
}
