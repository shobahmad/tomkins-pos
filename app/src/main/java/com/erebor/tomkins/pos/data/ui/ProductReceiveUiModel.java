package com.erebor.tomkins.pos.data.ui;

import java.util.Date;

public class ProductReceiveUiModel {

    private final String noDo;
    private final Date tglKirimGBJ;
    private final Date tglTerimaCnt;
    private final String catatan;
    private final boolean isComplete;
    private final double qtyTotal;
    private final double qtyReceived;

    public ProductReceiveUiModel(String noDo, Date tglKirimGBJ,
                                 Date tglTerimaCnt, String catatan,
                                 boolean isComplete, double qtyTotal, double qtyReceived) {
        this.noDo = noDo;
        this.tglKirimGBJ = tglKirimGBJ;
        this.tglTerimaCnt = tglTerimaCnt;
        this.catatan = catatan;
        this.isComplete = isComplete;
        this.qtyTotal = qtyTotal;
        this.qtyReceived = qtyReceived;
    }

    public String getNoDo() {
        return noDo;
    }

    public Date getTglKirimGBJ() {
        return tglKirimGBJ;
    }

    public Date getTglTerimaCnt() {
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
}
