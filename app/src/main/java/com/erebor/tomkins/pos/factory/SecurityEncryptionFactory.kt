package com.erebor.tomkins.pos.factory

import android.util.Base64

import com.erebor.tomkins.pos.BuildConfig
import com.erebor.tomkins.pos.tools.SecurityEncryption

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Singleton

@Singleton
class SecurityEncryptionFactory private constructor() {
    val encryption: SecurityEncryption

    init {
        encryption = object : SecurityEncryption {

            internal val pswdIterations = 10
            internal val keySize = 128
            internal val cypherInstance = "AES/CBC/PKCS5Padding"
            internal val secretKeyInstance = "PBKDF2WithHmacSHA1"
            internal val plainText = BuildConfig.SALTKEY
            internal val AESSalt = BuildConfig.SALTKEY
            internal val initializationVector = "8119745113154120"

            override fun md5(string: String): String {
                val hash: ByteArray

                try {
                    hash = MessageDigest.getInstance("MD5").digest(string.toByteArray(charset("UTF-8")))
                } catch (e: NoSuchAlgorithmException) {
                    throw RuntimeException("Huh, MD5 should be supported?", e)
                } catch (e: UnsupportedEncodingException) {
                    throw RuntimeException("Huh, UTF-8 should be supported?", e)
                }

                val hex = StringBuilder(hash.size * 2)

                for (b in hash) {
                    val i = (b.toInt() and 0x0f)
                    if (i < 0x10) hex.append('0')
                    hex.append(Integer.toHexString(i))
                }

                return hex.toString()
            }

            @Throws(Exception::class)
            override fun aesEncrypt(textToEncrypt: String): String {
                val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
                val cipher = Cipher.getInstance(cypherInstance)
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(initializationVector.toByteArray()))
                val encrypted = cipher.doFinal(textToEncrypt.toByteArray())
                return Base64.encodeToString(encrypted, Base64.DEFAULT)
            }

            @Throws(Exception::class)
            override fun aesDecrypt(textToDecrypt: String): String {
                val encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT)
                val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
                val cipher = Cipher.getInstance(cypherInstance)
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(initializationVector.toByteArray()))
                val decrypted = cipher.doFinal(encryted_bytes)
                return String(decrypted, Charsets.UTF_8)
            }

            @Throws(Exception::class)
            override fun sh1(text: String): String {
                val md = MessageDigest.getInstance("SHA-1")
                md.update(text.toByteArray(charset("iso-8859-1")), 0, text.length)
                val sha1hash = md.digest()
                return convertToHex(sha1hash)

            }

            private fun convertToHex(data: ByteArray): String {
                return data.toString(Charsets.UTF_8)
            }




            private fun getRaw(plainText: String, salt: String): ByteArray {
                try {
                    val factory = SecretKeyFactory.getInstance(secretKeyInstance)
                    val spec = PBEKeySpec(plainText.toCharArray(), salt.toByteArray(), pswdIterations, keySize)
                    return factory.generateSecret(spec).encoded
                } catch (e: InvalidKeySpecException) {
                    e.printStackTrace()
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                }

                return ByteArray(0)
            }
        }
    }

    companion object {

        private val encryptionFactory: SecurityEncryptionFactory? = null

        val instance: SecurityEncryptionFactory
            get() = encryptionFactory ?: SecurityEncryptionFactory()
    }
}
