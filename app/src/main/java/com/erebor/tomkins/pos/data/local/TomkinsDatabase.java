package com.erebor.tomkins.pos.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.erebor.tomkins.pos.data.converters.Converters;
import com.erebor.tomkins.pos.data.local.dao.AccessDao;
import com.erebor.tomkins.pos.data.local.dao.DownloadLastUpdateDao;
import com.erebor.tomkins.pos.data.local.dao.EventHargaDao;
import com.erebor.tomkins.pos.data.local.dao.EventHargaDetDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.dao.MsBrandDao;
import com.erebor.tomkins.pos.data.local.dao.MsGenderDao;
import com.erebor.tomkins.pos.data.local.dao.MsUkuranDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.UserDao;
import com.erebor.tomkins.pos.data.local.model.*;


@Database(entities = {
        UserDBModel.class,
        AccessDBModel.class,

//        EventFreeDBModel.class,
//        EventFreeDetDBModel.class,
        EventHargaDBModel.class,
        EventHargaDetDBModel.class,
        MsArtDBModel.class,
        MsBarcodeDBModel.class,
        MsBrandDBModel.class,
        MsDepStoreDBModel.class,
        MsGenderDBModel.class,
        MsKonterDBModel.class,
        MsUkuranDBModel.class,
//        MutasiArtDBModel.class,
        StokRealDBModel.class,
        TrxJualDBModel.class,
        TrxJualDetDBModel.class,
        TrxOpnameDBModel.class,
        TrxOpnameDetDBModel.class,
//        TrxReturGjbDBModel.class,
//        TrxReturGjbDetDBModel.class,
//        TrxTerimaDBModel.class,
//        TrxTerimaDetDBModel.class,

    },
    views = {DownloadLastUpdateModel.class},
    version = 1, exportSchema = false)
@TypeConverters({
        Converters.class
})
public abstract class TomkinsDatabase extends RoomDatabase {
    public static final String DB_NAME = "TOMKINS.db";

    private static TomkinsDatabase INSTANCE;

    public abstract UserDao userDAO();
    public abstract AccessDao accessDao();
    public abstract MsArtDao msArtDao();
    public abstract MsBarcodeDao msBarcodeDao();
    public abstract MsBrandDao msBrandDao();
    public abstract MsGenderDao msGenderDao();
    public abstract MsUkuranDao msUkuranDao();
    public abstract StokRealDao stokRealDao();
    public abstract DownloadLastUpdateDao downloadLastUpdateDao();
    public abstract EventHargaDao eventHargaDao();
    public abstract EventHargaDetDao eventHargaDetDao();

    private static final Object sLock = new Object();

    public static TomkinsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TomkinsDatabase.class, DB_NAME)
                        .addCallback(CREATE_CALLBACK)
                        .build();
            }
            return INSTANCE;
        }
    }


    static Callback CREATE_CALLBACK = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);

            database.execSQL(Triggers.accessFromUser);
        }
    };

}
