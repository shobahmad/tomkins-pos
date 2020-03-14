package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.base.BaseWorker;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseDownloadWorker<T> extends BaseWorker {
    public static final String KEY_EXCEPTION_MESSAGE = "key_exception";
    public static final String KEY_REQUEST_ID = "key_request_id";

    public BaseDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    abstract Logger getLogger();

    abstract List<Long> doInsert(List<T> data);

    @Nullable
    abstract Date getLastItemUpdate();

    abstract List<T> callApi(Date lastUpdate) throws Exception;

    public String getLogTag() {
        return getClass().getSimpleName();
    }

    private void saveToLocalStorage(List<T> data) throws Exception {
        long startTime = System.currentTimeMillis();
        AtomicInteger insertResult = new AtomicInteger();
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

    private List<T> download() throws Exception {
        return callApi(getLastItemUpdate());
    }

    private Data getSuccessOutputData() {
        long requestId = getInputData().getLong(KEY_REQUEST_ID, 0);
        return new Data.Builder()
                .putLong(KEY_REQUEST_ID, requestId)
                .build();
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        try {
            saveToLocalStorage(download());
        } catch (Exception e) {
            getLogger().error(getLogTag(), e.getMessage(), e);
            Data data = new Data.Builder()
                    .putString(KEY_EXCEPTION_MESSAGE, e.getMessage())
                    .build();
            return ListenableWorker.Result.failure(data);
        }

        return ListenableWorker.Result.success(getSuccessOutputData());
    }
}
