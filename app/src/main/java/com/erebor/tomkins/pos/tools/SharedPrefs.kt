package com.erebor.tomkins.pos.tools

interface SharedPrefs {

    var latestSyncDownloadDate: Long

    var username: String

    var password: String

    var hostname: String

    fun getString(key: String, defaultValue: String): String
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

}
