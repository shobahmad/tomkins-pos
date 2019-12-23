package com.erebor.tomkins.pos.repository.network;

import com.erebor.tomkins.pos.data.remote.LoginResponse;

import java.io.IOException;

import retrofit2.Response;

public class LoginRepository {

    TomkinsService service;

    public LoginRepository(TomkinsService service) {
        this.service = service;
    }

    public LoginResponse getSyncLogin(String username, String password) {
        try {
            Response<LoginResponse> response = service.getSyncLogin(username, password).execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
