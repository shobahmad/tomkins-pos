package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.MsBrandDao;
import com.erebor.tomkins.pos.data.local.model.MsBrandDBModel;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class SyncMsBrandDownloadWorker extends BaseSyncDownloadWorker<MsBrandDBModel, MsBrandDao> {

    @Inject
    Logger logger;
    @Inject
    MsBrandDao msBrandDao;
    @Inject
    TomkinsService tomkinsService;
    @Inject
    DateConverterHelper dateConverterHelper;

    public SyncMsBrandDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    MsBrandDao getDao() {
        return msBrandDao;
    }

    @Nullable
    @Override
    Date getLastItemUpdate() {
        if (msBrandDao.getSyncLatest() == null) {
            return null;
        }
        return msBrandDao.getSyncLatest().getLastUpdate();
    }

    @Override
    Call<RestResponse<DownloadResponse<List<MsBrandDBModel>>>> getDataFromApi(Date lastUpdate) {
        return tomkinsService.getMsBrand(dateConverterHelper.toDateTimeString(lastUpdate));
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }
}
