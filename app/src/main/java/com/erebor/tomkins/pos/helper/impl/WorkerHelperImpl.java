package com.erebor.tomkins.pos.helper.impl;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.worker.DownloadMsArtWorker;
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
                add(new WorkerRequest(DownloadMsArtWorker.class, 100, getDownloadingText(R.string.download_worker_art)));
            }
        };
    }

    private String getDownloadingText(int stringResource) {
        return resourceHelper.getResourceString(R.string.downloading) + " " + resourceHelper.getResourceString(stringResource);
    }

}
