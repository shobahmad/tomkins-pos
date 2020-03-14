package com.erebor.tomkins.pos.data.remote.response;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public abstract class NetworkBoundResult<ResultType> {
    @NonNull
    public ResultType fetchData() throws InvalidResponseException {
        Response<RestResponse<ResultType>> response;
        try {
            response = callApiAction().execute();
        } catch (IOException e) {
            throw new InvalidResponseException(e.getMessage());
        }
        RestResponse<ResultType> body = response.body();
        if (!response.isSuccessful())
            throw getUnsuccessfulResponse(response.errorBody());

        if (body != null)
            return body.getResult();

        try {
            throw new EmptyResponseException();
        } catch (EmptyResponseException e) {
            throw new InvalidResponseException("Invalid Response");
        }
    }

    protected abstract Call<RestResponse<ResultType>> callApiAction();

    @NonNull
    private InvalidResponseException getUnsuccessfulResponse(ResponseBody errorBody) {
        try {
            return new InvalidResponseException(new JSONObject(errorBody.string())
                    .getJSONObject("error")
                    .getString("message"));
        } catch (JSONException | IOException e) {
            return new InvalidResponseException("Invalid Response: " + e.getMessage());
        }
    }
}
