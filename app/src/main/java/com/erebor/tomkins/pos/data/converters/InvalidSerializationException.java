package com.erebor.tomkins.pos.data.converters;

public class InvalidSerializationException extends IllegalArgumentException {

    public InvalidSerializationException(String s) {
        super("Could not recognize " + s);
    }
}
