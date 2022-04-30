package com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavController
import com.example.onec.Models.ResenyaModel
import com.example.onec.Models.UsuarioModel
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ViewModels.ResenyaViewModel
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anunciosBuscadosReviews(navController: NavController) {
    OnecTheme() {

        val reviews = remember {
            mutableStateOf<MutableList<ResenyaModel>?>(null)
        }


        val loading = remember {
            mutableStateOf(true)
        }

        val showListReviews = remember {
            mutableStateOf(false)
        }

        val showEmptyReviews = remember {
            mutableStateOf(false)
        }

        val showErrorCarga  = remember {
        mutableStateOf(false)
        }

        errorCargaReviews(show = showErrorCarga, loading = loading)
        loadingReviews(show = loading, showListReviews, showEmptyReviews, showErrorCarga, reviews)
        emptyReviews(show = showEmptyReviews, navController = navController)
        listReviews(show = showListReviews, navController = navController, reviews)
    }
}

@Composable
fun errorCargaReviews(show: MutableState<Boolean>, loading : MutableState<Boolean>) {
    if (show.value) {
        val scrollState = rememberScrollState(0)
        OnecTheme() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color(0xff333542))
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Error al cargar Anuncios",
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
                    text = "Error al buscar anuncios\ninténtelo más tarde.",
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

@Composable
fun loadingReviews(show: MutableState<Boolean>, showList : MutableState<Boolean>, showEmpty : MutableState<Boolean>, showError: MutableState<Boolean>, resenyas : MutableState<MutableList<ResenyaModel>?>) {
    if (show.value) {
        val resenyaViewModel = remember {
            ResenyaViewModel()
        }
        OnecTheme() {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xff333542)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp), color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff))
                    LaunchedEffect(key1 = "nothing") {
                        resenyaViewModel.obtenerResenyasAnuncio(StaticVariables.anuncioBuscadoSelect!!._id) { reviews ->
                            if (reviews == null) {
                                show.value = false
                                showError.value = true
                            } else if (reviews.isEmpty()) {
                                show.value = false
                                showEmpty.value = true
                            } else if (reviews.isNotEmpty()) {
                                resenyas.value = reviews
                                show.value = false
                                showList.value = true
                            } else {
                                show.value = false
                                showError.value = true
                            }
                        }
                    }
                }
        }
    }
}

@Composable
fun emptyReviews(show: MutableState<Boolean>, navController: NavController) {
    if (show.value) {
        OnecTheme {
            val scrollState = rememberScrollState(0)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
                    .verticalScroll(scrollState)
            ) {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(Rutas.AnuncioBuscadoDetalles.route) {
                                popUpTo(
                                    Rutas.Main.route
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
                            text = "Valoraciones",
                            fontSize = 19.sp,
                            color = Color(0xfffcffff),
                            fontFamily = FontFamily(
                                Font(R.font.comforta)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    actions = {
                        IconButton(
                            onClick = { /*TODO*/ },
                            enabled = false,
                            content = {}) //Lo hacemos sin contenido, solo servirá para hacer que el titulo se quede centrado
                    }
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                     Text(text = "No existe ninguna reseña\npara este anuncio", fontSize = 19.sp, textAlign = TextAlign.Center, color =  Color(0xfffcffff))
                    }
                }
            }
        }
    }
}

@Composable
fun listReviews(show: MutableState<Boolean>, navController: NavController , reviews : MutableState<MutableList<ResenyaModel>?>) {
    if (show.value) {
        OnecTheme {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff333542)), horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigate(Rutas.AnuncioBuscadoDetalles.route) {
                                    popUpTo(
                                        Rutas.Main.route
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
                                text = "Valoraciones",
                                fontSize = 19.sp,
                                color = Color(0xfffcffff),
                                fontFamily = FontFamily(
                                    Font(R.font.comforta)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,
                        actions = {
                            IconButton(
                                onClick = { /*TODO*/ },
                                enabled = false,
                                content = {}) //Lo hacemos sin contenido, solo servirá para hacer que el titulo se quede centrado
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
                items(reviews.value!!) { review ->
                   val loading = remember {
                       mutableStateOf(true)
                   }

                   val email  = remember {
                       mutableStateOf<String?>(null)
                   }

                   if (loading.value) {
                       val loginRegVm = remember {
                           LoginRegistroViewModel()
                       }
                       loginRegVm.obtenerUsuario(id = review.id_user) { us ->
                           if (us != null) {
                               email.value = us.email
                               loading.value = false
                           }
                       }
                   }else {
                        //Mostramos la reseña
                       Card(modifier = Modifier.fillMaxWidth(0.9f), backgroundColor = Color(0xFF999dba) ) {
                           Box(modifier = Modifier.fillMaxWidth()) {
                               Column(modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(10.dp)) {
                                        Text(
                                            text = email.value!!,
                                            fontSize = 15.sp,
                                            color = Color(0xFF202020),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Row(Modifier.fillMaxWidth()) {
                                        Icon(
                                            painter = painterResource(if (review.puntuacion >= 1) R.drawable.fullstar else R.drawable.emptystar),
                                            contentDescription = "Estrella",
                                            modifier = Modifier
                                                .width(15.dp)
                                                .height(15.dp),
                                            tint = if (review.puntuacion >= 1) Color(0xffFFE7E425) else Color(0xFFE0E3EB)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Icon(
                                            painter = painterResource(if (review.puntuacion >= 2) R.drawable.fullstar else R.drawable.emptystar),
                                            contentDescription = "Estrella",
                                            modifier = Modifier
                                                .width(15.dp)
                                                .height(15.dp),
                                            tint = if (review.puntuacion >= 2) Color(0xffFFE7E425) else Color(0xFFE0E3EB)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Icon(
                                            painter = painterResource(if (review.puntuacion >= 3) R.drawable.fullstar else R.drawable.emptystar),
                                            contentDescription = "Estrella",
                                            modifier = Modifier
                                                .width(15.dp)
                                                .height(15.dp),
                                            tint = if (review.puntuacion >= 3) Color(0xffFFE7E425) else Color(0xFFE0E3EB)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Icon(
                                            painter = painterResource(if (review.puntuacion >= 4) R.drawable.fullstar else R.drawable.emptystar),
                                            contentDescription = "Estrella",
                                            modifier = Modifier
                                                .width(15.dp)
                                                .height(15.dp),
                                            tint = if (review.puntuacion >= 4) Color(0xffFFE7E425) else Color(0xFFE0E3EB)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Icon(
                                            painter = painterResource(if (review.puntuacion == 5) R.drawable.fullstar else R.drawable.emptystar),
                                            contentDescription = "Estrella",
                                            modifier = Modifier
                                                .width(15.dp)
                                                .height(15.dp),
                                            tint = if (review.puntuacion == 5) Color(0xffFFE7E425) else Color(0xFFE0E3EB)
                                        )
                                    }
                                   Spacer(modifier = Modifier.height(15.dp))
                                   Card(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(
                                       0xFF8488A2
                                   )
                                   ) {
                                       Column(modifier = Modifier
                                           .fillMaxWidth()
                                           .padding(10.dp)
                                           .height(100.dp)) {
                                           Text(
                                               text = "Detalles",
                                               fontSize = 14.sp,
                                               color = Color(0xFFE0E3EB),
                                               fontWeight = FontWeight.Bold
                                           )
                                           Spacer(modifier = Modifier.height(3.dp))
                                           Row(modifier = Modifier.fillMaxWidth()) {
                                               Spacer(modifier = Modifier.width(7.dp))
                                               Text(
                                                   text = review.descripcion,
                                                   fontSize = 16.sp,
                                                   color = Color(0xFF202020),
                                                   maxLines = 3,
                                                   overflow = TextOverflow.Ellipsis
                                               )
                                           }
                                       }
                                   }
                               }
                           }
                       }
                       Spacer(modifier = Modifier.height(15.dp))
                   }
                }
            }
        }
    }
}