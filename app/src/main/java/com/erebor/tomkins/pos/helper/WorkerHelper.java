package com.erebor.tomkins.pos.helper;

import com.erebor.tomkins.pos.worker.WorkerRequest;

import java.util.List;

public interface WorkerHelper {
    List<WorkerRequest> getDownloadWorkerRequest();
    WorkerRequest getUploadTrxWorkerRequest();
    WorkerRequest getUploadStockWorkerRequest();
}
