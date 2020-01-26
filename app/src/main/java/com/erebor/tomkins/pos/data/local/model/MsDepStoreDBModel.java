package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "MSDEPTSTORE",
        indices = @Index(value = "NamaDeptStore", unique = true)
)
public class MsDepStoreDBModel {
    /*
    CREATE TABLE [MSDEPTSTORE](
  [KodeDeptStore] VARCHAR(2) PRIMARY KEY NOT NULL,
  [NamaDeptStore] VARCHAR(60),
  [JenisDeptStore] VARCHAR(10))
     */

    /*
    CREATE UNIQUE INDEX [] ON [MSDEPTSTORE]([NamaDeptStore] ASC)
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeDeptStore")
    private String kodeDeptStore;

    @ColumnInfo(name = "NamaDeptStore")
    private String namaDeptStore;

    @ColumnInfo(name = "JenisDeptStore")
    private String jenisDeptStore;

    @NonNull
    public String getKodeDeptStore() {
        return kodeDeptStore;
    }

    public void setKodeDeptStore(@NonNull String kodeDeptStore) {
        this.kodeDeptStore = kodeDeptStore;
    }

    public String getNamaDeptStore() {
        return namaDeptStore;
    }

    public void setNamaDeptStore(String namaDeptStore) {
        this.namaDeptStore = namaDeptStore;
    }

    public String getJenisDeptStore() {
        return jenisDeptStore;
    }

    public void setJenisDeptStore(String jenisDeptStore) {
        this.jenisDeptStore = jenisDeptStore;
    }
}
