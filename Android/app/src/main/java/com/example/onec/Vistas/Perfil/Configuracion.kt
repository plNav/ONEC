package com.example.onec.Vistas.Perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.onec.ui.theme.OnecTheme

@Composable
fun configuracion(navController: NavController) {
    OnecTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff333542))
        ) {
            Surface(
                shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f).align(Alignment.BottomCenter), color = Color(0xff434557)
            ) {

            }
        }
    }
}
