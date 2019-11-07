package com.erebor.tomkins.pos.data.ui;

public class ReportSummaryUiModel {
    private int stockTotal;
    private int stockIncoming;
    private int stockOutgoing;

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public int getStockIncoming() {
        return stockIncoming;
    }

    public void setStockIncoming(int stockIncoming) {
        this.stockIncoming = stockIncoming;
    }

    public int getStockOutgoing() {
        return stockOutgoing;
    }

    public void setStockOutgoing(int stockOutgoing) {
        this.stockOutgoing = stockOutgoing;
    }
}
