package com.erebor.tomkins.pos.helper.impl;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.worker.SyncMsArtWorker;
import com.erebor.tomkins.pos.worker.SyncMsBarcodeWorker;
import com.erebor.tomkins.pos.worker.SyncMsBrandWorker;
import com.erebor.tomkins.pos.worker.SyncMsGenderWorker;
import com.erebor.tomkins.pos.worker.SyncMsUkuranWorker;
import com.erebor.tomkins.pos.worker.WorkerRequest;

import java.util.ArrayList;
import java.util.List;

public class WorkerHelperImpl implements WorkerHelper {

    ResourceHelper resourceHelper;

    public WorkerHelperImpl(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    @Override
    public List<WorkerRequest> getWorkerRequest() {
        return new ArrayList<WorkerRequest>() {
            {
                add(new WorkerRequest(SyncMsArtWorker.class, 25, getDownloadingText(R.string.download_worker_art)));
                add(new WorkerRequest(SyncMsBarcodeWorker.class, 50, getDownloadingText(R.string.download_worker_barcode)));
                add(new WorkerRequest(SyncMsBrandWorker.class, 75, getDownloadingText(R.string.download_worker_brand)));
                add(new WorkerRequest(SyncMsGenderWorker.class, 99, getDownloadingText(R.string.download_worker_gender)));
                add(new WorkerRequest(SyncMsUkuranWorker.class, 100, getDownloadingText(R.string.download_worker_ukuran)));
            }
        };
    }

    private String getDownloadingText(int stringResource) {
        return resourceHelper.getResourceString(R.string.downloading) + " " + resourceHelper.getResourceString(stringResource);
    }

}
