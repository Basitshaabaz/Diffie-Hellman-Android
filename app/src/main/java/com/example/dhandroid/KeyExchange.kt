package com.example.dhandroid

import java.security.*
import javax.crypto.KeyAgreement
import javax.crypto.interfaces.DHPublicKey
import javax.crypto.spec.DHParameterSpec

class KeyExchange(
    privateKeyReceiver: PrivateKey,
    publicKeySender: PublicKey
) {
    private var publicKey: PublicKey
    private var privateKey: PrivateKey

    private val keyFactory: KeyFactory
    private var keyPair: KeyPair? = null
    private var keyAgreement: KeyAgreement? = null
    private var keyPairGenerator: java.security.KeyPairGenerator? = null

    init {
        keyFactory = KeyFactory.getInstance("DH")
        publicKey = publicKeySender
        privateKey = privateKeyReceiver
        generateDHKeyPair(publicKey)
    }

    fun retrieveDHParamFromPB(key: PublicKey): DHParameterSpec? {
        return (key as DHPublicKey).getParams()
    }


    @Throws(NoSuchAlgorithmException::class, InvalidAlgorithmParameterException::class)
    fun generateDHKeyPair(senderPublicKey: PublicKey?) {
        val DHParam = retrieveDHParamFromPB(senderPublicKey!!)

        keyPairGenerator = java.security.KeyPairGenerator.getInstance("DH")!!
        keyPairGenerator!!.initialize(DHParam)
        keyPair = keyPairGenerator!!.generateKeyPair()

        try {
            initDHKeyAgreement()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }
    }


    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun initDHKeyAgreement() {
        keyAgreement = KeyAgreement.getInstance("DH")
        keyAgreement?.init(privateKey)
    }

    @Throws(InvalidKeyException::class)
    fun doPhase(publicKey: PublicKey?) {
        keyAgreement!!.doPhase(publicKey, true)
    }


    @Throws
    fun getSecret(): ByteArray {
        doPhase(publicKey)
        return keyAgreement!!.generateSecret()
    }

}