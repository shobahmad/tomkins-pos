package com.erebor.tomkins.pos.repository.network;

import com.erebor.tomkins.pos.data.local.model.EventHargaDBModel;
import com.erebor.tomkins.pos.data.local.model.EventHargaDetDBModel;
import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBrandDBModel;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;
import com.erebor.tomkins.pos.data.local.model.MsUkuranDBModel;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxJualDBModel;
import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.remote.LoginResponse;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TomkinsService {

    @POST("auth")
    Flowable<RestResponse<LoginResponse>> postLogin(@Body LoginRequest login);


    @POST("logout")
    Flowable<RestResponse<String>> postLogout();

    @GET("sync/msart")
    Call<RestResponse<DownloadResponse<List<MsArtDBModel>>>> getMsArt(@Query("last_update") String lastUpdate);

    @GET("sync/msbarcode")
    Call<RestResponse<DownloadResponse<List<MsBarcodeDBModel>>>> getMsBarcode(@Query("last_update") String lastUpdate);

    @GET("sync/msbrand")
    Call<RestResponse<DownloadResponse<List<MsBrandDBModel>>>> getMsBrand(@Query("last_update") String lastUpdate);

    @GET("sync/msgender")
    Call<RestResponse<DownloadResponse<List<MsGenderDBModel>>>> getMsGender(@Query("last_update") String lastUpdate);

    @GET("sync/msukuran")
    Call<RestResponse<DownloadResponse<List<MsUkuranDBModel>>>> getMsUkuran(@Query("last_update") String lastUpdate);

    @GET("sync/stokreal/{counter}")
    Call<RestResponse<DownloadResponse<List<StokRealDBModel>>>> getStokReal(@Path("counter") String counter, @Query("last_update") String lastUpdate);

    @GET("sync/eventharga/{counter}")
    Call<RestResponse<DownloadResponse<List<EventHargaDBModel>>>> getEventHarga(@Path("counter") String counter, @Query("last_update") String lastUpdate);

    @GET("sync/eventhargadet/{counter}")
    Call<RestResponse<DownloadResponse<List<EventHargaDetDBModel>>>> getEventHargaDet(@Path("counter") String counter,@Query("last_update") String lastUpdate);

    @POST("trx")
    Call<RestResponse<Date>> postTransaction(@Body TrxJualDBModel trxJualDBModel);

}
