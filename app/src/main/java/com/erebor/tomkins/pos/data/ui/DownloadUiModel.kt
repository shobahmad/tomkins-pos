package com.erebor.tomkins.pos.data.ui

data class DownloadUiModel(val isDownloading: Boolean, val progress: Int, val messsage: String, val lastDownloadTime: String) {
    constructor(isDownloading: Boolean, progress: Int, messsage: String): this(isDownloading, progress, messsage, "")
    constructor(isDownloading: Boolean, lastDownloadTime: String): this(isDownloading, 0, "", lastDownloadTime)
}


