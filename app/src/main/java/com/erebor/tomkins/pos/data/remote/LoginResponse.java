package com.erebor.tomkins.pos.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("error_code")
    @Expose
    private String error_code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Result result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        public Result() {
            id = 0;
            username = "";
            name = "";
            position = "";
        }

        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("position")
        @Expose
        private String position;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
