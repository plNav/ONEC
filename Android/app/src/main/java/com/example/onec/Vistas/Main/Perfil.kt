package com.example.onec.Vistas.Main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ui.theme.OnecTheme


@Composable
fun perfil(navController: NavController) {
    val loginViewModel = remember {
        LoginRegistroViewModel()
    }
    val items = listOf(
       "Configuración",
       "Cerrar sesión"
    )
    OnecTheme() {
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .background(Color(0xFF191A25))) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        items.forEach { item ->
                            drawerItems(name = item) {
                                if (it == "Configuración") {
                                    navController.navigate(Rutas.Configuracion.route) {
                                        popUpTo(Rutas.Main.route)
                                    }
                                } else {
                                    navController.navigate(Rutas.Login.route) { popUpTo(0)}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun cusShape() = object : Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(left = size.width *0.25f, top = 0f, right = size.width, bottom = size.height))
        //Log.e("size",size.wid)
    }
}

@Composable
fun drawerItems(
name: String,
onClick : (name:String) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(name) }
            .height(40.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(
                    id = if (name == "Configuración") {
                        R.drawable.ic_baseline_manage_accounts_24
                    } else {
                        R.drawable.ic_baseline_logout_24
                    }
                ),
                contentDescription = "Configuración",
                tint = Color(0xfffcffff),
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
            Spacer(modifier = Modifier.width(13.dp))
            Text(text = name, fontSize = 16.sp, color = Color(0xfffcffff))
        }
    }
}
