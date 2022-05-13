package com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Models.AnuncioPost
import com.example.onec.Models.AnunciosGuardadosPost
import com.example.onec.Models.VisualizacionesPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.*
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Main.Anuncios.anuncioCreado
import com.example.onec.Vistas.Main.Anuncios.dialogEliminando
import com.example.onec.Vistas.Main.Anuncios.loadingAnuncio
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anuncioFavoritoDetalles(navController: NavController) {
    OnecTheme() {
        
        val eliminandoAnuncio  = remember {
            mutableStateOf(false)
        } 

        val showAnuncioEliminado = remember {
            mutableStateOf(false)
        }

        val loginregVM = remember {
            LoginRegistroViewModel()
        }

        val resenyaViewModel = remember {
            ResenyaViewModel()
        }

        val errorCarga = remember {
            mutableStateOf(false)
        }

        val loading = remember {
            mutableStateOf(false)
        }

        val yaPuntuado = remember {
            mutableStateOf<Boolean?>(null)
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


        if (loadingAnuncioVista.value) {
            loading.value = true
            //Primero consultamos si el usuario ha realizado alguna reseña de el anuncio
            resenyaViewModel.calcularPuntuacionAnuncio(StaticVariables.anuncioFavSelect!!._id) { puntuacion ->
                if (puntuacion == null) {
                    loadingAnuncioVista.value = false
                    loading.value = false
                    errorCarga.value = true

                }else {
                    loginregVM.obtenerUsuario(StaticVariables.anuncioFavSelect!!.id_user) { userAnuncio ->
                        if (userAnuncio == null) {
                            loadingAnuncioVista.value = false
                            loading.value = false
                            errorCarga.value = true
                        }else {
                            resenyaViewModel.obtenerResenyasUsuarioAnuncio(
                                StaticVariables.anuncioFavSelect!!._id,
                                StaticVariables.usuario!!._id
                            ) { reviews ->
                                if (reviews == null) {
                                    loadingAnuncioVista.value = false
                                    loading.value = false
                                    errorCarga.value = true
                                } else if (reviews.isEmpty()) {
                                    yaPuntuado.value = false
                                    loading.value = false
                                    loadingAnuncioVista.value = false
                                    StaticVariables.correoAnuncioFavSelect = userAnuncio.email
                                    StaticVariables.puntuacionAnuncioFavSelect = puntuacion

                                } else if (reviews.isNotEmpty()) {
                                    yaPuntuado.value = true
                                    loading.value = false
                                    loadingAnuncioVista.value = false
                                    StaticVariables.correoAnuncioFavSelect = userAnuncio.email
                                    StaticVariables.puntuacionAnuncioFavSelect = puntuacion
                                } else {
                                    errorCarga.value = true
                                }

                            }
                        }
                    }
                }
            }
        }else {
            Scaffold(
                backgroundColor = Color(0xff333542),
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate(Rutas.Main.route) {popUpTo(0)} }) {
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
                                            text = StaticVariables.anuncioFavSelect!!.categoria,
                                            fontSize = 23.sp,
                                            color = Color(0xFF202020),
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Divider(thickness = 1.dp)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = StaticVariables.anuncioFavSelect!!.nombre,
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
                                                text = StaticVariables.anuncioFavSelect!!.descripcion,
                                                fontSize = 14.sp,
                                                color = Color(0xFF202020),
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        val precio = remember {
                                            if (StaticVariables.anuncioFavSelect!!.precioPorHora) "${StaticVariables.anuncioFavSelect!!.precio}€ Hora" else "${StaticVariables.anuncioFavSelect!!.precio}"
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
                                                text = StaticVariables.correoAnuncioFavSelect!!,
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
                                            text = StaticVariables.anuncioFavSelect!!.numVecesVisto.toString(),
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
                                            text = StaticVariables.puntuacionAnuncioFavSelect
                                                .toString(), fontSize = 13.sp, color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            onClick = {
                                navController.navigate(Rutas.AnuncioFavReviews.route)
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
                        Spacer(modifier = Modifier.height(15.dp))
                        if (yaPuntuado.value == false) {
                            Button(
                                onClick = {
                                    navController.navigate(Rutas.AnuncioPuntuar.route)
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
                                        text = "Puntuar",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(0.dp, 7.dp),
                                        fontFamily = FontFamily(Font(R.font.comforta))
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "Reseñas",
                                        tint = Color(0xfffcffff),
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        Button(
                            onClick = {
                                      eliminandoAnuncio.value = true
                            },
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFAD3B3F)
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Eliminar",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(0.dp, 7.dp),
                                    fontFamily = FontFamily(Font(R.font.comforta))
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Reseñas",
                                    tint = Color(0xfffcffff),
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
        dialogError(show = showDialogError, msj = errorMsj)
        loadingAnuncio(show = loading)
        errorCargarAnuncio(show = errorCarga, loading = loadingAnuncioVista)
        eliminandoAnuncioFav(show = eliminandoAnuncio, showDialogError = showDialogError, errorMsj = errorMsj, navController = navController )

    }
}



@Composable
fun eliminandoAnuncioFav(show: MutableState<Boolean>, showDialogError : MutableState<Boolean> , errorMsj : MutableState<String>, navController: NavController) {
    if (show.value) {
        OnecTheme() {

            val anunciosGuardadosViewModel = remember {
                AnunciosGuardadosViewModel()
            }

            Dialog(onDismissRequest = { /*TODO*/ }) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp), color = Color(0xfffcffff)
                    )
                    Text(
                        text = "Eliminando...",
                        fontSize = 16.sp,
                        color = Color(0xfffcffff),
                        textAlign = TextAlign.Center
                    )
                }
            }
            LaunchedEffect(key1 = "Nothing") {
                anunciosGuardadosViewModel.borrarAnuncioFavoritos(StaticVariables.anuncioGuardadoSelect!!._id) { borrado ->
                    if (borrado) {
                        StaticVariables.anunciosFavoritos.remove(StaticVariables.anuncioGuardadoSelect)
                        StaticVariables.anuncioFavSelect = null
                        navController.navigate(Rutas.Main.route) { popUpTo(0) }
                    }else {
                        show.value = false
                        showDialogError.value = true
                        errorMsj.value = "Error al eliminar el anuncio"
                    }
                }
            }
        }
    }
}
