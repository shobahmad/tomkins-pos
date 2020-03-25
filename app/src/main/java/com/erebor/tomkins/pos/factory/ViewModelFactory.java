package com.erebor.tomkins.pos.factory;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.erebor.tomkins.pos.di.ViewModelSubComponent;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDataMasterViewModel;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewModel;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final ArrayMap<Class, Callable<? extends ViewModel>> creators;

    @Inject
    public ViewModelFactory(final ViewModelSubComponent viewModelSubComponent) {
        creators = new ArrayMap<>();

        creators.put(SplashViewModel.class, () -> viewModelSubComponent.splashViewModel());
        creators.put(LoginViewModel.class, () -> viewModelSubComponent.loginViewModel());
        creators.put(DownloadInfoViewModel.class, () -> viewModelSubComponent.downloadInfoViewModel());
        creators.put(SyncDataMasterViewModel.class, () -> viewModelSubComponent.syncDataMasterViewModel());
        creators.put(TransactionViewModel.class, () -> viewModelSubComponent.transactionViewModel());

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Callable<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("Unknown model class " + modelClass);
        }
        try {
            return (T) creator.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

