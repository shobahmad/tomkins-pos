package com.erebor.tomkins.pos.di

import android.util.Log

import com.erebor.tomkins.pos.tools.Logger

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class LogModule {

    @Provides
    @Singleton
    internal fun provideLogger(): Logger {
        return object : Logger {
            override fun info(tag: String, message: String) {
                Log.i(tag, message)
            }

            override fun debug(tag: String, message: String) {
                Log.d(tag, message)
            }

            override fun warning(tag: String, message: String) {
                Log.w(tag, message)
            }

            override fun error(tag: String, message: String, throwable: Throwable) {
                Log.e(tag, message, throwable)

            }
        }
    }

}
