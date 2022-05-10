package com.example.onec.Vistas.Main.Ofertas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.CvModel
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CvViewModel
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ui.theme.OnecTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun buscarCandidatos(navController: NavController) {
    OnecTheme() {
        val loading = remember {
            mutableStateOf(true)
        }

        val cvViewModel = remember {
            CvViewModel()
        }
        
        val listCVS = remember {
            mutableStateOf(mutableListOf<CvModel>().toMutableStateList())
        }

        val showNoEncontrado = remember {
            mutableStateOf(false)
        }

        val showError = remember {
            mutableStateOf(false)
        }

        val showCVS = remember {
        mutableStateOf(false)
    }

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
                        text = "Búsqueda candidatos",
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
                    IconButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        content = {})
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
                    val reqH = remember { if (StaticVariables.ofertaSeleccionada!!.habilidadesReq) "S" else "N"}
                    cvViewModel.buscarCVS(StaticVariables.ofertaSeleccionada!!._id, reqH) { cvs ->
                        if (cvs == null) {
                            showError.value = true
                        }else if (cvs.isEmpty()) {
                            showNoEncontrado.value = true
                        }else {
                            listCVS.value = cvs.toMutableStateList()
                            showCVS.value = true
                        }
                        loading.value = false
                    }
                }
                mostrarCVS(show = showCVS, cvs = listCVS)
                cvsNoEncontrados(show = showNoEncontrado)
                mostrarError(show = showError, loading = loading)
            }
        }
    }
}

@Composable
fun mostrarCVS(show : MutableState<Boolean>, cvs : MutableState<SnapshotStateList<CvModel>>) {
    if (show.value) {
        
        val showError = remember {
            mutableStateOf(false)
        }
        
        val scrollState = rememberScrollState()

        val position = remember {
            mutableStateOf(0)
        }
        
        OnecTheme() {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (!cvs.value.isEmpty()) {
                    Text(text = "Número de candidatos: " + cvs.value.size, fontSize = 19.sp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.5f)
                        , backgroundColor = Color(0xFF25749B),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Column(Modifier.fillMaxSize()) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp),
                                color = Color(0xfffcffff)
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Column(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(10.dp)
                                            .verticalScroll(scrollState)
                                    ) {
                                        Text(
                                            text = cvs.value[position.value].nombre,
                                            fontSize = 18.sp,
                                            color = Color(0xFF1B1B27),
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        if (cvs.value[position.value].titulo != null) {
                                            Spacer(modifier = Modifier.height(20.dp))
                                            Text(
                                                text = "Titulación",
                                                fontSize = 12.sp,
                                                color = Color(0xFF1D1C1C),
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Row(Modifier.fillMaxWidth()) {
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    text = if (cvs.value[position.value].especialidad != null) cvs.value[position.value].especialidad!! else cvs.value[position.value].titulo,
                                                    fontSize = 16.sp,
                                                    color = Color(
                                                        0xFF215A77
                                                    )
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text(
                                            text = "Experiencia",
                                            fontSize = 12.sp,
                                            color = Color(0xFF1D1C1C),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Row(Modifier.fillMaxWidth()) {
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = if(cvs.value[position.value].experiencia > 0) cvs.value[position.value].experiencia.toString() + " años" else "Sin experiencia",
                                                fontSize = 16.sp,
                                                color = Color(
                                                    0xFF215A77
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text(
                                            text = "Habilidades",
                                            fontSize = 12.sp,
                                            color = Color(0xFF1D1C1C),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        if (cvs.value[position.value].habilidades.isEmpty()) {
                                            Text(
                                                text = "Ninguna habilidad especificada",
                                                fontSize = 16.sp,
                                                color = Color(
                                                    0xFF215A77
                                                )
                                            )
                                        }else {
                                           FlowRow(modifier = Modifier
                                               .background(Color.Transparent),
                                               mainAxisAlignment = MainAxisAlignment.Start,
                                               mainAxisSize = SizeMode.Expand,
                                               crossAxisSpacing = 12.dp,
                                               mainAxisSpacing = 8.dp) {
                                               cvs.value[position.value].habilidades.forEach { habilidad ->
                                                   Row(
                                                       modifier = Modifier
                                                           .background(
                                                               color = Color(0xFF646A8F),
                                                               shape = RoundedCornerShape(4.dp)
                                                           )
                                                           .padding(2.dp),
                                                       verticalAlignment = Alignment.CenterVertically,
                                                       horizontalArrangement = Arrangement.Center
                                                   ) {
                                                       Text(
                                                           text = "# $habilidad",
                                                           color = Color.White,
                                                           overflow = TextOverflow.Ellipsis,
                                                           maxLines = 1,
                                                           modifier = Modifier.padding(
                                                               10.dp,
                                                               5.dp,
                                                               10.dp,
                                                               5.dp
                                                           )
                                                       )
                                                   }
                                               }
                                           }
                                        }
                                    }
                                }
                            }
                            Box(
                                Modifier
                                    .fillMaxSize()
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_thumb_up_24),
                                            contentDescription = "Like",
                                            tint = Color(0xfffcffff)
                                        )
                                    }

                                    IconButton(onClick = {
                                        cvs.value.remove(cvs.value[position.value])
                                        if (position.value > 0) position.value -= 1
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_thumb_down_alt_24),
                                            contentDescription = "Like",
                                            tint = Color(0xfffcffff)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Candidato ${position.value + 1} de ${cvs.value.size}", fontSize = 16.sp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.SpaceEvenly) {
                        if (position.value > 0) {
                            IconButton(onClick = {position.value -= 1 }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.left),
                                    contentDescription = "Dislike",
                                    tint = Color(0xfffcffff)
                                )
                            }
                        }

                        if (position.value < cvs.value.size -1) {
                            IconButton(onClick = { position.value += 1}) {
                                Icon(
                                    painter = painterResource(id = R.drawable.rigth),
                                    contentDescription = "Dislike",
                                    tint = Color(0xfffcffff)
                                )
                            }
                        }
                    }
                }else {
                    Text(text = "No quedan más candidatos", fontSize = 19.sp, textAlign = TextAlign.Center, color = Color(0xfffcffff))
                }
            }
        }
    }
}

@Composable
fun cvsNoEncontrados(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "ningún candidato disponible", fontSize = 19.sp, color = Color(0xfffcffff))
            }
        }
    }
}

@Composable
fun mostrarError(show: MutableState<Boolean>, loading : MutableState<Boolean>) {
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
                        text = "Error al buscar candidatos",
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
                        text = "Error al buscar candidatos\n por favor inténtelo más tarde.",
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
