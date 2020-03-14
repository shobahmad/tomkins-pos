package com.erebor.tomkins.pos.data.remote.response;

public enum ResponseStatusConstant {
    ERROR(0),
    SUCCESS(1),
    LOADING(2);

    private final int value;

    ResponseStatusConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
