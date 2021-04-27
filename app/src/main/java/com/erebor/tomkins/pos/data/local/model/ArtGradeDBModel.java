package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "ART_GRADE",
primaryKeys = {"Barcode"})
public class ArtGradeDBModel implements BaseDatabaseModel {

    public ArtGradeDBModel(@NonNull String barcode, @NonNull String grade) {
        this.barcode = barcode;
        this.grade = grade;
    }

    @NonNull
    @ColumnInfo(name = "Barcode")
    private String barcode;

    @NonNull
    @ColumnInfo(name = "Grade", defaultValue = "A")
    private String grade;

    @NonNull
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(@NonNull String barcode) {
        this.barcode = barcode;
    }

    @NonNull
    public String getGrade() {
        return grade;
    }

    public void setGrade(@NonNull String grade) {
        this.grade = grade;
    }

    @Override
    public void setLastUpdate(Date lastUpdate) {
    }

    @Override
    public Date getLastUpdate() {
        return Calendar.getInstance().getTime();
    }
}
