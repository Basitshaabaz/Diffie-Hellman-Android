package com.example.dhandroid

import java.nio.charset.StandardCharsets
import java.security.KeyPair
import java.util.*

object KeyPairGenerator {

    private val ALLOWED_CHARACTERS: CharSequence = "qwertyuiopasdfghjklzxcvbnm1234567890"


    //Generate KeyPair with unique Identifier for further processing
    fun generateKey(): KeyPairModel {
        val keyPairGenerator = java.security.KeyPairGenerator.getInstance("DH")!!
        val keyPair = keyPairGenerator!!.generateKeyPair()
        val identifier = getRandomString(128)
        return KeyPairModel(keyPair, identifier)
    }

    fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString) sb.append(
            ALLOWED_CHARACTERS[random.nextInt(
                ALLOWED_CHARACTERS.length
            )]
        )
        return sb.toString()
    }


    data class KeyPairModel(
        var keyPair: KeyPair,
        var identifier: String
    )
}