package com.erebor.tomkins.pos.repository.local;

public interface StockUpdateLocalRepository {
    void addStock(String kodeArt, String size, int qty);
    void removeStock(String kodeArt, String size, int qty);
}
