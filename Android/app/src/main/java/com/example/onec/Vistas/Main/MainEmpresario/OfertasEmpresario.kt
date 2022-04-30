package com.example.onec.Vistas.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.onec.Models.ModelOferta
import com.example.onec.R
import com.example.onec.ViewModels.OfertaViewModel
import com.example.onec.ui.theme.OnecTheme

@Composable
fun ofertaEmpresario(selected: MutableState<Boolean>, navController: NavController) {

    val viewModelOferta = remember {
        OfertaViewModel()
    }
    val hasError = remember {
        mutableStateOf(false)
    }

    val isLoading = remember {
        mutableStateOf(false)
    }

    val hasResult = remember {
        mutableStateOf(false)
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
                                //val oferta = ModelOferta(id_usuario = FireAuth.user!!.uid,"Prueba","Descripcion")
                                isLoading.value = true
                                hasResult.value = false
                                viewModelOferta.crearOferta(ModelOferta("asxggsdfd","Titulo","asdgkj")) {
                                    if (it != null) {
                                        viewModelOferta.ofertaCreada = it
                                        isLoading.value = false
                                        hasResult.value = true
                                    }else {
                                        isLoading.value = false
                                        hasResult.value = false
                                        hasError.value = true
                                    }
                                }
                            }) {
                                Icon(Icons.Filled.Add, contentDescription = "Agregar oferta", tint = Color(0xfffcffff))
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
                        if (isLoading.value) {
                            //Muestra el Loading
                            circuloLoading()
                        }else if (hasResult.value){
                            //Muestra el contenido
                            contenidoResult(viewModelOferta)
                        }
                    }
                }
            }
        }
    }
}