package com.erebor.tomkins.pos.data.model

import android.util.Log

import com.erebor.tomkins.pos.factory.SecurityEncryptionFactory
import com.erebor.tomkins.pos.tools.SecurityEncryption

class EncryptedSha1Field {

    private var encryption: SecurityEncryption? = null
    var value: String? = null
        get() {
            try {
                return field
            } catch (e: Exception) {
                return null
            }

        }

    constructor() {
        this.encryption = SecurityEncryptionFactory.instance.encryption
    }

    constructor(value: String) {
        this.encryption = SecurityEncryptionFactory.instance.encryption
        this.value = encrypt(value)
    }

    private fun encrypt(value: String): String? {
        try {
            val encrypted = encryption!!.sh1(value)
            Log.d("EncryptedSha1Field", "Encrypting '$value' --> '$encrypted'")
            return encrypted
        } catch (e: Exception) {
            return null
        }

    }

}
