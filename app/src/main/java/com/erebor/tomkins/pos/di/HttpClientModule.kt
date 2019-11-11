package com.erebor.tomkins.pos.di

import android.content.Context
import android.util.Log

import com.erebor.tomkins.pos.data.converters.DateDeserializer
import com.erebor.tomkins.pos.tools.SharedPrefs
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor

import java.util.Date
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class HttpClientModule {


    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val builder = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    internal fun provideHostSelectionInterceptor(sharedPrefs: SharedPrefs): HostSelectionInterceptor {
        return HostSelectionInterceptor(sharedPrefs)
    }


    @Provides
    @Singleton
    internal fun getRequestHeader(context: Context, hostSelectionInterceptor: HostSelectionInterceptor, sharedPrefs: SharedPrefs): OkHttpClient {
        val logger = HttpLoggingInterceptor { message -> Log.d("HttpLog", message) }
        logger.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        httpClient
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(hostSelectionInterceptor)
                .addNetworkInterceptor(logger)
                .addInterceptor(logger)
                .addInterceptor(ChuckInterceptor(context))

        return httpClient.build()
    }

}
