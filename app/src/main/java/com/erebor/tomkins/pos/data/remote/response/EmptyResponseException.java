package com.erebor.tomkins.pos.data.remote.response;

public class EmptyResponseException extends Exception {
    private static final String message = "Response body is empty";

    public EmptyResponseException() {
        super(message);
    }
}
