package com.erebor.tomkins.pos.repository.network;

import com.erebor.tomkins.pos.data.remote.LoginRequest;
import com.erebor.tomkins.pos.data.remote.LoginResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface TomkinsService {

    @POST("login")
    Flowable<LoginResponse> postLogin(@Body LoginRequest login);

    @GET("login")
    Flowable<LoginResponse> gettLogin(@Query("username") String username, @Query("password") String password);

    @GET("login")
    Call<LoginResponse> getSyncLogin(@Query("username") String username, @Query("password") String password);

}
