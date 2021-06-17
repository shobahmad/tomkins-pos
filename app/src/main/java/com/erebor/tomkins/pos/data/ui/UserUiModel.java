package com.erebor.tomkins.pos.data.ui;

public class UserUiModel {
    private final String KodeSPG;
    private final String NamaSPG;
    private final String KodeCounter;
    private final String NamaCounter;
    private final boolean PrimarySPG;

    public UserUiModel(String kodeSPG, String namaSPG, String kodeCounter,
                       String namaCounter, boolean primarySPG) {
        KodeSPG = kodeSPG;
        NamaSPG = namaSPG;
        KodeCounter = kodeCounter;
        NamaCounter = namaCounter;
        PrimarySPG = primarySPG;
    }

    public String getKodeSPG() {
        return KodeSPG;
    }

    public String getNamaSPG() {
        return NamaSPG;
    }

    public String getKodeCounter() {
        return KodeCounter;
    }

    public String getNamaCounter() {
        return NamaCounter;
    }

    public boolean isPrimarySPG() {
        return PrimarySPG;
    }
}
