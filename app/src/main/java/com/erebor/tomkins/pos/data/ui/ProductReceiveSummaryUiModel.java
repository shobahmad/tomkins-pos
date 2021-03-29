package com.erebor.tomkins.pos.data.ui;

import java.util.Date;

public class ProductReceiveSummaryUiModel {
    private final Date receiveDate;
    private final String note;

    public ProductReceiveSummaryUiModel(Date receiveDate, String note) {
        this.receiveDate = receiveDate;
        this.note = note;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public String getNote() {
        return note;
    }
}

