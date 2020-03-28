package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.base.BaseWorker;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDao;
import com.erebor.tomkins.pos.data.local.dao.TrxJualDetDao;
import com.erebor.tomkins.pos.data.local.model.TrxJualDBModel;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.repository.network.TomkinsService;

import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;

public class SyncUploadWorker extends BaseWorker {
    public static final String KEY_EXCEPTION_MESSAGE = "key_exception";
    public static final String KEY_REQUEST_ID = "key_request_id";

    @Inject
    TrxJualDao trxJualDao;
    @Inject
    TrxJualDetDao trxJualDetDao;
    @Inject
    TomkinsService service;

    public SyncUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @NonNull
    @Override
    public Result doWork() {
        while (true) {
            try {
               /*
                  @if unsync transaction is empty then
                      break
                */
               if (trxJualDao.getSyncUnuploadedCount() == 0) {
                   break;
               }

                /*
                    @get first queued transaction data
                 */
                TrxJualDBModel trxJualDBModel = trxJualDao.getSyncFirstQueue();
                if (trxJualDBModel == null) {
                    break;
                }
                trxJualDBModel.setListDetail(trxJualDetDao.getListByNoBon(trxJualDBModel.getNoBon()));

                /*
                    @post data
                    @read response
                 */
                Date uploadedDate = postTransaction(trxJualDBModel);

                /*
                @update transaction set sync
                 */
                trxJualDBModel.setUploaded(true);
                trxJualDBModel.setTanggalUpload(uploadedDate);
                trxJualDao.update(trxJualDBModel).blockingGet();
            } catch (Exception | Error e) {
                Data data = new Data.Builder()
                        .putString(KEY_EXCEPTION_MESSAGE, e.getMessage())
                        .build();
                return Result.failure(data);
            }
        }

        return Result.success(getSuccessOutputData());
    }

    private Date postTransaction(TrxJualDBModel trxJualDBModel) throws Exception {
        return new NetworkBoundResult<Date>() {
            @Override
            protected Call<RestResponse<Date>> callApiAction() {
                return service.postTransaction(trxJualDBModel);
            }
        }.fetchData();
    }

    private Data getSuccessOutputData() {
        long requestId = getInputData().getLong(KEY_REQUEST_ID, 0);
        return new Data.Builder()
                .putLong(KEY_REQUEST_ID, requestId)
                .build();
    }


}
