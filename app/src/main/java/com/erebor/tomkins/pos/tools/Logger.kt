package com.erebor.tomkins.pos.tools

interface Logger {

    fun info(tag: String, message: String)
    fun debug(tag: String, message: String)
    fun warning(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable)
}
