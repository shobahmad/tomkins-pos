package com.erebor.tomkins.pos.data.local.model;

import androidx.room.DatabaseView;

import java.util.Date;

@DatabaseView("SELECT EVENT.KodeEvent kodeEvent, DISCOUNT.KodeArt kodeArt, DISCOUNT.HargaKhusus hargaKhusus, DISCOUNT.Diskon diskon , EVENT.TglDari tglDari, EVENT.TglSampai tglSampai FROM EVENTHARGA EVENT, EVENTHARGADET DISCOUNT WHERE EVENT.KodeEvent = DISCOUNT.KodeEvent")
public class EventDiscountModel {

    public String kodeEvent;
    public String kodeArt;
    public double hargaKhusus;
    public double diskon;
    public Date tglDari;
    public Date tglSampai;

    public String getKodeEvent() {
        return kodeEvent;
    }

    public void setKodeEvent(String kodeEvent) {
        this.kodeEvent = kodeEvent;
    }


    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(String kodeArt) {
        this.kodeArt = kodeArt;
    }

    public double getHargaKhusus() {
        return hargaKhusus;
    }

    public void setHargaKhusus(double hargaKhusus) {
        this.hargaKhusus = hargaKhusus;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public Date getTglDari() {
        return tglDari;
    }

    public void setTglDari(Date tglDari) {
        this.tglDari = tglDari;
    }

    public Date getTglSampai() {
        return tglSampai;
    }

    public void setTglSampai(Date tglSampai) {
        this.tglSampai = tglSampai;
    }
}
