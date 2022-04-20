package com.example.onec.Vistas.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.ModelOferta
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anuncios(selected: MutableState<Boolean>,navController: NavController) {
    if(selected.value) {

        val isLoading = remember {
            mutableStateOf(false)
        }

        val showError = remember {
            mutableStateOf(false)
        }


        val showlistEmpty = remember {
            mutableStateOf(false)
        }

        val showList = remember {
            mutableStateOf(false)
        }

        val anuncioViewModel = remember {
            AnuncioViewModel()
        }

        OnecTheme() {
            Scaffold( topBar = {
                TopAppBar(
                    backgroundColor = Color(0xFF1B1C29),
                    title = {
                        Text(
                            text = "Mis anuncios", fontFamily = FontFamily(Font(R.font.comforta)), color = Color(0xfffcffff), fontWeight = FontWeight.W500
                        ) },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Rutas.CrearAnuncio.route)
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Agregar oferta", tint = Color(0xfffcffff))
                        }
                    }, modifier = Modifier
                        .shadow(elevation = 0.dp)
                        .fillMaxHeight(0.08f)
                )
            }){
                //Primero comprobamos, que no se haya cargado ya la lista de anuncios del usuario, si no está cargada ponemos en true el isLoading
                if (!StaticVariables.anunciosBuscados ) {
                    isLoading.value = true
                }else {
                     if(StaticVariables.anunciosUsuario.isEmpty()) {
                        //La respuesta llega correctamente, pero el usuario no tienen ningun anuncio creado
                        showlistEmpty.value = true
                    }else if (StaticVariables.anunciosUsuario.isNotEmpty()) {
                        //El usuario tiene anuncios ya creados
                        showList.value = true
                    }else {
                        //Mostrar Error por si acaso
                        showError.value = true
                    }
                }

                //Gestionamos la carga de los anuncios
                if (isLoading.value) {
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
                    anuncioViewModel.obtenerAnunciosUsuario(StaticVariables.usuario!!._id) { anuncios ->
                        if (anuncios == null) {
                           //Mostrar error
                            showError.value = true
                            isLoading.value = false
                        }else if(anuncios.isEmpty()) {
                            //La respuesta llega correctamente, pero el usuario no tienen ningun anuncio creado
                            showlistEmpty.value = true
                            StaticVariables.anunciosBuscados = true
                            StaticVariables.anunciosUsuario = anuncios.toMutableList()
                            isLoading.value = false
                        }else if (anuncios.isNotEmpty()) {
                            //El usuario tiene anuncios ya creados
                            showList.value = true
                            isLoading.value = false
                            StaticVariables.anunciosBuscados = true
                            StaticVariables.anunciosUsuario = anuncios.toMutableList()
                        }else {
                            //Mostrar Error por si acaso
                            showError.value = true
                            isLoading.value = false
                        }
                    }
                }else {
                    listAnuncios(showList)
                    listAnunciosEmpty(showlistEmpty)
                    errorCarga(isLoading,showError)
                }
            }
        }
    }
}






/**
 * listAnuncios -> mostrará la vista de los anuncios que tiene el usuario
 * */
@Composable
fun listAnuncios(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {

                }
            }
        }
    }
}






/**
 * listAnunciosEmpty -> Mostrará una vista en caso de que el usuario no haya creado ningún anuncio
 * */
@Composable
fun listAnunciosEmpty(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Column(Modifier.fillMaxSize().background(Color(0xff3b3d4c)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Ningún anuncio creado", fontSize = 19.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
            }
        }
    }
}





/**
 *  errorCarga -> Mostrara una vista mostrando un error, con un botón para reintentar cargarlo
 * */
@Composable
fun errorCarga(show: MutableState<Boolean>, isLoading : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color(0xff3b3d4c)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Error al cargar Anuncios", fontSize = 19.sp, color = Color(0xfffcffff))
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.errorlog),
                    contentDescription = "Error log"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Ha ocurrido un error\nal cargar los anuncios del usuario\nreinténtelo más tarde.", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        isLoading.value = true
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