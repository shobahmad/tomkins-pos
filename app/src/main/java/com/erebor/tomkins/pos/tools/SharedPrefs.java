package com.erebor.tomkins.pos.tools;

public interface SharedPrefs {

    String getString(String key, String defaultValue);
    boolean getBoolean(String key, boolean defaultValue);

    long getLatestSyncDownloadDate();
    void setLatestSyncDownloadDate(long time);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    String getHostname();
    void setHostname(String hostname);

    void setDownloadRequestId(long aLong);
    long getDownloadRequestId();

    int getSyncAutoDownloadInterval();

}
