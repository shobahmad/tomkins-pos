package com.erebor.tomkins.pos.data.local.model;

import androidx.room.DatabaseView;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;

import java.util.Date;

@DatabaseView("select * from (select last_update article, NULL barcode, NULL brand, NULL gender, NULL size, NULL stock  from MSART order by last_update desc limit 1) art union " +
              "select * from (select NULL article, last_update barcode, NULL brand, NULL gender, NULL size, NULL stock  from MSBARCODE order by last_update desc limit 1) barcode")
public class DownloadLastUpdateModel implements BaseDatabaseModel {
    public Date article;
    public Date barcode;
    public Date brand;
    public Date gender;
    public Date size;
    public Date stock;

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
}
