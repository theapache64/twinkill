package com.theapache64.twinkill.utils


import android.util.Base64

import java.security.Key

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


/**
 * To do basic text encryption and decryption with predefined salt.
 */
object DarkKnight {

    private val ALGORITHM = "AES"

    private val SALT = byteArrayOf(
        't'.toByte(),
        'H'.toByte(),
        'e'.toByte(),
        'A'.toByte(),
        'p'.toByte(),
        'A'.toByte(),
        'c'.toByte(),
        'H'.toByte(),
        'e'.toByte(),
        '6'.toByte(),
        '4'.toByte(),
        '1'.toByte(),
        '0'.toByte(),
        '0'.toByte(),
        '0'.toByte(),
        '0'.toByte()
    )

    val salt: Key
        get() = SecretKeySpec(SALT, ALGORITHM)


    fun getEncrypted(plainText: String): String {
        val salt = salt
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, salt)
        val encodedValue = cipher.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encodedValue, Base64.DEFAULT)
    }

    fun getDecrypted(encodedText: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, salt)
        val decodedValue = Base64.decode(encodedText, Base64.DEFAULT)
        val decValue = cipher.doFinal(decodedValue)
        return String(decValue)
    }

}