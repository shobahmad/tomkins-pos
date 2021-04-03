package com.erebor.tomkins.pos.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;

import com.erebor.tomkins.pos.base.BaseWorker;
import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.remote.StockRequest;
import com.erebor.tomkins.pos.data.remote.response.NetworkBoundResult;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.tools.Logger;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Inject
    TomkinsDatabase tomkinsDatabase;

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
                List<StokRealDBModel> stokRealDBModel = stokRealDao.getListSyncQueue();
                if (stokRealDBModel.isEmpty()) {
                    break;
                }
                /*
                    @post data
                    @read response
                 */
                Date uploadedDate = postTransaction(stokRealDBModel.get(0).getLastUpdate(), stokRealDBModel);
                logger.debug(getClass().getSimpleName(), uploadedDate.toString());

                /*
                @update transaction set sync
                 */
                tomkinsDatabase.runInTransaction(() -> {
                    for (StokRealDBModel stokDBModel : stokRealDBModel) {
                        stokDBModel.setUploaded(true);
                            stokRealDao.update(stokDBModel).blockingGet();
                    }
                });
            } catch (Exception | Error e) {
                logger.error(getClass().getSimpleName(), e.getMessage(), e);
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

    private Date postTransaction(Date lastUpdate, List<StokRealDBModel> stokRealDBModel) throws Exception {
        return new NetworkBoundResult<Date>() {
            @Override
            protected Call<RestResponse<Date>> callApiAction() {
                return service.postStock(sharedPrefs.getKodeKonter(), new StockRequest(lastUpdate, stokRealDBModel));
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
