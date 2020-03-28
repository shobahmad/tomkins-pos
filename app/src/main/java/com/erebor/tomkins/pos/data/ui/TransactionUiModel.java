package com.erebor.tomkins.pos.data.ui;

import java.util.ArrayList;
import java.util.Date;

public class TransactionUiModel {
    private final String barcode;
    private final String transactionId;
    private final Date transactionDate;
    private final double grandTotal;
    private final ArrayList<TransactionDetailUiModel> listTransaction;

    public TransactionUiModel(String barcode, String transactionId, Date transactionDate, double grandTotal, ArrayList<TransactionDetailUiModel> listTransaction) {
        this.barcode = barcode;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.grandTotal = grandTotal;
        this.listTransaction = listTransaction;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public ArrayList<TransactionDetailUiModel> getListTransaction() {
        return listTransaction;
    }
}
