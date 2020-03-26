package com.erebor.tomkins.pos.data.local.model;

import androidx.room.DatabaseView;

import java.util.Date;

@DatabaseView("select article.last_update article, barcode.last_update barcode, brand.last_update brand, gender.last_update gender, size.last_update size, stock.last_update stock, event.last_update event, eventdetail.last_update eventdetail from " +
              "(select last_update from MSART order by last_update desc limit 1) article, " +
              "(select last_update from MSBARCODE order by last_update desc limit 1) barcode, " +
              "(select last_update from MSBRAND order by last_update desc limit 1) brand, " +
              "(select last_update from MSGENDER order by last_update desc limit 1) gender, " +
              "(select last_update from MSUKURAN order by last_update desc limit 1) size, " +
              "(select last_update from STOKREAL order by last_update desc limit 1) stock, " +
              "(select last_update from EVENTHARGA order by last_update desc limit 1) event, " +
              "(select last_update from EVENTHARGADET order by last_update desc limit 1) eventdetail "
             )
public class DownloadLastUpdateModel  {
    public Date article;
    public Date barcode;
    public Date brand;
    public Date gender;
    public Date size;
    public Date stock;
    public Date event;
    public Date eventdetail;

    public Date getArticle() {
        return article;
    }

    public void setArticle(Date article) {
        this.article = article;
    }

    public Date getBarcode() {
        return barcode;
    }

    public void setBarcode(Date barcode) {
        this.barcode = barcode;
    }

    public Date getBrand() {
        return brand;
    }

    public void setBrand(Date brand) {
        this.brand = brand;
    }

    public Date getGender() {
        return gender;
    }

    public void setGender(Date gender) {
        this.gender = gender;
    }

    public Date getSize() {
        return size;
    }

    public void setSize(Date size) {
        this.size = size;
    }

    public Date getStock() {
        return stock;
    }

    public void setStock(Date stock) {
        this.stock = stock;
    }

    public Date getEvent() {
        return event;
    }

    public void setEvent(Date event) {
        this.event = event;
    }

    public Date getEventdetail() {
        return eventdetail;
    }

    public void setEventdetail(Date eventdetail) {
        this.eventdetail = eventdetail;
    }
}
