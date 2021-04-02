package com.erebor.tomkins.pos.data.ui;

public class ProductReceiveUiModel {

    private final String noDo;
    private final String tglKirimGBJ;
    private final String tglTerimaCnt;
    private final String catatan;
    private final boolean isComplete;
    private final double qtyTotal;
    private final double qtyReceived;
    private final boolean isUploaded;
    private final String lastUpdate;

    public ProductReceiveUiModel(String noDo, String tglKirimGBJ,
                                 String tglTerimaCnt, String catatan,
                                 boolean isComplete, double qtyTotal,
                                 double qtyReceived, boolean isUploaded,
                                 String lastUpdate) {
        this.noDo = noDo;
        this.tglKirimGBJ = tglKirimGBJ == null ? "-" : tglKirimGBJ;
        this.tglTerimaCnt = tglTerimaCnt == null ? "-" : tglTerimaCnt;
        this.catatan = catatan;
        this.isComplete = isComplete;
        this.qtyTotal = qtyTotal;
        this.qtyReceived = qtyReceived;
        this.isUploaded = isUploaded;
        this.lastUpdate = lastUpdate;
    }

    public String getNoDo() {
        return noDo;
    }

    public String getTglKirimGBJ() {
        return tglKirimGBJ;
    }

    public String getTglTerimaCnt() {
        return tglTerimaCnt;
    }

    public String getCatatan() {
        return catatan;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public double getQtyTotal() {
        return qtyTotal;
    }

    public double getQtyReceived() {
        return qtyReceived;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }
}
