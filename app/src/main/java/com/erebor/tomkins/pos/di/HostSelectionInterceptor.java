package com.erebor.tomkins.pos.di;


import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class HostSelectionInterceptor implements Interceptor {

    private SharedPrefs sharedPrefs;

    HostSelectionInterceptor(SharedPrefs sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String oldHostname = request.url().host();
        String newHostname = HttpUrl.parse(sharedPrefs.getHostname()).host();
        HttpUrl.Builder builder = request.url().newBuilder(
                request.url().toString()
                        .replaceAll(oldHostname, newHostname)
                        .replaceAll("%3A", ":")
        );
        if (builder == null) {
            throw new UnknownHostException();
        }

        request = request.newBuilder()
                .url(builder.build())
                .build();
        return chain.proceed(request);
    }
}
