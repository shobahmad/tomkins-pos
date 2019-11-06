package com.erebor.tomkins.pos.di;

import android.content.Context;
import android.util.Log;

import com.erebor.tomkins.pos.data.converters.DateDeserializer;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class HttpClientModule {


    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    HostSelectionInterceptor provideHostSelectionInterceptor(SharedPrefs sharedPrefs) {
        return new HostSelectionInterceptor(sharedPrefs);
    }


    @Provides
    @Singleton
    OkHttpClient getRequestHeader(Context context, HostSelectionInterceptor hostSelectionInterceptor, SharedPrefs sharedPrefs) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor(message -> Log.d("HttpLog", message));
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(hostSelectionInterceptor)
                .addNetworkInterceptor(logger)
                .addInterceptor(logger)
                .addInterceptor(new ChuckInterceptor(context));

        return httpClient.build();
    }

}