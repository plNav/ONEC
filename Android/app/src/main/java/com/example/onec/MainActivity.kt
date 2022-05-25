package com.example.onec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.onec.Navegacion.navHost

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        setContent {
                    navHost(applicationContext)
            }
        }
    }
