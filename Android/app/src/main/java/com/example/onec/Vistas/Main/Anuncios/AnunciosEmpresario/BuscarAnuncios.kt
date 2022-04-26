package com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario

import android.util.Log
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.onec.Models.AnuncioModel
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.ViewModels.AnunciosGuardadosViewModel
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme

@Composable
fun buscarAnuncio(navController: NavController) {
    OnecTheme() {

        val anuncioViewModel = remember {
            AnuncioViewModel()
        }

        val anunciosGuardadosViewModel = remember {
            AnunciosGuardadosViewModel()
        }

        val loading = remember {
            mutableStateOf(false)
        }

        val showError = remember {
            mutableStateOf(false)
        }

        val showAnuncioNoEncontrado = remember {
            mutableStateOf(false)
        }

        val showListaAnuncios = remember {
            mutableStateOf(false)
        }

        val listaAnuncios = remember {
            mutableStateOf(StaticVariables.anunciosBuscadosEmpre)
        }
        
        val scrollState = rememberScrollState(0)
        
        val busquedaAnuncio = remember {
            mutableStateOf("")
        }

        val showMsjExplicar = remember {
            mutableStateOf(false)
        }

        val showDialogError = remember {
            mutableStateOf(false)
        }

        val dialogMsj = remember {
            mutableStateOf("Error desconocido")
        }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 0.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Buscar anuncios",
                            fontSize = 19.sp,
                            color = Color(0xfffcffff),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        TextField(
                            value = busquedaAnuncio.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta)),
                                color = Color(0xFF266E86)
                            ),
                            label = {
                                Text(
                                    text = "Buscar",
                                    color = Color(0xFF292929),
                                    fontFamily = FontFamily(Font(R.font.comforta))
                                )
                            },
                            onValueChange = { it.also { busquedaAnuncio.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Add",
                                    tint = Color(0xFF388BA7),
                                    modifier = Modifier.clickable {
                                        if (busquedaAnuncio.value.isNullOrEmpty() ||busquedaAnuncio.value.isNullOrBlank() || busquedaAnuncio.value == "") {
                                            showDialogError.value = true
                                            dialogMsj.value = "De introducir un campo"
                                        }else {
                                            //Limpiamos el resto de variables
                                            showMsjExplicar.value = false
                                            showError.value = false
                                            showListaAnuncios.value = false
                                            showAnuncioNoEncontrado.value = false
                                            loading.value = true
                                            anunciosGuardadosViewModel.obtenerAnunciosFavoritosUsuario { anunciosFav ->
                                                if (anunciosFav == null) {
                                                    showError.value = true
                                                }else if (anunciosFav.isEmpty()) {
                                                    anuncioViewModel.buscarAnuncios(busquedaAnuncio.value) { anunciosEncontrados ->
                                                        if (anunciosEncontrados == null) {
                                                            showError.value = true
                                                        }else if (anunciosEncontrados.isEmpty()) {
                                                            showAnuncioNoEncontrado.value = true
                                                        }else if (anunciosEncontrados.isNotEmpty()) {
                                                            listaAnuncios.value = anunciosEncontrados
                                                            StaticVariables.anunciosBuscadosEmpre = anunciosEncontrados
                                                            StaticVariables.anuncioEmpreBuscado = true
                                                            showListaAnuncios.value = true
                                                        }else {
                                                            showError.value = true
                                                        }
                                                        loading.value = false
                                                    }
                                                }else {
                                                    val idAnunciosFav = anunciosFav.map { it.id_anuncio }
                                                    anuncioViewModel.buscarAnuncios(busquedaAnuncio.value) { anunciosEncontrados ->
                                                        if (anunciosEncontrados == null) {
                                                            showError.value = true
                                                        }else if (anunciosEncontrados.isEmpty()) {
                                                            showAnuncioNoEncontrado.value = true
                                                        }else if (anunciosEncontrados.isNotEmpty()) {
                                                            anunciosEncontrados.forEach { anuncio ->
                                                                if (!idAnunciosFav.contains(anuncio._id)) {
                                                                    StaticVariables.anunciosBuscadosEmpre.add(anuncio)
                                                                    StaticVariables.anuncioEmpreBuscado = true
                                                                    showListaAnuncios.value = true
                                                                }
                                                            }
                                                            //Crear una variable que compruebe que no sea empty la lista para que no pete
                                                            if (StaticVariables.anunciosBuscadosEmpre.isEmpty()) {
                                                                showAnuncioNoEncontrado.value = true
                                                            }
                                                        }else {
                                                            showError.value = true
                                                        }
                                                        loading.value = false
                                                    }
                                                }
                                            }

                                        }
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFFCFFFF),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF388BA7)
                            )
                        )
                    }
                    if (StaticVariables.anunciosBuscadosEmpre.isNotEmpty()) {
                        Log.d("noesVacio",StaticVariables.anunciosBuscadosEmpre.size.toString())
                        showListaAnuncios.value = true
                    }else {
                        showMsjExplicar.value = true
                    }
                    dialogError(show = showDialogError, msj = dialogMsj )
                    cargar(loading)
                    errorBusqueda(showError,loading)
                    listaAnunciosBuscados(showListaAnuncios, listaAnuncios.value, navController = navController)
                    noEncontrado(showAnuncioNoEncontrado)
                    mostrarMsjBuscar(show = showMsjExplicar)
                    
                }
            }
        }
        BackHandler() {
            StaticVariables.anuncioEmpreBuscado = false
            StaticVariables.anunciosBuscadosEmpre.clear()
            navController.navigate(Rutas.Main.route) {popUpTo(0)}
        }
    }

@Composable
fun mostrarMsjBuscar(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Introduzca un campo\npara buscar un anuncio",
                    fontSize = 19.sp,
                    color = Color(0xfffcffff)
                )
            }
        }
    }
}

@Composable
fun listaAnunciosBuscados(show: MutableState<Boolean>, anuncios : MutableList<AnuncioModel>, navController: NavController) {
    if (show.value) {
        OnecTheme() {
           LazyColumn(modifier = Modifier.fillMaxSize()) {
               items(anuncios) { anuncio ->

                   val loginRegistroViewModel = remember {
                       LoginRegistroViewModel()
                   }

                   val loading = remember {
                       mutableStateOf(true)
                   }

                   val correo = remember {
                       mutableStateOf<String?>(null)
                   }

                   if (loading.value) {
                       //Si está en true el loading, cargamos el correo del propietario del anuncio
                       loginRegistroViewModel.obtenerUsuario(anuncio.id_user) { usuario ->
                           if (usuario != null) {
                               correo.value = usuario.email
                           }
                           loading.value = false
                       }

                   }else {
                        //El correo ya está cargado, por lo tanto mostrar el anuncio en forma de card
                            Spacer(modifier = Modifier.height(5.dp))
                       Card(modifier = Modifier
                           .fillMaxWidth()
                           .clickable {
                                StaticVariables.anuncioBuscadoSelect = anuncio
                                StaticVariables.correoAnuncioBuscadoSelect = correo.value
                                navController.navigate(Rutas.AnuncioBuscadoDetalles.route)
                           }, backgroundColor = Color(
                           0xFF266E86
                       )) {
                           Column(modifier = Modifier
                               .fillMaxWidth()
                           ) {
                               Box(Modifier.background(color = Color(0xFF999dba))) {
                                   Column(Modifier.padding(5.dp)) {
                                       Text(
                                           text = anuncio.categoria,
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
                                           text = anuncio.nombre,
                                           fontSize = 16.sp,
                                           color = Color(0xFF202020),
                                           fontWeight = FontWeight.Bold,
                                           maxLines = 1,
                                           overflow = TextOverflow.Ellipsis
                                       )
                                       Spacer(modifier = Modifier.height(10.dp))
                                       Text(
                                           text = anuncio.descripcion,
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
                                           imageVector = Icons.Filled.ThumbUp,
                                           contentDescription = "Num veces visto",
                                           tint = Color.White,
                                           modifier =  Modifier.height(25.dp).width(25.dp)
                                       )
                                       Text(
                                           text = "Visualizaciones",
                                           fontSize = 12.sp,
                                           fontWeight = FontWeight.Bold,
                                           color = Color.White
                                       )
                                       Spacer(modifier = Modifier.height(7.dp))
                                       Text(
                                           text = anuncio.numVecesVisto.toString(), fontSize = 13.sp, color = Color.White
                                       )

                                   }
                                   Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                       Icon(
                                           imageVector = Icons.Filled.Star,
                                           contentDescription = "valoracion",
                                           tint = Color.White,
                                           modifier =  Modifier.height(25.dp).width(25.dp)
                                       )
                                       Text(
                                           text = "Valoración",
                                           fontSize = 12.sp,
                                           fontWeight = FontWeight.Bold,
                                           color = Color.White
                                       )
                                       Spacer(modifier = Modifier.height(7.dp))
                                       Text(
                                           text = anuncio.puntuacion.toInt().toString(), fontSize = 13.sp, color = Color.White
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
}

@Composable
fun noEncontrado(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "No se ha encontrado ningún anuncio", fontSize = 19.sp, color = Color(0xfffcffff))
            }
        }
    }
}

@Composable
fun cargar(show : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Column(
                modifier = Modifier.fillMaxSize(),
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
            }
        }
    }
}

@Composable
fun errorBusqueda(show: MutableState<Boolean>, loading : MutableState<Boolean>) {
    val scrollState = rememberScrollState(0)
    if (show.value) {
        OnecTheme() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
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
