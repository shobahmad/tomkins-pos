package com.erebor.tomkins.pos.repository.network;

import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBrandDBModel;
import com.erebor.tomkins.pos.data.local.model.MsGenderDBModel;
import com.erebor.tomkins.pos.data.local.model.MsKonterDBModel;
import com.erebor.tomkins.pos.data.local.model.MsUkuranDBModel;
import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.remote.LoginResponse;
import com.erebor.tomkins.pos.data.remote.DownloadResponse;
import com.erebor.tomkins.pos.data.remote.response.RestResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TomkinsService {

    @POST("login")
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

    @GET("sync/stokreal")
    Call<RestResponse<DownloadResponse<List<StokRealDBModel>>>> getStokReal(@Query("last_update") String lastUpdate);

}
