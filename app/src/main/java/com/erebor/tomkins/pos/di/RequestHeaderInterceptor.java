package com.erebor.tomkins.pos.di;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHeaderInterceptor implements Interceptor {

    private Context context;
    private SharedPrefs sharedPrefs;
    RequestHeaderInterceptor(Context context, SharedPrefs sharedPrefs) {
        this.context = context;
        this.sharedPrefs = sharedPrefs;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("Authorization", sharedPrefs.getToken())
                .header("DeviceId", getDeviceId(context))
                .build();
        return chain.proceed(request);
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
