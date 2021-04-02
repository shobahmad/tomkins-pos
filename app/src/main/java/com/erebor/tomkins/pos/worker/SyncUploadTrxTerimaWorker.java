package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.base.BaseWorker;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;

public class SyncUploadTrxTerimaWorker extends BaseWorker {
    public static final String KEY_EXCEPTION_MESSAGE = "key_exception";
    public static final String KEY_REQUEST_ID = "key_request_id";

    @Inject
    TrxTerimaLocalRepository trxTerimaLocalRepository;
    @Inject
    TomkinsService service;
    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    Logger logger;

    public SyncUploadTrxTerimaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
               int unuploadedCount = trxTerimaLocalRepository.getSyncUnuploadedCount();
                logger.debug(getClass().getSimpleName(), "TrxTerima queue : " + unuploadedCount);
               if (unuploadedCount == 0) {
                   break;
               }

                /*
                    @get first queued transaction data
                 */
                TrxTerimaDBModel firstQueue = trxTerimaLocalRepository.getSyncFirstQueue();
                if (firstQueue == null) {
                    break;
                }
                /*
                    @post data
                    @read response
                 */
                postTransaction(firstQueue);

                /*
                @update transaction set sync
                 */
                trxTerimaLocalRepository.uploaded(firstQueue);
                logger.debug(getClass().getSimpleName(), "TrxTerima uploaded : " + firstQueue.getUploaded());
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

    private Date postTransaction(TrxTerimaDBModel stokRealDBModel) throws Exception {
        return new NetworkBoundResult<Date>() {
            @Override
            protected Call<RestResponse<Date>> callApiAction() {
                return service.postTrxTerima(sharedPrefs.getKodeKonter(), stokRealDBModel);
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
