package com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.AnuncioModel
import com.example.onec.Models.ModelOferta
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.ViewModels.AnunciosGuardadosViewModel
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ViewModels.ResenyaViewModel
import com.example.onec.Vistas.Main.Anuncios.loadingAnuncio
import com.example.onec.ui.theme.OnecTheme
import kotlin.math.absoluteValue

@Composable
fun anunciosMainEmpresario(navController: NavController) {
    OnecTheme() {
    
        val loading = remember {
            mutableStateOf(false)
        }
        val showError = remember {
            mutableStateOf(false)
        }
        val showListaFav = remember {
            mutableStateOf(false)
        }
        val showListaVacia = remember {
            mutableStateOf(false)
        }
        val contador = remember {
            mutableStateOf(0)
        }
        val numItems = remember {
            mutableStateOf(0)
        }
        Scaffold( topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF1B1C29),
                title = {
                    Text(
                        text = "Anuncios", fontFamily = FontFamily(Font(R.font.comforta)), color = Color(0xfffcffff), fontWeight = FontWeight.W500
                    ) },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Rutas.BuscarAnuncio.route)
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar anuncios", tint = Color(0xfffcffff))
                    }
                }, modifier = Modifier
                    .shadow(elevation = 0.dp)
                    .fillMaxHeight(0.08f)
            )
        }){
            if (!StaticVariables.anunciosFavBuscados) {
                loading.value = true
            }else if (StaticVariables.anunciosFavoritos.isEmpty()){
                showListaVacia.value = true
            }else if(StaticVariables.anunciosFavoritos.isNotEmpty()) {
                showListaFav.value = true
            }
            listaAnunciosFav(show = showListaFav, navController = navController, errorCarga = showError)
            listaVacia(show = showListaVacia)
            errorCargarAnunciosFav(showError, loading = loading)
            cargando(loading = loading, showListaEmpty = showListaVacia, showListaFav = showListaFav, showError = showError)
        }
    }
}


@Composable
fun listaAnunciosFav(show : MutableState<Boolean>, navController: NavController, errorCarga : MutableState<Boolean>){

    val loginRegistroViewModel = remember {
        LoginRegistroViewModel()
    }

    val anuncioViewModel = remember {
        AnuncioViewModel()
    }

    val resenyaViewModel = remember {
        ResenyaViewModel()
    }


    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 5.dp)) {
                    LazyColumn() {
                        items(StaticVariables.anunciosFavoritos) { anuncio ->

                            val loading = remember {
                                mutableStateOf(true)
                            }

                            val correo = remember {
                                mutableStateOf<String?>(null)
                            }

                            val anuncioModel = remember {
                                mutableStateOf<AnuncioModel?>(null)
                            }

                            val puntuacion = remember {
                                mutableStateOf<Float?>(null)
                            }

                            if (loading.value) {
                                //cargamos los anuncios
                                anuncioViewModel.obtenerAnuncio(anuncio.id_anuncio) { modeloAnuncio ->
                                    if (modeloAnuncio != null) {
                                        loginRegistroViewModel.obtenerUsuario(anuncio.id_user) { usuario ->
                                            if (usuario != null) {
                                                correo.value = usuario.email
                                                anuncioModel.value = modeloAnuncio
                                                resenyaViewModel.calcularPuntuacionAnuncio(modeloAnuncio._id) { punt ->
                                                   if (punt != null) {
                                                       puntuacion.value = punt
                                                       loading.value = false
                                                   }
                                                }

                                            } else {
                                                show.value = false
                                                errorCarga.value = true
                                            }

                                        }

                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.height(5.dp))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            StaticVariables.anuncioGuardadoSelect = anuncio
                                            StaticVariables.anuncioFavSelect = anuncioModel.value
                                            StaticVariables.correoAnuncioFavSelect = correo.value
                                            StaticVariables.puntuacionAnuncioFavSelect = puntuacion.value
                                            navController.navigate(Rutas.AnuncioFavDetalles.route)
                                        }, backgroundColor = Color(
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
                                                    text = anuncioModel.value!!.categoria,
                                                    fontSize = 23.sp,
                                                    color = Color(0xFF202020),
                                                    fontWeight = FontWeight.Bold,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Divider(thickness = 1.dp)
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    text = anuncioModel.value!!.nombre,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF202020),
                                                    fontWeight = FontWeight.Bold,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                                Text(
                                                    text = anuncioModel.value!!.descripcion,
                                                    fontSize = 14.sp,
                                                    color = Color(0xFF202020),
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(3.dp))
                                                Text(
                                                    text = "50€ Hora",
                                                    fontSize = 15.sp,
                                                    color = Color(0xFF202020),
                                                    fontWeight = FontWeight.Bold
                                                )
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
                                                        .height(25.dp)
                                                        .width(25.dp)
                                                )
                                                Text(
                                                    text = "Visualizaciones",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                                Spacer(modifier = Modifier.height(7.dp))
                                                Text(
                                                    text = anuncioModel.value!!.numVecesVisto.toString(),
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
                                                        .height(25.dp)
                                                        .width(25.dp)
                                                )
                                                Text(
                                                    text = "Valoración",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                                Spacer(modifier = Modifier.height(7.dp))
                                                Text(
                                                    text = puntuacion.value
                                                        .toString(),
                                                    fontSize = 13.sp,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun listaVacia(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                   Text(text = "Ningún anuncio Guardado", fontSize = 19.sp, color = Color(0xfffcffff))
                }
            }
        }
    }
}

@Composable
fun errorCargarAnunciosFav(show: MutableState<Boolean>, loading: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            val scrollState = rememberScrollState(0)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(Color(0xff3b3d4c)),
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
                        text = "Ha ocurrido un error\nal cargar los anuncios del guardados\ninténtelo más tarde.",
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
fun cargando(loading : MutableState<Boolean>, showListaEmpty : MutableState<Boolean>, showListaFav : MutableState<Boolean>, showError: MutableState<Boolean>) {
    if (loading.value) {
        val anuncioFavViewModel = remember {
            AnunciosGuardadosViewModel()
        }

        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
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
                anuncioFavViewModel.obtenerAnunciosFavoritosUsuario { anunciosFav ->
                    if (anunciosFav != null && anunciosFav.isNotEmpty()) {
                        if (anunciosFav == null) {
                            showError.value = true
                        } else if (anunciosFav.isEmpty()) {
                            showListaEmpty.value = true
                            loading.value = true
                        } else if (anunciosFav.isNotEmpty()) {
                            showListaFav.value = true
                            StaticVariables.anunciosFavoritos = anunciosFav
                        } else {
                            showError.value = true
                        }
                        loading.value = false
                    } else if (anunciosFav != null && anunciosFav.isEmpty()) {
                        Log.d("Entra", "ernkjl")
                        //Se cargan los anuncios pero el usuario no tiene ninguno en favoritos
                        showListaEmpty.value = true
                        StaticVariables.anunciosFavBuscados = true
                        loading.value = false
                    } else {
                        //Mostramos un error de carga
                        showError.value = true
                        loading.value = false
                    }
                }

            }
        }
    }
}

@Composable
fun datosItems( email : String) {
    OnecTheme() {
    }
}