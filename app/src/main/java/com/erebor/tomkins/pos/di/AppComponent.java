package com.erebor.tomkins.pos.di;

import android.app.Application;

import com.erebor.tomkins.pos.MainActivity;
import com.erebor.tomkins.pos.repository.network.RetrofitModule;
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.view.sale.SaleActivity;
import com.erebor.tomkins.pos.view.setting.SettingPrefsFragment;
import com.erebor.tomkins.pos.view.splash.SplashScreenActivity;
import com.erebor.tomkins.pos.view.sync.SyncActivity;
import com.erebor.tomkins.pos.worker.SyncMsArtWorker;
import com.erebor.tomkins.pos.worker.SyncMsBarcodeWorker;
import com.erebor.tomkins.pos.worker.SyncMsBrandWorker;

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

    void doInjection(SettingPrefsFragment fragment);

    void doInjection(SyncMsArtWorker worker);
    void doInjection(SyncMsBarcodeWorker worker);
    void doInjection(SyncMsBrandWorker worker);

}
