package com.erebor.tomkins.pos.data.remote.response;

import java.io.IOException;

public class InvalidResponseException extends IOException {
    public InvalidResponseException() {
        super();
    }

    public InvalidResponseException(String message) {
        super(message);
    }

}
