package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.base.BaseWorker;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;

public class SyncUploadStockWorker extends BaseWorker {
    public static final String KEY_EXCEPTION_MESSAGE = "key_exception";
    public static final String KEY_REQUEST_ID = "key_request_id";

    @Inject
    StokRealDao stokRealDao;
    @Inject
    TomkinsService service;
    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    Logger logger;

    public SyncUploadStockWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
                if (!isValidSession()) {
                    break;
                }
               /*
                  @if unsync transaction is empty then
                      break
                */
               int unuploadedCount = stokRealDao.getSyncUnuploadedCount();
                logger.debug(getClass().getSimpleName(), "Stock queue : " + unuploadedCount);
               if (unuploadedCount == 0) {
                   break;
               }

                /*
                    @get first queued transaction data
                 */
                StokRealDBModel stokRealDBModel = stokRealDao.getSyncFirstQueue();
                if (stokRealDBModel == null) {
                    break;
                }
                /*
                    @post data
                    @read response
                 */
                Date uploadedDate = postTransaction(stokRealDBModel);

                /*
                @update transaction set sync
                 */
                stokRealDBModel.setUploaded(true);
                stokRealDao.update(stokRealDBModel).blockingGet();
            } catch (Exception | Error e) {
                Data data = new Data.Builder()
                        .putString(KEY_EXCEPTION_MESSAGE, e.getMessage())
                        .build();
                return Result.failure(data);
            }
        }

        return Result.success(getSuccessOutputData());
    }


    private boolean isValidSession() {
        return !sharedPrefs.getUsername().isEmpty();
    }

    private Date postTransaction(StokRealDBModel stokRealDBModel) throws Exception {
        return new NetworkBoundResult<Date>() {
            @Override
            protected Call<RestResponse<Date>> callApiAction() {
                return service.postStock(stokRealDBModel);
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
