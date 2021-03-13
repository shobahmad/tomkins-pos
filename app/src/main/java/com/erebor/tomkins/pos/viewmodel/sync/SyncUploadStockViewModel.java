package com.erebor.tomkins.pos.viewmodel.sync;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.worker.SyncUploadStockWorker;
import com.erebor.tomkins.pos.worker.WorkerRequest;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class SyncUploadStockViewModel extends BaseViewModel<SyncUploadViewState> {

    private static final String SYNC_WORK_NAME = "upload_stock_work";
    private static final String TAG = "SyncUploadStockViewModel";
    private final WorkManager workManager;
    private final Logger logger;
    private final SharedPrefs sharedPrefs;
    private final WorkerHelper workerHelper;
    private final StokRealDao stokRealDao;

    private final int UPLOAD_INTERVAL_MINUTES = 5;

    private Map<String, Observer<List<WorkInfo>>> observerHashMap = new HashMap<>();

    private final MutableLiveData<Integer> pendingCountLiveData;

    @Inject
    public SyncUploadStockViewModel(WorkManager workManager, Logger logger, SharedPrefs sharedPrefs, 
                                    WorkerHelper workerHelper, StokRealDao stokRealDao) {
        this.workManager = workManager;
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
        this.workerHelper = workerHelper;
        this.stokRealDao = stokRealDao;

        this.pendingCountLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getPendingCountLiveData() {
        return pendingCountLiveData;
    }

    public void observeChanged() {
        addObservers();
        startSync();
        getPendingCount();
    }

    private void getPendingCount() {
        getDisposable().add(stokRealDao.getUnuploadedCount()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(unuploadedCount -> pendingCountLiveData.postValue(unuploadedCount))
        );
    }

    private void addObservers() {
            WorkerRequest workerRequest = workerHelper.getUploadStockWorkerRequest();
            Observer<List<WorkInfo>> observer = getObserver(workerRequest.getProgressUpdate());
            workManager.getWorkInfosByTagLiveData(
                    workerRequest.getWorker().getSimpleName()
            ).observeForever(observer);

            observerHashMap.put(
                    workerRequest.getWorker().getSimpleName(),
                    observer
            );
    }

    private void startSync() {
        if (sharedPrefs.getLatestSyncUploadDate() != 0) {
            cancelEnqueued();
            long curtime = System.currentTimeMillis();

            long diff = curtime - sharedPrefs.getLatestSyncUploadDate();
            long diffMinutes = diff / (60 * 1000) % 60;

            logger.debug(getClass().getSimpleName(), "diffMinutes: " + diffMinutes);
            long delay = diffMinutes >= UPLOAD_INTERVAL_MINUTES ? 0 : diffMinutes;

            makeRequest(((Number) delay).intValue());
            return;
        }
        startSyncFull();
    }

    public void startSyncFull() {
        cancelEnqueued();
        makeRequest(0);
    }


    private Observer<List<WorkInfo>> getObserver(int progress) {
        return workInfos -> {
            if (workInfos.isEmpty())
                return;
            handleWorkerCallback(
                    workInfos.get(0),
                    workInfos.get(0).getOutputData(),
                    progress
            );
        };
    }


    private void handleWorkerCallback(WorkInfo info, Data data, int workerProgress) {
        WorkInfo.State state = info.getState();

        if (state.equals(WorkInfo.State.BLOCKED)) {
            setValue(SyncUploadViewState.WAITING_STATE);
            return;
        }
        if (state.equals(WorkInfo.State.CANCELLED)) {
            setValue(SyncUploadViewState.WAITING_STATE);
            return;
        }

        if (state.equals(WorkInfo.State.ENQUEUED)) {
            logger.debug(TAG, "ENQUEUED " + info.getTags().toArray()[0]);
            setValue(SyncUploadViewState.WAITING_STATE);
            return;
        }

        if (state.equals(WorkInfo.State.RUNNING)) {
            logger.debug(TAG, "RUNNING " + info.getTags().toArray()[0]);
            setValue(SyncUploadViewState.LOADING_STATE);
            return;
        }

        if (state.equals(WorkInfo.State.SUCCEEDED)) {
            sharedPrefs.setLatestSyncUploadDate(System.currentTimeMillis());
            setValue(SyncUploadViewState.SUCCESS_STATE);
            makeRequest(UPLOAD_INTERVAL_MINUTES);
            return;
        }


        String message = data.getString(SyncUploadStockWorker.KEY_EXCEPTION_MESSAGE);
        if (state.equals(WorkInfo.State.FAILED) && message != null) {
            logger.debug(TAG, "FAILED " + info.getTags().toArray()[0] +" -> " + message);
            SyncUploadViewState.ERROR_STATE.setError(new Exception(message));
            setValue(SyncUploadViewState.ERROR_STATE);
        }

    }

    private void makeRequest(int delay) {
        if (sharedPrefs.getUsername().isEmpty()) {
            cancelEnqueued();
            logger.debug(TAG, "makeRequest cancelled, invalid session");
            return;
        }
        if (delay > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, delay);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            logger.debug(TAG, "makeRequest at " + simpleDateFormat.format(calendar.getTime()) +" after add delay " + delay +" minutes");
        }

        if (delay == 0) {
            logger.debug(TAG, "makeRequest no delay");
        }

        long requestId = System.currentTimeMillis();
        setValue(SyncUploadViewState.WAITING_STATE);

        OneTimeWorkRequest.Builder initRequest = buildWorkerRequest(
                requestId,
                workerHelper.getUploadStockWorkerRequest().getWorker())
                .setInitialDelay(delay, TimeUnit.MINUTES);

        WorkContinuation continuation = workManager
                .beginUniqueWork(SYNC_WORK_NAME,
                        ExistingWorkPolicy.KEEP,
                        initRequest.build());

        continuation
                .enqueue();
    }

    private void cancelEnqueued() {
        boolean enqueued = isEnqueued();
        if (!enqueued) {
            logger.debug(TAG, "No work to cancel");
            return;
        }

        logger.debug(TAG, "cancel enqueued work");
        workManager.cancelUniqueWork(SYNC_WORK_NAME);
    }

    private boolean isEnqueued() {
        ListenableFuture<List<WorkInfo>> listListenableFuture = workManager.getWorkInfosByTag(SyncUploadStockWorker.class.getName());
        try {
            List<WorkInfo> workInfos = listListenableFuture.get();
            if (workInfos.isEmpty()) {
                return false;
            }

            for (WorkInfo workInfo : workInfos) {
                if (workInfo.getState().equals(WorkInfo.State.ENQUEUED)) {
                    return true;
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    private OneTimeWorkRequest.Builder buildWorkerRequest(long requestId, Class<? extends Worker> worker) {
        Data data = new Data.Builder()
                .putLong(SyncUploadStockWorker.KEY_REQUEST_ID, requestId)
                .build();

        return new OneTimeWorkRequest.Builder(worker)
                .addTag(worker.getSimpleName())
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .setInputData(data);
    }


    private void removeObservers() {
        if (observerHashMap.isEmpty())
            return;

        WorkerRequest workerRequest = workerHelper.getUploadStockWorkerRequest();
        Observer<List<WorkInfo>> observer = observerHashMap.get(
                workerRequest.getWorker().getSimpleName()
        );

        if (observer != null) {
            workManager.getWorkInfosByTagLiveData(
                    workerRequest.getWorker().getSimpleName()
            ).removeObserver(observer);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        removeObservers();
    }
}
