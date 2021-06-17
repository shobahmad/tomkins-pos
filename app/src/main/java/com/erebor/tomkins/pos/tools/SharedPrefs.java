package com.erebor.tomkins.pos.tools;

public interface SharedPrefs {

    String getString(String key, String defaultValue);
    boolean getBoolean(String key, boolean defaultValue);

    long getLatestSyncDownloadDate();
    void setLatestSyncDownloadDate(long time);

    long getLatestSyncUploadDate();
    void setLatestSyncUploadDate(long time);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    String getKodeKonter();
    void setKodeKonter(String KodeKonter);

    String getNamaKonter();
    void setNamaKonter(String NamaKonter);

    String getKodeSPG();
    void setKodeSPG(String KodeSPG);

    String getNamaSPG();
    void setNamaSPG(String NamaSPG);

    boolean isPrimarySPG();
    void setPrimarySPG(boolean primarySPG);

    String getToken();
    void setToken(String token);

    String getHostname();
    void setHostname(String hostname);

    void setDownloadRequestId(long aLong);
    long getDownloadRequestId();

    int getSyncAutoDownloadInterval();

}
