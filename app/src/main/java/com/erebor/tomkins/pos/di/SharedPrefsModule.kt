package com.erebor.tomkins.pos.di


import android.content.Context
import android.content.SharedPreferences

import androidx.preference.PreferenceManager

import com.erebor.tomkins.pos.BuildConfig
import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.tools.SecurityEncryption
import com.erebor.tomkins.pos.tools.SharedPrefs

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class SharedPrefsModule {


    @Provides
    @Singleton
    internal fun providesSharedPreferences(context: Context, encryption: SecurityEncryption): SharedPrefs {
        return object : SharedPrefs {

            internal var sharedPreferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(context)


            override var latestSyncDownloadDate: Long
                get() = getLong(getKey(R.string.setting_key_latest_download_date), 0)!!
                set(time) {
                    val editor = sharedPreferences!!.edit()
                    editor.putLong(getKey(R.string.setting_key_latest_download_date), time)
                    editor.apply()
                }

            override var username: String
                get() {
                    try {
                        return encryption.aesDecrypt(getString(getKey(R.string.session_key_username), ""))
                    } catch (e: Exception) {
                        return ""
                    }

                }
                set(username) {
                    val editor = sharedPreferences!!.edit()
                    try {
                        editor.putString(getKey(R.string.session_key_username), encryption.aesEncrypt(username))
                    } catch (e: Exception) {
                    }

                    editor.apply()
                }

            override var password: String
                get() {
                    try {
                        return encryption.aesDecrypt(getString(getKey(R.string.session_key_password), ""))
                    } catch (e: Exception) {
                        return ""
                    }

                }
                set(password) {
                    val editor = sharedPreferences!!.edit()
                    try {
                        editor.putString(getKey(R.string.session_key_password), encryption.aesEncrypt(password))
                    } catch (e: Exception) {
                    }

                    editor.apply()
                }

            override var hostname: String
                get() = getString(getKey(R.string.setting_key_server_config), BuildConfig.HOSTNAME)
                set(hostname) {
                    val editor = sharedPreferences!!.edit()
                    try {
                        editor.putString(getKey(R.string.setting_key_server_config), hostname)
                    } catch (e: Exception) {
                    }

                    editor.apply()
                }

            private fun getKey(resource: Int): String {
                return context.resources.getString(resource)
            }

            override fun getString(key: String, defaultValue: String): String {
                return if (sharedPreferences == null) defaultValue else sharedPreferences!!.getString(key, defaultValue)
            }

            fun getLong(key: String, defaultValue: Long): Long? {
                return if (sharedPreferences == null) defaultValue else sharedPreferences!!.getLong(key, defaultValue)
            }

            fun getInt(key: String, defaultValue: Int): Int {
                return if (sharedPreferences == null) defaultValue else sharedPreferences!!.getInt(key, defaultValue)
            }

            override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
                return if (sharedPreferences == null) defaultValue else sharedPreferences!!.getBoolean(key, defaultValue)
            }
        }
    }
}
