package com.erebor.tomkins.pos.di;

import com.erebor.tomkins.pos.viewmodel.article.ArticleViewModel;
import com.erebor.tomkins.pos.viewmodel.login.LoginViewModel;
import com.erebor.tomkins.pos.viewmodel.report.StockReportViewModel;
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.DownloadInfoViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncDownloadViewModel;
import com.erebor.tomkins.pos.viewmodel.sync.SyncUploadViewModel;
import com.erebor.tomkins.pos.viewmodel.transaction.TransactionViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link com.erebor.tomkins.pos.factory.ViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    SplashViewModel splashViewModel();
    LoginViewModel loginViewModel();
    DownloadInfoViewModel downloadInfoViewModel();
    SyncDownloadViewModel syncDataMasterViewModel();
    TransactionViewModel transactionViewModel();
    SyncUploadViewModel syncUploadViewModel();
    ArticleViewModel articleViewModel();
    StockReportViewModel stockReportViewModel();

}
