package com.example.onec.Vistas.Main.MainEmpresario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anunciosEmpresario(selected: MutableState<Boolean>, navController: NavController) {
    if(selected.value) {
        OnecTheme() {
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF2E3F32))) {
                Text(
                    text = "Anuncios",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                )
            }
        }
    }
}