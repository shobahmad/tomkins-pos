package com.erebor.tomkins.pos.helper.impl;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.worker.SyncEventHargaDetDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncEventHargaDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsArtDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsBarcodeDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsBrandDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsGenderDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncMsUkuranDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncStokRealDownloadWorker;
import com.erebor.tomkins.pos.worker.SyncUploadStockWorker;
import com.erebor.tomkins.pos.worker.SyncUploadTransactionWorker;
import com.erebor.tomkins.pos.worker.WorkerRequest;

import java.util.ArrayList;
import java.util.List;

public class WorkerHelperImpl implements WorkerHelper {

    ResourceHelper resourceHelper;

    public WorkerHelperImpl(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    @Override
    public List<WorkerRequest> getDownloadWorkerRequest() {
        return new ArrayList<WorkerRequest>() {
            {
                add(new WorkerRequest(SyncMsArtDownloadWorker.class, 16, getDownloadingText(R.string.download_worker_art)));
                add(new WorkerRequest(SyncMsBarcodeDownloadWorker.class, 32, getDownloadingText(R.string.download_worker_barcode)));
                add(new WorkerRequest(SyncMsBrandDownloadWorker.class, 48, getDownloadingText(R.string.download_worker_brand)));
                add(new WorkerRequest(SyncMsGenderDownloadWorker.class, 64, getDownloadingText(R.string.download_worker_gender)));
                add(new WorkerRequest(SyncMsUkuranDownloadWorker.class, 75, getDownloadingText(R.string.download_worker_ukuran)));
                add(new WorkerRequest(SyncStokRealDownloadWorker.class, 80, getDownloadingText(R.string.download_worker_stock)));
                add(new WorkerRequest(SyncEventHargaDownloadWorker.class, 90, getDownloadingText(R.string.download_worker_event)));
                add(new WorkerRequest(SyncEventHargaDetDownloadWorker.class, 100, getDownloadingText(R.string.download_worker_event_detail)));
            }
        };
    }

    @Override
    public WorkerRequest getUploadTrxWorkerRequest() {
        return new WorkerRequest(SyncUploadTransactionWorker.class, 100, getUploadingText(R.string.transaction));
    }

    @Override
    public WorkerRequest getUploadStockWorkerRequest() {
        return new WorkerRequest(SyncUploadStockWorker.class, 100, getUploadingText(R.string.stock));
    }

    private String getDownloadingText(int stringResource) {
        return resourceHelper.getResourceString(R.string.downloading) + " " + resourceHelper.getResourceString(stringResource);
    }
    private String getUploadingText(int stringResource) {
        return resourceHelper.getResourceString(R.string.uploading) + " " + resourceHelper.getResourceString(stringResource);
    }

}
