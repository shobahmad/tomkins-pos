package com.erebor.tomkins.pos.tools;

public interface SecurityEncryption {
    String md5(String text);
    String aesEncrypt(String textToEncrypt) throws Exception;
    String aesDecrypt(String textToDencrypt) throws Exception;
    String sh1(String text) throws Exception;
}
