package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.MsGenderDao;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class SyncMsGenderDownloadWorker extends BaseSyncDownloadWorker<MsGenderDBModel, MsGenderDao> {

    @Inject
    Logger logger;
    @Inject
    MsGenderDao msGenderDao;
    @Inject
    TomkinsService tomkinsService;
    @Inject
    DateConverterHelper dateConverterHelper;
    @Inject
    SharedPrefs sharedPrefs;

    public SyncMsGenderDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    MsGenderDao getDao() {
        return msGenderDao;
    }

    @Override
    SharedPrefs getSharedPrefs() {
        return sharedPrefs;
    }

    @Nullable
    @Override
    Date getLastItemUpdate() {
        if (msGenderDao.getSyncLatest() == null) {
            return null;
        }
        return msGenderDao.getSyncLatest().getLastUpdate();
    }

    @Override
    Call<RestResponse<DownloadResponse<List<MsGenderDBModel>>>> getDataFromApi(Date lastUpdate) {
        return tomkinsService.getMsGender(dateConverterHelper.toDateTimeString(lastUpdate));
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }
}
