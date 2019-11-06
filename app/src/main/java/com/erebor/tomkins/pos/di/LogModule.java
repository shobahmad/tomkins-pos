package com.erebor.tomkins.pos.di;

import android.util.Log;

import com.erebor.tomkins.pos.tools.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LogModule {

    @Provides
    @Singleton
    Logger provideLogger() {
        return new Logger() {
            @Override
            public void info(String tag, String message) {
                Log.i(tag, message);
            }

            @Override
            public void debug(String tag, String message) {
                Log.d(tag, message);
            }

            @Override
            public void warning(String tag, String message) {
                Log.w(tag, message);
            }

            @Override
            public void error(String tag, String message, Throwable throwable) {
                Log.e(tag, message, throwable);

            }
        };
    }

}
