package com.erebor.tomkins.pos.repository.network;

import com.erebor.tomkins.pos.data.local.model.MsArtDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBarcodeDBModel;
import com.erebor.tomkins.pos.data.local.model.MsBrandDBModel;
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
import retrofit2.http.Query;

public interface TomkinsService {

    @POST("login")
    Flowable<LoginResponse> postLogin(@Body LoginRequest login);

    @GET("login")
    Flowable<LoginResponse> gettLogin(@Query("username") String username, @Query("password") String password);

    @GET("login")
    Call<LoginResponse> getSyncLogin(@Query("username") String username, @Query("password") String password);

    @GET("sync_msart")
    Call<RestResponse<DownloadResponse<List<MsArtDBModel>>>> getMsArt(@Query("last_update") String lastUpdate);

    @GET("sync_msbarcode")
    Call<RestResponse<DownloadResponse<List<MsBarcodeDBModel>>>> getMsBarcode(@Query("last_update") String lastUpdate);

    @GET("sync_msbrand")
    Call<RestResponse<DownloadResponse<List<MsBrandDBModel>>>> getMsBrand(@Query("last_update") String lastUpdate);

}
