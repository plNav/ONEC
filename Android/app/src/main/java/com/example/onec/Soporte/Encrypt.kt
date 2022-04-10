package com.example.onec.Soporte

import android.util.Log
import java.security.MessageDigest

fun getSHA256(passw : String) : String {
    try{
        val bytes = passw.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }

    }catch (e: Exception){
        Log.e("Error","Error al convertir a SHA256")
        throw e
    }
}
