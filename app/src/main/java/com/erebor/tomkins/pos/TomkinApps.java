package com.erebor.tomkins.pos;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.di.AppModule;
import com.erebor.tomkins.pos.di.DaggerAppComponent;
import com.erebor.tomkins.pos.di.UtilsModule;


public class TomkinApps extends MultiDexApplication {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
