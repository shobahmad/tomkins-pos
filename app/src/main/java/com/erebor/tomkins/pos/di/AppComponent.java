package com.erebor.tomkins.pos.di;

import android.app.Application;

import com.erebor.tomkins.pos.MainActivity;
import com.erebor.tomkins.pos.repository.network.RetrofitModule;
import com.erebor.tomkins.pos.view.article.ArticleActivity;
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.view.sale.SaleActivity;
import com.erebor.tomkins.pos.view.setting.SettingPrefsFragment;
import com.erebor.tomkins.pos.view.splash.SplashScreenActivity;
import com.erebor.tomkins.pos.view.sync.SyncActivity;
import com.erebor.tomkins.pos.view.transaction.TransactionActivity;
import com.erebor.tomkins.pos.worker.SyncEventHargaDetDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncEventHargaDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsArtDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsBarcodeDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsBrandDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsGenderDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsUkuranDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncStokRealDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncUploadWorker;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        ViewModelModule.class,
        UtilsModule.class,
        LogModule.class,
        HttpClientModule.class,
        RetrofitModule.class,
        SharedPrefsModule.class,
        DatabaseModule.class
})
@Singleton
public interface AppComponent {
    Application getApplication();

    void doInjection(MainActivity mainActivity);
    void doInjection(SplashScreenActivity activity);
    void doInjection(LoginActivity activity);
    void doInjection(DashboardActivity activity);
    void doInjection(SaleActivity activity);
    void doInjection(SyncActivity activity);
    void doInjection(TransactionActivity activity);
    void doInjection(ArticleActivity activity);

    void doInjection(SettingPrefsFragment fragment);

    void doInjection(SyncMsArtDownloadWorker worker);
    void doInjection(SyncMsBarcodeDownloadWorker worker);
    void doInjection(SyncMsBrandDownloadWorker worker);
    void doInjection(SyncMsGenderDownloadWorker worker);
    void doInjection(SyncMsUkuranDownloadWorker worker);
    void doInjection(SyncStokRealDownloadWorker worker);
    void doInjection(SyncEventHargaDownloadWorker worker);
    void doInjection(SyncEventHargaDetDownloadWorker worker);

    void doInjection(SyncUploadWorker worker);

}
