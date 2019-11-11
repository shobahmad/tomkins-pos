package com.erebor.tomkins.pos.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {
    @Provides @Singleton fun provideContext(): Context = app
    @Provides @Singleton fun provideApplication(): Application = app
}

