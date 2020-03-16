package com.erebor.tomkins.pos.di;

import android.content.Context;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.AccessDao;
import com.erebor.tomkins.pos.data.local.dao.DownloadLastUpdateDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsBarcodeDao;
import com.erebor.tomkins.pos.data.local.dao.MsBrandDao;
import com.erebor.tomkins.pos.data.local.dao.MsGenderDao;
import com.erebor.tomkins.pos.data.local.dao.MsUkuranDao;
import com.erebor.tomkins.pos.data.local.dao.UserDao;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;

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
    DownloadLastUpdateDao providesDownloadLastUpdateDao(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.downloadLastUpdateDao();
    }
}
