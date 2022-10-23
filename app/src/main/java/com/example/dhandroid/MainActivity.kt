package com.example.dhandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log

class MainActivity : AppCompatActivity() {
    val tag = MainActivity::class.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Generate Key Pair for Bob
        val kBob = KeyPairGenerator.generateKey()

        //Generating Keypair for ALice
        val kAlice = KeyPairGenerator.generateKey()

        //Initiating Key Exchange Pre Conditions
        val DHBob = KeyExchange(
            kBob.keyPair.private, kAlice.keyPair.public
        )

        //Getting Secret Key of with Bob's Private Key and Alice's Public Key
        Log.e(tag, "[Bob SK]:\t${Base64.encodeToString(DHBob.getSecret(), Base64.DEFAULT)}")

        //Initiating Key Exchange Pre Conditions
        val DHAlice = KeyExchange(
            kAlice.keyPair.private,
            kBob.keyPair.public
        )

        //Getting Secret Key of with Alice's Private Key and Bob's Public Key
        Log.e(tag, "[Alice SK]:\t${Base64.encodeToString(DHAlice.getSecret(), Base64.DEFAULT)}")

    }
}