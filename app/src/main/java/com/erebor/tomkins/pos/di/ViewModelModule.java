package com.erebor.tomkins.pos.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkManager;

import com.erebor.tomkins.pos.factory.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = ViewModelSubComponent.class)
public class ViewModelModule {

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new ViewModelFactory(viewModelSubComponent.build());
    }

    @Singleton
    @Provides
    WorkManager providesWorkManager(@NonNull Application application) {
        return WorkManager.getInstance(application);
    }
}
