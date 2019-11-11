package com.erebor.tomkins.pos.di

import com.erebor.tomkins.pos.tools.SharedPrefs
import com.google.gson.Gson

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {


    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient, sharedPrefs: SharedPrefs): Retrofit {
        return Retrofit.Builder()
                .baseUrl(sharedPrefs.hostname)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
    //
    //    @Provides
    //    @Singleton
    //    UserAuthService providesUserAuthService(Retrofit retrofit) {
    //        return retrofit.create(UserAuthService.class);
    //    }
}
