package com.example.onec.Vistas.Main.Ofertas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.CandidatosOfertasModel
import com.example.onec.Models.CvModel
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CandidatosOfertasViewModel
import com.example.onec.ViewModels.CvViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

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
                    mostrarCandidatos(show = showList, candidatos, mostrarSinCandidatos, navController = navController)
                }
            }
        }
    }
}

@Composable
fun errorCargaCandidatos(show : MutableState<Boolean>, loading : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                    Text(
                        text = "Error al cargar candidatos",
                        fontSize = 19.sp,
                        color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                    Image(
                        painter = painterResource(id = R.drawable.errorlog),
                        contentDescription = "Error log",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                    Text(
                        text = "Error al cargar los candidatos\n por favor inténtelo más tarde.",
                        fontSize = 16.sp,
                        color = Color(0xfffcffff),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
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
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
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
fun mostrarCandidatos(show: MutableState<Boolean>, candidatos: MutableState<SnapshotStateList<CandidatosOfertasModel>>, showNoEnc : MutableState<Boolean>, navController: NavController) {
    if (show.value) {

        val cvViewModel = remember {
            CvViewModel()
        }

        val can = remember {
            mutableStateOf(candidatos.value.size)
        }

        val candidatosOfertasViewModel = remember {
            CandidatosOfertasViewModel()
        }

        val showLoadingDialog = remember {
            mutableStateOf(false)
        }

        val showErr = remember {
            mutableStateOf(false)
        }

        val errMsj = remember {
            mutableStateOf("Error al eliminar candidato.")
        }

        val listaCVsEliminados = remember {
            mutableStateOf(mutableListOf<CvModel>().toMutableStateList())
        }

        OnecTheme() {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(5.dp)) {
                    items(candidatos.value) { candidato ->
                    val loading = remember {
                        mutableStateOf(true)
                    }

                    val cv = remember {
                        mutableStateOf<CvModel?>(null)
                    }

                    if (loading.value) {
                        LaunchedEffect(key1 = "nothing") {
                            cvViewModel.obtenerCvEspecifico(candidato.id_cv) { cvObtenido ->
                                if (cvObtenido != null) {
                                    cv.value = cvObtenido
                                    loading.value = false
                                }
                            }
                        }
                    }else {
                        AnimatedVisibility(
                            visible = !listaCVsEliminados.value.contains(cv.value),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 700))
                        ) {
                            val deleted = remember {
                                mutableStateOf(false)
                            }
                            Column(Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if(!deleted.value) {
                                        StaticVariables.candidatoGuardadoSeleccionadoCV = cv.value
                                        StaticVariables.candidatoGuardadoSeleccionado = candidato
                                        navController.navigate(Rutas.DetallesCandidatoGuardado.route)
                                    }
                                }, backgroundColor = Color(0xFFEDEEFF)
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                ) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(Modifier.fillMaxWidth(0.8f)) {
                                            Text(
                                                text = cv.value!!.nombre,
                                                fontSize = 18.sp,
                                                color = Color(0xFF1B1B27),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Divider(thickness = 1.dp, color = Color(0xFFBCBDD8))
                                            Spacer(modifier = Modifier.height(10.dp))
                                            if (cv.value!!.titulo != null) {
                                                Text(
                                                    text = "Titulación",
                                                    fontSize = 12.sp,
                                                    color = Color(0xFF1D1C1C),
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(3.dp))
                                                Text(
                                                    text = if (cv.value!!.especialidad != null) cv.value!!.especialidad!! else cv.value!!.titulo,
                                                    fontSize = 16.sp,
                                                    color = Color(
                                                        0xFF215A77
                                                    ),
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis

                                                )
                                            }
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "Experiencia",
                                                fontSize = 12.sp,
                                                color = Color(0xFF1D1C1C),
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = if (cv.value!!.experiencia > 0) cv.value!!.experiencia.toString() + " años" else "Sin experiencia",
                                                fontSize = 16.sp,
                                                color = Color(
                                                    0xFF215A77
                                                )
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                if (!deleted.value) {
                                                    deleted.value = true
                                                    showLoadingDialog.value = true
                                                    candidatosOfertasViewModel.eliminarCandidatosOfertasId(
                                                        candidato._id
                                                    ) { did ->
                                                        if (did) {
                                                            listaCVsEliminados.value.add(cv.value!!)
                                                            if (can.value - 1 <= 0) {
                                                                show.value = false
                                                                showNoEnc.value = true
                                                            } else {
                                                                can.value -= 1
                                                            }
                                                        } else {
                                                            deleted.value = false
                                                            showErr.value = true
                                                        }
                                                        showLoadingDialog.value = false
                                                    }
                                                }
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Borrar",
                                                tint = Color(0xFF2D81A8)
                                            )
                                        }
                                    }
                                }
                            }
                                Spacer(modifier = Modifier.height(5.dp))
                            }

                        }
                    }
                }
            }
            dialogError(show = showErr, msj = errMsj)
            dialogLoading(show = showLoadingDialog)
        }
    }
}