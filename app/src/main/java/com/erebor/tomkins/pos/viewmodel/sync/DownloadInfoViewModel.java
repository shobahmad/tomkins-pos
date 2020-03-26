package com.erebor.tomkins.pos.viewmodel.sync;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseViewModel;
import com.erebor.tomkins.pos.data.local.dao.DownloadLastUpdateDao;
import com.erebor.tomkins.pos.data.local.model.DownloadLastUpdateModel;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.tools.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class DownloadInfoViewModel extends BaseViewModel<DownloadInfoViewState> {

    private Logger logger;
    private DownloadLastUpdateDao downloadLastUpdateDao;
    private ResourceHelper resourceHelper;
    private DateConverterHelper dateConverterHelper;

    @Inject
    public DownloadInfoViewModel(Logger logger, DownloadLastUpdateDao downloadLastUpdateDao, ResourceHelper resourceHelper, DateConverterHelper dateConverterHelper) {
        this.logger = logger;
        this.downloadLastUpdateDao = downloadLastUpdateDao;
        this.resourceHelper = resourceHelper;
        this.dateConverterHelper = dateConverterHelper;
    }

    public void getInfo() {
        setValue(DownloadInfoViewState.LOADING_STATE);

        getDisposable().add(downloadLastUpdateDao.getLatestInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        downloadLastUpdateModel -> {
                            DownloadInfoViewState.SUCCESS_STATE.setData(getDownloadInfo(downloadLastUpdateModel));
                            postValue(DownloadInfoViewState.SUCCESS_STATE);
                        },
                        throwable -> {
                            DownloadInfoViewState.ERROR_STATE.setError(throwable);
                            postValue(DownloadInfoViewState.ERROR_STATE);
                            logger.error(getClass().getSimpleName(), throwable.getMessage(), throwable);
                        }
                ));
    }

    private List<DownloadUiModel> getDownloadInfo(DownloadLastUpdateModel downloadLastUpdateModel) {
        List<DownloadUiModel> result = new ArrayList<>();

        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_art), getLastUpdate(downloadLastUpdateModel.article)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_barcode), getLastUpdate(downloadLastUpdateModel.barcode)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_brand), getLastUpdate(downloadLastUpdateModel.brand)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_gender), getLastUpdate(downloadLastUpdateModel.gender)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_ukuran), getLastUpdate(downloadLastUpdateModel.size)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_stock), getLastUpdate(downloadLastUpdateModel.stock)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_event), getLastUpdate(downloadLastUpdateModel.event)));
        result.add(new DownloadUiModel(resourceHelper.getResourceString(R.string.download_worker_event_detail), getLastUpdate(downloadLastUpdateModel.eventdetail)));

        return result;
    }

    private String getLastUpdate(Date date) {
        return date == null ? resourceHelper.getResourceString(R.string.download_not_yet) : dateConverterHelper.getDifference(date.getTime());
    }
}
