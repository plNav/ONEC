package com.example.onec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.onec.Navegacion.navHost
import com.example.onec.Soporte.FireAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FireAuth.auth = Firebase.auth


    }

    override fun onStart() {
        super.onStart()
        val currentUser = FireAuth.auth!!.currentUser
        FireAuth.user = FireAuth.auth!!.currentUser
        setContent {
                    navHost(applicationContext, currentUser != null)
            }
        }
    }
