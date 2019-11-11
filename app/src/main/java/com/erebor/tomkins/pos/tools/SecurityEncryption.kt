package com.erebor.tomkins.pos.tools

interface SecurityEncryption {
    fun md5(text: String): String
    @Throws(Exception::class)
    fun aesEncrypt(textToEncrypt: String): String

    @Throws(Exception::class)
    fun aesDecrypt(textToDencrypt: String): String

    @Throws(Exception::class)
    fun sh1(text: String): String
}
