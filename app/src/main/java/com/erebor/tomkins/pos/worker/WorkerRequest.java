package com.erebor.tomkins.pos.worker;

import com.erebor.tomkins.pos.base.BaseWorker;

public class WorkerRequest {
    private Class worker;
    private int progressUpdate;
    private String messageInfo;

    public WorkerRequest(Class<? extends BaseWorker> worker, int progressUpdate, String messageInfo) {
        this.worker = worker;
        this.progressUpdate = progressUpdate;
        this.messageInfo = messageInfo;
    }
    public WorkerRequest(Class<? extends BaseWorker> worker, String messageInfo) {
        this.worker = worker;
        this.messageInfo = messageInfo;
    }

    public Class getWorker() {
        return worker;
    }

    public int getProgressUpdate() {
        return progressUpdate;
    }

    public String getMessageInfo() {
        return messageInfo;
    }
}
