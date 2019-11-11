package com.erebor.tomkins.pos.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager

import com.erebor.tomkins.pos.factory.ViewModelFactory

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module(subcomponents = [ViewModelSubComponent::class])
class ViewModelModule {

    @Singleton
    @Provides
    internal fun provideViewModelFactory(
            viewModelSubComponent: ViewModelSubComponent.Builder): ViewModelProvider.Factory {

        return ViewModelFactory(viewModelSubComponent.build())
    }

    @Singleton
    @Provides
    internal fun providesWorkManager(application: Application): WorkManager {
        return WorkManager.getInstance(application)
    }
}
