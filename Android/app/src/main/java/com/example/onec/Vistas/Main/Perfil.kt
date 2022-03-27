package com.example.onec.Vistas.Main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onec.ui.theme.OnecTheme


@Composable
fun perfil() {
    OnecTheme() {
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .background(Color(0xFF191A25))) {
           Column(modifier = Modifier
               .align(alignment = Alignment.Center)
               .padding(vertical = 4.dp, horizontal = 4.dp)) {
              Box(modifier = Modifier
                  .fillMaxSize()) {
                  Column(modifier = Modifier.fillMaxSize()) {
                      //Boton acceder a Perfil Usuario -> Mis datos
                     Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().shadow(elevation = 0.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {

                     }
                      Spacer(modifier = Modifier.height(1.dp))
                      //Botón acceder a contratos aceptados
                      Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(),colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {

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
