package com.example.onec.Soporte

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FireAuth {
    companion object{
        lateinit var auth: FirebaseAuth
        var user : FirebaseUser? = null
    }
}