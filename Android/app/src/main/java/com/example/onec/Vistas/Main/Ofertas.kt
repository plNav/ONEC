package com.example.onec.Vistas.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import com.example.onec.R

import com.example.onec.ViewModels.OfertaViewModel
import com.example.onec.ui.theme.OnecTheme

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
                    .background(color = Color(0xff3b3d4c))
            ) {
                Scaffold(modifier = Modifier.fillMaxSize(),topBar = {
                    TopAppBar(
                        backgroundColor = Color(0xFF1B1C29),
                        title = {
                            Text(
                                text = "Mis ofertas",
                                fontFamily = FontFamily(Font(R.font.comforta)),
                                color = Color(0xfffcffff),
                                fontWeight = FontWeight.W500
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                /*Abre las ofertas guardadas como que le gustan*/
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_thumb_up_24),
                                    contentDescription = "Agregar oferta",
                                    tint = Color(0xfffcffff)
                                )
                            }
                        }, modifier = Modifier
                            .shadow(elevation = 0.dp)
                            .fillMaxHeight(0.08f)
                    )
                }) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xff3b3d4c))
                            .fillMaxSize()
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                    ) {

                    }
                }
            }
        }

    }
}





