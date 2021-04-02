package com.erebor.tomkins.pos.factory;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.erebor.tomkins.pos.di.ViewModelSubComponent;
import com.erebor.tomkins.pos.viewmodel.article.ArticleViewModel;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveStockViewModel;
import com.erebor.tomkins.pos.viewmodel.receive.ProductReceiveViewModel;
import com.erebor.tomkins.pos.viewmodel.report.GenderViewModel;
import com.erebor.tomkins.pos.viewmodel.report.StockReportViewModel;
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDownloadViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadStockViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadTrxTerimaViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadTrxViewModel;
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
        creators.put(SyncDownloadViewModel.class, () -> viewModelSubComponent.syncDataMasterViewModel());
        creators.put(TransactionViewModel.class, () -> viewModelSubComponent.transactionViewModel());
        creators.put(SyncUploadTrxViewModel.class, () -> viewModelSubComponent.syncUploadViewModel());
        creators.put(ArticleViewModel.class, () -> viewModelSubComponent.articleViewModel());
        creators.put(StockReportViewModel.class, () -> viewModelSubComponent.stockReportViewModel());
        creators.put(GenderViewModel.class, () -> viewModelSubComponent.genderViewModel());
        creators.put(SyncUploadStockViewModel.class, () -> viewModelSubComponent.syncUploadStockViewModel());
        creators.put(ProductReceiveViewModel.class, () -> viewModelSubComponent.productReceiveViewModel());
        creators.put(ProductReceiveStockViewModel.class, () -> viewModelSubComponent.productReceiveStockViewModel());
        creators.put(SyncUploadTrxTerimaViewModel.class, () -> viewModelSubComponent.syncUploadTrxTerimaViewModel());

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

