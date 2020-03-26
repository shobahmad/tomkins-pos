package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.data.local.dao.EventHargaDetDao;
import com.erebor.tomkins.pos.data.local.model.EventHargaDetDBModel;
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

public class SyncEventHargaDetWorker extends BaseSyncWorker<EventHargaDetDBModel, EventHargaDetDao> {
    @Inject
    Logger logger;
    @Inject
    EventHargaDetDao eventHargaDao;
    @Inject
    TomkinsService tomkinsService;
    @Inject
    DateConverterHelper dateConverterHelper;


    public SyncEventHargaDetWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    EventHargaDetDao getDao() {
        return eventHargaDao;
    }

    @Nullable
    @Override
    Date getLastItemUpdate() {
        if (eventHargaDao.getSyncLatest() == null) {
            return null;
        }
        return eventHargaDao.getSyncLatest().getLastUpdate();
    }

    @Override
    Call<RestResponse<DownloadResponse<List<EventHargaDetDBModel>>>> getDataFromApi(Date lastUpdate) {
        return tomkinsService.getEventHargaDet(dateConverterHelper.toDateTimeString(lastUpdate));
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }
}
