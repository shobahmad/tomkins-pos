package com.erebor.tomkins.pos

import android.content.Context

import androidx.multidex.MultiDexApplication

import com.erebor.tomkins.pos.di.AppComponent
import com.erebor.tomkins.pos.di.AppModule
import com.erebor.tomkins.pos.di.UtilsModule


class TomkinApps : MultiDexApplication() {
//    var appComponent: AppComponent
//        internal set
//    internal var context: Context
//
//    override fun onCreate() {
//        super.onCreate()
//        context = this
//        appComponent = DaggerAppComponent.builder()
//                .appModule(AppModule(this))
//                .utilsModule(UtilsModule())
//                .build()
//    }
//
//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//    }

//    val appComponent: AppComponent by lazy {
//        DaggerAppComponent
//                .builder()
//                .appModule(AppModule(this))
//                .build()
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        appComponent.inject(this)
//    }

}
