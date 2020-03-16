package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.MsArtDao;
import com.erebor.tomkins.pos.data.local.dao.MsUkuranDao;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsUkuranDBModel;
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

public class SyncMsUkuranWorker extends BaseSyncWorker<MsUkuranDBModel, MsUkuranDao> {
    @Inject
    Logger logger;
    @Inject
    MsUkuranDao msUkuranDao;
    @Inject
    TomkinsService tomkinsService;
    @Inject
    DateConverterHelper dateConverterHelper;


    public SyncMsUkuranWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    MsUkuranDao getDao() {
        return msUkuranDao;
    }

    @Nullable
    @Override
    Date getLastItemUpdate() {
        if (msUkuranDao.getSyncLatest() == null) {
            return null;
        }
        return msUkuranDao.getSyncLatest().getLastUpdate();
    }

    @Override
    Call<RestResponse<DownloadResponse<List<MsUkuranDBModel>>>> getDataFromApi(Date lastUpdate) {
        return tomkinsService.getMsUkuran(dateConverterHelper.toDateTimeString(lastUpdate));
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }
}
