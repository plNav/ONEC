package com.example.onec.Vistas.Main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.ModelOferta
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.FireAuth
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.OfertaViewModel
import com.example.onec.ui.theme.OnecTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ofertas(selected: MutableState<Boolean>,navController: NavController) {

    val viewModelOferta = remember {
        OfertaViewModel()
    }

    if (selected.value) {
        OnecTheme {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color(0xff3b3d4c))) {
              Scaffold( topBar = {
                  TopAppBar(
                      backgroundColor = Color(0xFF1B1C29),
                      title = {
                          Text(
                              text = "Mis ofertas", fontFamily = FontFamily(Font(R.font.comforta)), color = Color(0xfffcffff), fontWeight = FontWeight.W500
                          ) },
                      actions = {
                          IconButton(onClick = {
                            /*Abre las ofertas guardadas como que le gustan*/
                          }) {
                              Icon(painter = painterResource(id = R.drawable.ic_baseline_thumb_up_24), contentDescription = "Agregar oferta", tint = Color(0xfffcffff))
                          }
                      }, modifier = Modifier
                          .shadow(elevation = 0.dp)
                          .fillMaxHeight(0.08f)
                  )
              }) {
                 Box(modifier = Modifier
                     .background(Color(0xff3b3d4c))
                     .fillMaxSize()
                     .padding(0.dp, 5.dp, 0.dp, 0.dp)) {

                 } 
              }
            }
        }
    }
}

@Composable
fun circuloLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Cargando...", color = Color(0xfffcffff))
            CircularProgressIndicator(color = Color(0xFF266E86), strokeWidth = 2.dp, modifier = Modifier
                .height(30.dp)
                .width(30.dp))
        }
    }


}

@Composable
fun contenidoResult(viewModel: OfertaViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Oferta.id = "+viewModel.ofertaCreada!!.titulo, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}





