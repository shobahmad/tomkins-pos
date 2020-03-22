package com.erebor.tomkins.pos.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("KodeKonter")
    @Expose
    private String KodeKonter;

    @SerializedName("NamaKonter")
    @Expose
    private String NamaKonter;

    @SerializedName("KodeSPG")
    @Expose
    private String KodeSPG;

    @SerializedName("NamaSPG")
    @Expose
    private String NamaSPG;

    @SerializedName("Token")
    @Expose
    private String Token;

    public String getKodeKonter() {
        return KodeKonter;
    }

    public void setKodeKonter(String kodeKonter) {
        KodeKonter = kodeKonter;
    }

    public String getNamaKonter() {
        return NamaKonter;
    }

    public void setNamaKonter(String namaKonter) {
        NamaKonter = namaKonter;
    }

    public String getKodeSPG() {
        return KodeSPG;
    }

    public void setKodeSPG(String kodeSPG) {
        KodeSPG = kodeSPG;
    }

    public String getNamaSPG() {
        return NamaSPG;
    }

    public void setNamaSPG(String namaSPG) {
        NamaSPG = namaSPG;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
