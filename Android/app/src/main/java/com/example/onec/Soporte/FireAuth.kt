package com.example.onec.Soporte

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FireAuth {
    companion object{
        var auth: FirebaseAuth? = null
        var user : FirebaseUser? = null
    }
}