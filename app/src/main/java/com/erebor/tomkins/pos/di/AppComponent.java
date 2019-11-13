package com.erebor.tomkins.pos.di;

import android.app.Application;

import com.erebor.tomkins.pos.MainActivity;
import com.erebor.tomkins.pos.view.login.LoginActivity;
import com.erebor.tomkins.pos.view.splash.SplashScreenActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        ViewModelModule.class,
        UtilsModule.class,
        LogModule.class,
        HttpClientModule.class,
        RetrofitModule.class,
        SharedPrefsModule.class
})
@Singleton
public interface AppComponent {
    Application getApplication();

    void doInjection(MainActivity mainActivity);
    void doInjection(SplashScreenActivity activity);
    void doInjection(LoginActivity activity);


}
