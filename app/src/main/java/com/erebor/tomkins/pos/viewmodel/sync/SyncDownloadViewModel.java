package com.erebor.tomkins.pos.viewmodel.sync;

import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.worker.BaseSyncDownloadWorker;
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

public class SyncDownloadViewModel extends BaseViewModel<SyncDownloadViewState> {

    private static final String SYNC_WORK_NAME = "sync_work";
    private static final String TAG = "DataSyncViewModel";

    private WorkManager workManager;
    private Logger logger;
    private SharedPrefs sharedPrefs;
    private WorkerHelper workerHelper;

    private int progress = 0;

    private Map<String, Observer<List<WorkInfo>>> observerHashMap = new HashMap<>();

    @Inject
    public SyncDownloadViewModel(WorkManager workManager, Logger logger, SharedPrefs sharedPrefs, WorkerHelper workerHelper) {
        this.workManager = workManager;
        this.logger = logger;
        this.sharedPrefs = sharedPrefs;
        this.workerHelper = workerHelper;
    }

    public void observeChanged() {
        SyncDownloadViewState.WAITING_STATE.setLastDownloadTime(getLatestDownloadedDate());
        setValue(SyncDownloadViewState.WAITING_STATE);
        addObservers();
        startSync();
    }

    private void startSync() {
        if (getLatestDownloadedDate() != 0) {
            cancelEnqueued();
            long curtime = System.currentTimeMillis();
            long intervalInMilis = sharedPrefs.getSyncAutoDownloadInterval() * 1000 * 60;

            int deviationInMinutes = ((Number) (curtime - intervalInMilis / (1000 * 60))).intValue();
            int delay = curtime - getLatestDownloadedDate() >= intervalInMilis ? 0 : deviationInMinutes;
            makeRequest(delay);
            return;
        }
        startSyncFull();
    }

    private void addObservers() {
        for (WorkerRequest workerRequest : workerHelper.getDownloadWorkerRequest()) {
            Observer<List<WorkInfo>> observer = getObserver(workerRequest.getProgressUpdate());
            workManager.getWorkInfosByTagLiveData(
                    workerRequest.getWorker().getSimpleName()
            ).observeForever(observer);

            observerHashMap.put(
                    workerRequest.getWorker().getSimpleName(),
                    observer
            );
        }
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

    public Long getLatestDownloadedDate() {
        return sharedPrefs.getLatestSyncDownloadDate();
    }

    @SuppressWarnings("unchecked")
    private void makeRequest(int delay) {
        if (sharedPrefs.getUsername().isEmpty()) {
            cancelEnqueued();
            logger.debug(TAG, "makeRequest cancelled, invalid session");
            return;
        }
        if (delay > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.add(Calendar.MINUTE, delay);
            calendar.add(Calendar.SECOND, 30);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            logger.debug(TAG, "makeRequest at " + simpleDateFormat.format(calendar.getTime()));
        }

        long requestId = System.currentTimeMillis();
        SyncDownloadViewState.WAITING_STATE.setLastDownloadTime(getLatestDownloadedDate());
        setValue(SyncDownloadViewState.WAITING_STATE);

        OneTimeWorkRequest.Builder initRequest = buildWorkerRequest(
                requestId,
                workerHelper.getDownloadWorkerRequest().get(0).getWorker())
                .setInitialDelay(delay, TimeUnit.MINUTES);

        WorkContinuation continuation = workManager
                .beginUniqueWork(SYNC_WORK_NAME,
                        ExistingWorkPolicy.KEEP,
                        initRequest.build());
        for (int i = 1; i < workerHelper.getDownloadWorkerRequest().size(); i++) {
            WorkerRequest workerRequest = workerHelper.getDownloadWorkerRequest().get(i);
            continuation = continuation.then(buildWorkerRequest(
                    requestId,
                    workerRequest.getWorker())
                    .build());
        }
        continuation
                .enqueue();
    }

    private String getProgressMessage(int progress) {
        List<WorkerRequest> workerRequests = workerHelper.getDownloadWorkerRequest();
        if (workerRequests.isEmpty()) {
            return progress + "%";
        }
        if (progress == 100) {
            return workerRequests.get(workerRequests.size() - 1).getMessageInfo();
        }
        if (progress <= workerRequests.get(0).getProgressUpdate()) {
            return workerRequests.get(0).getMessageInfo();
        }

        for (int j = 0; j < workerRequests.size(); j++) {
            int start = workerRequests.get(j).getProgressUpdate();
            int end = j == workerRequests.size() - 1 ? 100 : workerRequests.get(j + 1).getProgressUpdate();

            if (progress == start) {
                return j == 0 ? workerRequests.get(j).getMessageInfo() : workerRequests.get(j - 1).getMessageInfo();
            }

            if (progress > start && progress <= end) {
                return j == workerRequests.size() - 1 ? workerRequests.get(j).getMessageInfo() : workerRequests.get(j + 1).getMessageInfo();
            }
        }

        return progress + "%";
    }

    private OneTimeWorkRequest.Builder buildWorkerRequest(long requestId, Class<? extends BaseSyncDownloadWorker> worker) {
        Data data = new Data.Builder()
                .putLong(BaseSyncDownloadWorker.KEY_REQUEST_ID, requestId)
                .build();

        return new OneTimeWorkRequest.Builder(worker)
                .addTag(worker.getSimpleName())
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .setInputData(data);
    }

    private void handleWorkerCallback(WorkInfo info, Data data, int workerProgress) {
        WorkInfo.State state = info.getState();
        long requestId = data.getLong(BaseSyncDownloadWorker.KEY_REQUEST_ID, 0);
        boolean completedRequestBefore = data.getLong(BaseSyncDownloadWorker.KEY_REQUEST_ID, 0) == sharedPrefs.getDownloadRequestId();

        if (state.equals(WorkInfo.State.BLOCKED)) {
            return;
        }
        if (state.equals(WorkInfo.State.CANCELLED)) {
            return;
        }
        if (state.equals(WorkInfo.State.ENQUEUED)) {
            return;
        }

        if (state.equals(WorkInfo.State.RUNNING) && progress < 100) {
            int updateProgress = progress + ((workerProgress - progress) / 2);
            String message = getProgressMessage(updateProgress) + "... ";
            logger.debug(TAG, "RUNNING " + info.getTags().toArray()[0] + " | Updating progress : " + updateProgress + " --> " + message);
            SyncDownloadViewState.LOADING_STATE.setMessage(message);
            SyncDownloadViewState.LOADING_STATE.setProgress(progress);
            setValue(SyncDownloadViewState.LOADING_STATE);
            return;
        }

        if (state.equals(WorkInfo.State.SUCCEEDED)) {
            progress = workerProgress;
        }


        if (state.equals(WorkInfo.State.SUCCEEDED) && progress == 100 && completedRequestBefore) {
            logger.debug(TAG, "SKIP " + info.getTags().toArray()[0] + " SUCCEEDED from previous request");
            return;
        }

        if (state.equals(WorkInfo.State.SUCCEEDED) && progress == 100) {
            sharedPrefs.setDownloadRequestId(data.getLong(BaseSyncDownloadWorker.KEY_REQUEST_ID, 0));
            logger.debug(TAG, "SUCCEEDED " + info.getTags().toArray()[0] + " | Updating progress : " + progress);
            sharedPrefs.setLatestSyncDownloadDate(System.currentTimeMillis());
            SyncDownloadViewState.SUCCESS_STATE.setLastDownloadTime(getLatestDownloadedDate());
            SyncDownloadViewState.SUCCESS_STATE.setProgress(progress);
            setValue(SyncDownloadViewState.SUCCESS_STATE);
            makeRequest(sharedPrefs.getSyncAutoDownloadInterval());
            resetProgress(true);
            return;
        }

        String message = data.getString(BaseSyncDownloadWorker.KEY_EXCEPTION_MESSAGE);
        if (state.equals(WorkInfo.State.FAILED) && message != null) {
            int updateProgress = progress + ((workerProgress - progress) / 2);
            String workerTitle = getProgressMessage(updateProgress);

            logger.debug(TAG, "[" + requestId + "]FAILED " + info.getTags().toArray()[0] + " | Updating progress : " + message);
            SyncDownloadViewState.ERROR_STATE.setMessage(workerTitle + " " + message);
            SyncDownloadViewState.ERROR_STATE.setProgress(progress);
            setValue(SyncDownloadViewState.ERROR_STATE);
            return;
        }

    }

    private void resetProgress(boolean finish) {
        progress = 0;
        logger.debug(TAG, "Updating progress : " + (finish ? 100 : 0) + " --> " + (finish ? "Download complete" : "Starting..."));

    }

    public void startSyncFull() {
        cancelEnqueued();
        makeRequest(0);
    }
    private void cancelEnqueued() {
        boolean enqueued = isEnqueued();
        if (!enqueued) {
            logger.debug(TAG, "No work to cancel");
            return;
        }

        logger.debug(TAG, "cancel enqueued work");
        workManager.cancelUniqueWork(SYNC_WORK_NAME);
        resetProgress(false);
    }

    private boolean isEnqueued() {
        ListenableFuture<List<WorkInfo>> listListenableFuture = workManager.getWorkInfosByTag(BaseSyncDownloadWorker.class.getName());
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

    private void removeObservers() {
        if (observerHashMap.isEmpty())
            return;

        for (WorkerRequest workerRequest : workerHelper.getDownloadWorkerRequest()) {
            Observer<List<WorkInfo>> observer = observerHashMap.get(
                    workerRequest.getWorker().getSimpleName()
            );

            if (observer != null) {
                workManager.getWorkInfosByTagLiveData(
                        workerRequest.getWorker().getSimpleName()
                ).removeObserver(observer);
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        removeObservers();
    }
}
