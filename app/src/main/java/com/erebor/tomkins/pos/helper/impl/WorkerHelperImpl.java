package com.erebor.tomkins.pos.helper.impl;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.worker.SyncMsArtWorker;
import com.erebor.tomkins.pos.worker.SyncMsBarcodeWorker;
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
                add(new WorkerRequest(SyncMsArtWorker.class, 50, getDownloadingText(R.string.download_worker_art)));
                add(new WorkerRequest(SyncMsBarcodeWorker.class, 100, getDownloadingText(R.string.download_worker_barcode)));
            }
        };
    }

    private String getDownloadingText(int stringResource) {
        return resourceHelper.getResourceString(R.string.downloading) + " " + resourceHelper.getResourceString(stringResource);
    }

}
