package com.erebor.tomkins.pos.di;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.erebor.tomkins.pos.BuildConfig;
import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.tools.SecurityEncryption;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {


    @Provides
    @Singleton
    SharedPrefs providesSharedPreferences(Context context, SecurityEncryption encryption) {
        return new SharedPrefs() {

            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);

            private String getKey(int resource) {
                return context.getResources().getString(resource);
            }

            @Override
            public String getString(String key, String defaultValue) {
                return sharedPreferences == null ? defaultValue : sharedPreferences.getString(key, defaultValue);
            }
            public Long getLong(String key, long defaultValue) {
                return sharedPreferences == null ? defaultValue : sharedPreferences.getLong(key, defaultValue);
            }

            public int getInt(String key, int defaultValue) {
                return sharedPreferences == null ? defaultValue : sharedPreferences.getInt(key, defaultValue);
            }

            @Override
            public boolean getBoolean(String key, boolean defaultValue) {
                return sharedPreferences == null ? defaultValue : sharedPreferences.getBoolean(key, defaultValue);
            }


            @Override
            public long getLatestSyncDownloadDate() {
                return getLong(getKey(R.string.setting_key_latest_download_date), 0);
            }

            @Override
            public void setLatestSyncDownloadDate(long time) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(getKey(R.string.setting_key_latest_download_date), time);
                editor.apply();
            }

            @Override
            public String getUsername() {
                try {
                    return encryption.aesDecrypt(getString(getKey(R.string.session_key_username), ""));
                } catch (Exception e) {
                    return "";
                }
            }

            @Override
            public void setUsername(String username) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.session_key_username), encryption.aesEncrypt(username));
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getPassword() {
                try {
                    return encryption.aesDecrypt(getString(getKey(R.string.session_key_password), ""));
                } catch (Exception e) {
                    return "";
                }
            }

            @Override
            public void setPassword(String password) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.session_key_password), encryption.aesEncrypt(password));
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getKodeKonter() {
                return getString(getKey(R.string.key_kode_konter), null);
            }

            @Override
            public void setKodeKonter(String KodeKonter) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.key_kode_konter), KodeKonter);
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getNamaKonter() {
                return getString(getKey(R.string.key_nama_konter), null);
            }

            @Override
            public void setNamaKonter(String NamaKonter) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.key_nama_konter), NamaKonter);
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getKodeSPG() {
                return getString(getKey(R.string.key_kode_spg), null);
            }

            @Override
            public void setKodeSPG(String KodeSPG) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.key_kode_spg), KodeSPG);
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getNamaSPG() {
                return getString(getKey(R.string.key_nama_spg), null);
            }

            @Override
            public void setNamaSPG(String NamaSPG) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.key_nama_spg), NamaSPG);
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getToken() {
                return getString(getKey(R.string.key_token), "");
            }

            @Override
            public void setToken(String token) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.key_token), token);
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public String getHostname() {
                return getString(getKey(R.string.key_setting_host), BuildConfig.HOSTNAME);
            }

            @Override
            public void setHostname(String hostname) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(getKey(R.string.key_setting_host), hostname);
                } catch (Exception e) {
                }
                editor.apply();
            }

            @Override
            public void setDownloadRequestId(long requestId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putLong(getKey(R.string.download_request_id), requestId);
                } catch (Exception ignored) {
                }
                editor.apply();
            }

            @Override
            public long getDownloadRequestId() {
                return getLong(getKey(R.string.download_request_id), 0);
            }

            @Override
            public int getSyncAutoDownloadInterval() {
                String interval = getString(getKey(R.string.setting_key_auto_download_interval), "5");
                try {
                    return Integer.parseInt(interval);
                } catch (NumberFormatException e) {
                    return 5;
                }
            }
        };
    }
}
