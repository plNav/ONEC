package com.example.onec.Vistas.Main.Ofertas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.CandidatosOfertasModel
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CandidatosOfertasViewModel
import com.example.onec.ui.theme.OnecTheme

@Composable
fun ofertasCandidatos(navController: NavController) {

    val loading = remember {
        mutableStateOf(true)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    val showList = remember {
        mutableStateOf(false)
    }

    val mostrarSinCandidatos = remember {
        mutableStateOf(false)
    }

    val candidatosOfertasViewModel = remember {
        CandidatosOfertasViewModel()
    }

    val candidatos = remember {
        mutableStateOf(mutableListOf<CandidatosOfertasModel>().toMutableStateList())
    }

    OnecTheme() {
        Scaffold(modifier = Modifier.fillMaxSize(),topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF1B1C29),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Rutas.OfertaDetalles.route) {
                            popUpTo(
                                0
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = Color(0xfffcffff)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Candidatos",
                        fontSize = 19.sp,
                        color = Color(0xfffcffff),
                        fontFamily = FontFamily(
                            Font(R.font.comforta)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                        IconButton(onClick = {
                            navController.navigate(Rutas.BuscarCandidatos.route)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Volver atrás",
                                tint = Color(0xfffcffff)
                            )
                        }
                }
            )
        }) {
            Box(
                modifier = Modifier
                    .background(Color(0xff3b3d4c))
                    .fillMaxSize()
                    .padding(0.dp, 5.dp, 0.dp, 0.dp)
            ) {
                if (loading.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xff3b3d4c))
                    ){
                        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(modifier = Modifier
                                .height(50.dp)
                                .width(50.dp), color = Color(0xfffcffff))
                            Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                        }
                    }
                    candidatosOfertasViewModel.obtenerCandidatosOferta(StaticVariables.ofertaSeleccionada!!._id) { candidatosOfertas ->
                       if (candidatosOfertas == null) {
                           showError.value = true
                       }else if (candidatosOfertas.isEmpty()){
                           mostrarSinCandidatos.value = true
                       }else {
                           candidatos.value = candidatosOfertas.toMutableStateList()
                          showList.value = true
                       }
                        loading.value = false
                    }
                }else {
                    errorCargaCandidatos(show = showError, loading = loading)
                    noCandidatos(show = mostrarSinCandidatos)
                    mostrarCandidatos(show = showList, candidatos)
                }
            }
        }
    }
}

@Composable
fun errorCargaCandidatos(show : MutableState<Boolean>, loading : MutableState<Boolean>) {
    if (show.value) {
        val scrollState = rememberScrollState(0)
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Error al cargar candidatos",
                        fontSize = 19.sp,
                        color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.errorlog),
                        contentDescription = "Error log"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Error al cargar los candidatos\n por favor inténtelo más tarde.",
                        fontSize = 16.sp,
                        color = Color(0xfffcffff),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            loading.value = true
                            show.value = false
                        },
                        Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFF266E86
                            )
                        )
                    ) {
                        Text(
                            text = "Reintentar",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(
                                Font(R.font.comforta)
                            )
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun noCandidatos(show: MutableState<Boolean>) {
    if (show.value) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "ningún candidato guardado", fontSize = 19.sp, color = Color(0xfffcffff))
        }
    }
}

@Composable
fun mostrarCandidatos(show: MutableState<Boolean>, candidatos: MutableState<SnapshotStateList<CandidatosOfertasModel>>) {
    if (show.value) {
        OnecTheme() {
            
        }
    }
}