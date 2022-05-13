package com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.AnuncioPost
import com.example.onec.Models.AnunciosGuardadosPost
import com.example.onec.Models.VisualizacionesPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.ViewModels.AnunciosGuardadosViewModel
import com.example.onec.ViewModels.ResenyaViewModel
import com.example.onec.ViewModels.VisualizacionesViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anuncioBuscadoDetalles(navController: NavController){
    OnecTheme {

        val resenyaViewModel = remember {
            ResenyaViewModel()
        }

        val visualizacionesViewModel = remember {
            VisualizacionesViewModel()
        }

        val errorCarga = remember {
            mutableStateOf(false)
        }

        val loading = remember {
            mutableStateOf(false)
        }

        val cargandoAnuncio = remember {
            mutableStateOf(false)
        }

        val loadingAnuncioVista = remember {
            mutableStateOf(true)
        }

        val showDialogError = remember {
            mutableStateOf(false)
        }
        val errorMsj = remember {
            mutableStateOf("Error desconocido")
        }
        val scrollState = rememberScrollState(0)

        val anunciosGuardadosViewModel = remember {
            AnunciosGuardadosViewModel()
        }

        val anuncioViewModel = remember {
            AnuncioViewModel()
        }

        if (loadingAnuncioVista.value) {
            cargandoAnuncio.value = true
            //Primero obtenemos las visualizaciones del usuario para dicho anuncio y la puntuacion
            resenyaViewModel.calcularPuntuacionAnuncio(StaticVariables.anuncioBuscadoSelect!!._id) { puntuacion ->
                if (puntuacion != null) {
                    StaticVariables.puntuacionAnuncioBuscado = puntuacion
                    visualizacionesViewModel.obtenerVisualizacionesUsuarioAnuncio(
                        StaticVariables.usuario!!._id,
                        StaticVariables.anuncioBuscadoSelect!!._id
                    ) { visualizaciones ->
                        if (visualizaciones == null) {
                            errorCarga.value = true
                        } else if (visualizaciones.isEmpty()) {
                            //Hacemos un Post de la visualizacion
                            val visualizacion = VisualizacionesPost(
                                StaticVariables.anuncioBuscadoSelect!!._id,
                                StaticVariables.usuario!!._id
                            )
                            visualizacionesViewModel.crearVisualizacion(visualizacion = visualizacion) { visualizacion ->
                                if (visualizacion != null) {
                                    val anuncio = AnuncioPost(
                                        StaticVariables.anuncioBuscadoSelect!!.id_user,
                                        StaticVariables.anuncioBuscadoSelect!!.categoria,
                                        StaticVariables.anuncioBuscadoSelect!!.nombre,
                                        StaticVariables.anuncioBuscadoSelect!!.descripcion,
                                        StaticVariables.anuncioBuscadoSelect!!.precio,
                                        StaticVariables.anuncioBuscadoSelect!!.precioPorHora,
                                        StaticVariables.anuncioBuscadoSelect!!.numVecesVisto + 1
                                    )
                                    anuncioViewModel.actualizarAnuncio(
                                        StaticVariables.anuncioBuscadoSelect!!._id,
                                        anuncio = anuncio
                                    ) { did ->
                                        if (did) {
                                            StaticVariables.anuncioBuscadoSelect!!.numVecesVisto =
                                                anuncio.numVecesVisto
                                        } else {
                                            errorCarga.value = true
                                        }
                                        cargandoAnuncio.value = false
                                        loadingAnuncioVista.value = false
                                    }
                                } else {
                                    errorCarga.value = true
                                }
                            }
                        } else {
                            cargandoAnuncio.value = false
                            loadingAnuncioVista.value = false
                        }
                    }
                }else {
                    errorCarga.value = true
                    cargandoAnuncio.value = false
                    loadingAnuncioVista.value = false
                }
            }
        }else {
            Scaffold(
                backgroundColor = Color(0xff333542),
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate(Rutas.BuscarAnuncio.route) }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Volver atrás",
                                    tint = Color(0xfffcffff)
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "Detalles Anuncio",
                                fontSize = 19.sp,
                                color = Color(0xfffcffff),
                                fontFamily = FontFamily(
                                    Font(R.font.comforta)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        backgroundColor = Color(0xFF1B1C29),
                        actions = {
                            IconButton(
                                onClick = { /*TODO*/ },
                                enabled = false,
                                content = {}) //Lo hacemos sin contenido, solo servirá para hacer que el titulo se quede centrado
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(), backgroundColor = Color(
                                0xFF266E86
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box(Modifier.background(color = Color(0xFF999dba))) {
                                    Column(Modifier.padding(5.dp)) {
                                        Text(
                                            text = StaticVariables.anuncioBuscadoSelect!!.categoria,
                                            fontSize = 23.sp,
                                            color = Color(0xFF202020),
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Divider(thickness = 1.dp)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = StaticVariables.anuncioBuscadoSelect!!.nombre,
                                            fontSize = 16.sp,
                                            color = Color(0xFF202020),
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = "Descripción", fontSize = 13.sp, color = Color(
                                                0xFFE0E3EB
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Row(Modifier.fillMaxWidth()) {
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = StaticVariables.anuncioBuscadoSelect!!.descripcion,
                                                fontSize = 14.sp,
                                                color = Color(0xFF202020),
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        val precio = remember {
                                            if (StaticVariables.anuncioBuscadoSelect!!.precioPorHora) "${StaticVariables.anuncioBuscadoSelect!!.precio}€ Hora" else "${StaticVariables.anuncioBuscadoSelect!!.precio}"
                                        }
                                        Text(
                                            text = "Precio", fontSize = 13.sp, color = Color(
                                                0xFFE0E3EB
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Row(Modifier.fillMaxWidth()) {
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = precio,
                                                fontSize = 14.sp,
                                                color = Color(0xFF202020),
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = "Correo", fontSize = 13.sp, color = Color(
                                                0xFFE0E3EB
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Row(Modifier.fillMaxWidth()) {
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = StaticVariables.correoAnuncioBuscadoSelect!!,
                                                fontSize = 14.sp,
                                                color = Color(0xFF202020),
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.numvistas),
                                            contentDescription = "Num veces visto",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .height(30.dp)
                                                .width(30.dp)
                                        )
                                        Text(
                                            text = "Visualizaciones",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.height(7.dp))
                                        Text(
                                            text = StaticVariables.anuncioBuscadoSelect!!.numVecesVisto.toString(),
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )

                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = "valoracion",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .height(30.dp)
                                                .width(30.dp)
                                        )
                                        Text(
                                            text = "Valoración",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.height(7.dp))
                                        Text(
                                            text = StaticVariables.puntuacionAnuncioBuscado
                                                .toString(), fontSize = 13.sp, color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            onClick = {
                                   navController.navigate(Rutas.AnunciosBuscadosReviews.route)
                            },
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFD5CC21)
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Revisar",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(0.dp, 7.dp),
                                    fontFamily = FontFamily(Font(R.font.comforta))
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.reviews),
                                    contentDescription = "Reseñas",
                                    tint = Color(0xfffcffff),
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                val anuncioFavorito = AnunciosGuardadosPost(
                                    StaticVariables.anuncioBuscadoSelect!!._id,
                                    StaticVariables.usuario!!._id
                                )
                                loading.value = true
                                anunciosGuardadosViewModel.agregarAnuncioFavoritos(anuncioFavorito) { anuncio ->
                                    if (anuncio == null) {
                                        errorMsj.value =
                                            "Error al guardar el anuncio\ninténtelo más tarde"
                                        showDialogError.value = true
                                    } else {
                                        StaticVariables.anunciosFavoritos.add(anuncio)
                                        StaticVariables.anunciosBuscadosEmpre.remove(StaticVariables.anuncioBuscadoSelect)
                                        StaticVariables.anuncioBuscadoSelect = null
                                        navController.navigate(Rutas.BuscarAnuncio.route) {
                                            popUpTo(
                                                Rutas.Main.route
                                            )
                                        }
                                    }
                                    loading.value = false
                                }
                            },
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF209956)
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Archivar",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(0.dp, 7.dp),
                                    fontFamily = FontFamily(Font(R.font.comforta))
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Reseñas",
                                    tint = Color(0xfffcffff)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
        dialogError(show = showDialogError, msj = errorMsj)
        dialogLoading(show = loading)
        loadingAnuncio(loading = cargandoAnuncio )
        errorCargarAnuncio(show = errorCarga, loading = loadingAnuncioVista)
    }
}

@Composable
fun errorCargarAnuncio(show: MutableState<Boolean>, loading : MutableState<Boolean>){
    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xff3b3d4c)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                    Text(
                        text = "Error al cargar Anuncio",
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
                        text = "Ha ocurrido un error\nal cargar el anuncio\ninténtelo más tarde.",
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
fun loadingAnuncio(loading : MutableState<Boolean>){
    if (loading.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff333542)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color(0xfffcffff), modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff))
        }
    }
}

