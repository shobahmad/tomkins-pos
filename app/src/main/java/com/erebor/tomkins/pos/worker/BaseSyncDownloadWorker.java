package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.BuildConfig;
import com.erebor.tomkins.pos.base.BaseDao;
import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.erebor.tomkins.pos.base.BaseWorker;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;

public abstract class BaseSyncDownloadWorker<T extends BaseDatabaseModel, D extends BaseDao> extends BaseWorker {
    public static final String KEY_EXCEPTION_MESSAGE = "key_exception";
    public static final String KEY_REQUEST_ID = "key_request_id";

    private D dao;

    public BaseSyncDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        dao = getDao();
    }

    abstract Logger getLogger();
    abstract D getDao();
    abstract SharedPrefs getSharedPrefs();

    List<T> getDataWithLastUpdate(List<T> data) throws ParseException {
        List<T> dataModels = new ArrayList<>();
        dataModels.addAll(data);

        for (T item : dataModels) {
            if (item.getLastUpdate() == null)
                item.setLastUpdate(getAplicationStartDate());
        }
        return dataModels;
    }

    List<Long> doInsert(List<T> data) throws ParseException {
        for (T datum : data) {
            if (datum.getLastUpdate() == null) {
                datum.setLastUpdate(getAplicationStartDate());
            }
        }
        return dao.insertAllReplaceSync(data);
    }

    @Nullable
    abstract Date getLastItemUpdate();

    abstract Call<RestResponse<DownloadResponse<List<T>>>>getDataFromApi(Date lastUpdate);

    DownloadResponse<List<T>> callApi(Date lastUpdate) throws Exception {
        return new NetworkBoundResult<DownloadResponse<List<T>>>() {
            @Override
            protected Call<RestResponse<DownloadResponse<List<T>>>> callApiAction() {
                return getDataFromApi(lastUpdate);
            }
        }.fetchData();
    }

    public String getLogTag() {
        return getClass().getSimpleName();
    }

    private void saveToLocalStorage(List<T> data) throws Exception {
        long startTime = System.currentTimeMillis();
        AtomicInteger insertResult = new AtomicInteger();
        if (data == null) {
            throw new Exception("Empty data");
        }
        List<Long> insertResults = doInsert(data);
        for (Long insert : insertResults) {
            if (insert < 1)
                break;

            insertResult.getAndIncrement();
        }
        getLogger().debug(getLogTag(), "["+(System.currentTimeMillis() - startTime)+"ms] saveToLocalStorage data size : " + data.size() + " vs " + insertResult.get());
        if (insertResult.get() != data.size())
            throw new Exception("Insert data not match");
    }

    protected List<T> download() throws Exception {
        DownloadResponse<List<T>> downloadResponse = callApi(getLastItemUpdate());
        return getDataWithLastUpdate(downloadResponse.getData());
    }

    private Data getSuccessOutputData() {
        long requestId = getInputData().getLong(KEY_REQUEST_ID, 0);
        return new Data.Builder()
                .putLong(KEY_REQUEST_ID, requestId)
                .build();
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            if (!isValidSession()) {
                Data data = new Data.Builder()
                        .putString(KEY_EXCEPTION_MESSAGE, "Unauthorized access")
                        .build();
                return Result.failure(data);
            }
            saveToLocalStorage(download());
            Date lastUpdate = getLastItemUpdate();
            Date appStartDate = getAplicationStartDate();
            if (lastUpdate.equals(appStartDate)) {
                //retry
                saveToLocalStorage(download());
            }
        } catch (Exception e) {
            getLogger().error(getLogTag(), e.getMessage(), e);
            Data data = new Data.Builder()
                    .putString(KEY_EXCEPTION_MESSAGE, e.getMessage())
                    .build();
            return Result.failure(data);
        }

        return Result.success(getSuccessOutputData());
    }

    private boolean isValidSession() {
        return !getSharedPrefs().getUsername().isEmpty();
    }

    private Date getAplicationStartDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.parse(BuildConfig.StartDate);
    }
}
