package com.erebor.tomkins.pos.di


import com.erebor.tomkins.pos.tools.SharedPrefs

import java.io.IOException
import java.net.UnknownHostException

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request

class HostSelectionInterceptor internal constructor(private val sharedPrefs: SharedPrefs) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        val oldHostname = request.url().host()
        val newHostname = HttpUrl.parse(sharedPrefs.hostname)!!.host()
        val builder = request.url().newBuilder(
                request.url().toString()
                        .replace(oldHostname.toRegex(), newHostname)
                        .replace("%3A".toRegex(), ":")
        )
                ?: throw UnknownHostException()

        request = request.newBuilder()
                .url(builder.build())
                .build()
        return chain.proceed(request)
    }
}
