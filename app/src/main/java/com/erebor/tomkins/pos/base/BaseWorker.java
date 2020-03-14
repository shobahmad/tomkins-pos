package com.erebor.tomkins.pos.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.TomkinApps;
import com.erebor.tomkins.pos.di.AppComponent;

public abstract class BaseWorker extends Worker {

    public BaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        TomkinApps application = (TomkinApps) context.getApplicationContext();
        inject(application.getAppComponent());
    }

    public abstract void inject(AppComponent appComponent);
}
