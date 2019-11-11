package com.erebor.tomkins.pos.data.model

import com.erebor.tomkins.pos.factory.SecurityEncryptionFactory
import com.erebor.tomkins.pos.tools.SecurityEncryption

class EncryptedAesField(value: String) {
    private val encryption: SecurityEncryption
    private val encrypted: String
    private var decrypted: String? = null
    val decryptedValue: String?
        get() {
            try {
                if (decrypted != null) {
                    return decrypted
                }
                decrypted = encryption.aesDecrypt(encrypted)
                return decrypted
            } catch (e: Exception) {
                return null
            }

        }
    val encryptedValue: String?
        get() {
            try {
                return encrypted
            } catch (e: Exception) {
                return null
            }

        }

    init {
        this.encryption = SecurityEncryptionFactory.instance.encryption
        this.encrypted = encrypt(value)
    }

    private fun encrypt(value: String): String {
        try {
            return encryption.aesEncrypt(value)
        } catch (e: Exception) {
            return value
        }

    }

    companion object {

        private val TAG = "EncryptedAesField"
    }

}
