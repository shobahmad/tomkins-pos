package com.erebor.tomkins.pos.di;

import android.content.Context;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.AccessDao;
import com.erebor.tomkins.pos.data.local.dao.DownloadLastUpdateDao;
import com.erebor.tomkins.pos.data.local.dao.EventDiscountDao;
import com.erebor.tomkins.pos.data.local.dao.EventHargaDao;
import com.erebor.tomkins.pos.data.local.dao.EventHargaDetDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.dao.MsBrandDao;
import com.erebor.tomkins.pos.data.local.dao.MsGenderDao;
import com.erebor.tomkins.pos.data.local.dao.MsUkuranDao;
import com.erebor.tomkins.pos.data.local.dao.StockReportDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDetDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.data.local.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    TomkinsDatabase providesDatabase(Context context) {
        return TomkinsDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    AccessDao providesAccessDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.accessDao();
    }
    @Provides
    @Singleton
    UserDao providesUserDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.userDAO();
    }

    @Provides
    @Singleton
    MsArtDao providesMsArtDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.msArtDao();
    }
    @Provides
    @Singleton
    MsBarcodeDao providesMsBarcodeDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.msBarcodeDao();
    }
    @Provides
    @Singleton
    MsBrandDao providesMsBrandDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.msBrandDao();
    }
    @Provides
    @Singleton
    MsGenderDao providesMsGenderDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.msGenderDao();
    }
    @Provides
    @Singleton
    MsUkuranDao providesMsUkuranDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.msUkuranDao();
    }
    @Provides
    @Singleton
    StokRealDao providesStokRealDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.stokRealDao();
    }

    @Provides
    @Singleton
    DownloadLastUpdateDao providesDownloadLastUpdateDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.downloadLastUpdateDao();
    }

    @Provides
    @Singleton
    EventHargaDao providesEventHargaDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.eventHargaDao();
    }
    @Provides
    @Singleton
    EventHargaDetDao providesEventHargaDetDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.eventHargaDetDao();
    }
    @Provides
    @Singleton
    EventDiscountDao providesEventDiscountDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.eventDiscountDao();
    }
    @Provides
    @Singleton
    TrxJualDao providesTrxJualDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.trxJualDao();
    }
    @Provides
    @Singleton
    TrxJualDetDao providesTrxJualDetDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.trxJualDetDao();
    }
    @Provides
    @Singleton
    StockReportDao providesStockReportDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.stockReportDao();
    }
    @Provides
    @Singleton
    TrxTerimaDao providesTrxTerimaDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.trxTerimaDao();
    }
    @Provides
    @Singleton
    TrxTerimaDetDao providesTrxTerimaDetDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.trxTerimaDetDao();
    }
}
