package com.erebor.tomkins.pos.tools;

public interface Logger {

    void info(String tag, String message);
    void debug(String tag, String message);
    void warning(String tag, String message);
    void error(String tag, String message, Throwable throwable);
}
