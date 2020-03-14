package com.erebor.tomkins.pos.di;

import android.content.Context;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.AccessDao;
import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
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
    MsArtDao providesMsArt(TomkinsDatabase tomkinsDatabase) {
        return tomkinsDatabase.msArtDao();
    }
}
