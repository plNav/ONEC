package com.example.onec.Vistas.Main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Models.AnuncioModel
import com.example.onec.Models.ModelOferta
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.ViewModels.AnunciosGuardadosViewModel
import com.example.onec.ViewModels.ResenyaViewModel
import com.example.onec.ViewModels.VisualizacionesViewModel
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anuncios(selected: MutableState<Boolean>,navController: NavController) {
    if(selected.value) {

        val anuncioModel = remember {
            mutableStateOf<AnuncioModel?>(null)
        }

        val eliminando = remember {
            mutableStateOf(false)
        }

        val errorMsj = remember {
            mutableStateOf("Error al realizar la operación")
        }

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

        val listaItemsEliminados = remember {
            mutableStateOf<MutableList<AnuncioModel>>(mutableStateListOf())
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
                            StaticVariables.anunciosUsuario = anuncios.toMutableStateList()
                            isLoading.value = false
                        }else if (anuncios.isNotEmpty()) {
                            //El usuario tiene anuncios ya creados
                            showList.value = true
                            isLoading.value = false
                            StaticVariables.anunciosBuscados = true
                            StaticVariables.anunciosUsuario = anuncios.toMutableStateList()
                        }else {
                            //Mostrar Error por si acaso
                            showError.value = true
                            isLoading.value = false
                        }
                    }
                }else {
                    listAnuncios(showList, navController, eliminando, anuncioModel, listaItemsEliminados)
                    listAnunciosEmpty(showlistEmpty)
                    errorCarga(isLoading,showError)
                    dialogEliminando(show = eliminando, showError, anuncioModel.value, listaItemsEliminados, errorMsj)
                    dialogError(show = showError , msj = errorMsj )
                }
            }
        }
    }
}







@Composable
fun dialogEliminando(show: MutableState<Boolean>, showDialogError : MutableState<Boolean>, anuncio : AnuncioModel?, listaItemsEliminados : MutableState<MutableList<AnuncioModel>>, errorMsj : MutableState<String>) {
    if (show.value) {
        OnecTheme() {
            val errorMsj = remember {
                mutableStateOf("Error al eliminar el anuncio")
            }
            val resenyaViewModel = remember {
                ResenyaViewModel()
            }

            val anunciosGuardadosViewModel = remember {
                AnunciosGuardadosViewModel()
            }

            val visualizacionesViewModel = remember {
                VisualizacionesViewModel()
            }

            val anuncioViewModel = remember {
                AnuncioViewModel()
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
            LaunchedEffect(key1 = "empty") {
                resenyaViewModel.eliminarReviewsAnuncio(
                    anuncio!!._id
                ) { did ->
                    if (did) {
                        anunciosGuardadosViewModel.borrarAnunciosGuardadosIdAnuncio(anuncio!!._id) { didFav ->
                            if (didFav) {
                                visualizacionesViewModel.eliminarVisualizacionesAnuncio(anuncio!!._id) { didVi ->
                                    if (didVi) {
                                        //Todo lo anterior se ha eliminado, ya podemos eliminar el anuncio
                                        anuncioViewModel.eliminarAnuncio(anuncio!!._id) { borrado ->
                                            if (borrado) {
                                                show.value = false
                                                listaItemsEliminados.value.add(anuncio)
                                                StaticVariables.anunciosUsuario.remove(anuncio)
                                            }else {
                                                showDialogError.value = true
                                                show.value = false
                                                errorMsj.value = "Error al eliminar el anuncio\ninténtelo más tarde"
                                            }
                                        }
                                    }else {
                                        showDialogError.value = true
                                        show.value = false
                                        errorMsj.value = "Error al eliminar el anuncio\ninténtelo más tarde"
                                    }
                                }
                            }else {
                                showDialogError.value = true
                                show.value = false
                                errorMsj.value = "Error al eliminar el anuncio\ninténtelo más tarde"
                            }
                        }
                    } else {
                        showDialogError.value = true
                        show.value = false
                        errorMsj.value = "Error al eliminar el anuncio\ninténtelo más tarde"
                    }
                }
            }
        }
    }
}

/**
 * listAnuncios -> mostrará la vista de los anuncios que tiene el usuario
 * */
@Composable
fun listAnuncios(show: MutableState<Boolean>, navController: NavController, eliminando : MutableState<Boolean> , anuncio: MutableState<AnuncioModel?>, listaItemsEliminados : MutableState<MutableList<AnuncioModel>>) {

    val showListEmpty = remember {
        mutableStateOf(false)
    }

    val anuncioViewModel = remember {
        AnuncioViewModel()
    }

    val showDialogError = remember {
        mutableStateOf(false)
    }

    val errorMsj = remember {
        mutableStateOf("Error desconocido")
    }
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
                    if (StaticVariables.anunciosUsuario.isNotEmpty()) {
                        items(StaticVariables.anunciosUsuario) { item ->
                            Spacer(modifier = Modifier.height(10.dp))
                            AnimatedVisibility(
                                visible = !listaItemsEliminados.value.contains(item),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 700))
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            StaticVariables.anuncioSeleccionado = item
                                            navController.navigate(Rutas.AnuncioDetalles.route)
                                        },
                                    shape = RoundedCornerShape(7.dp),
                                    color = Color.Transparent
                                ) {
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.Transparent)) {
                                        Column(Modifier.fillMaxWidth()) {
                                            Surface(modifier = Modifier.fillMaxWidth(), color = Color( 0xFF141624), shape = RoundedCornerShape(7.dp,7.dp,0.dp,0.dp)) {
                                                Column(modifier = Modifier
                                                    .padding(vertical = 5.dp, horizontal = 3.dp)
                                                    .background(Color.Transparent)
                                                    .fillMaxWidth()) {
                                                    Text(text = item.nombre, fontSize = 16.sp, textAlign = TextAlign.Center, color = Color(0xfffcffff))
                                                }
                                            }
                                            Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xfffcffff), shape = RoundedCornerShape(0.dp,0.dp,7.dp,7.dp)) {
                                                Row(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(3.dp, 5.dp, 3.dp, 0.dp)
                                                    ) {
                                                    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                                                        Row() {
                                                            Text(
                                                                text = "Categoría",
                                                                fontSize = 16.sp,
                                                                color = Color(
                                                                    0xFF272727
                                                                )
                                                            )
                                                            Spacer(modifier = Modifier.width(3.dp))
                                                            Text(
                                                                text = item.categoria,
                                                                fontSize = 16.sp,
                                                                overflow = TextOverflow.Ellipsis,
                                                                color = Color(
                                                                    0xFF22596B
                                                                )
                                                            )
                                                        }
                                                        Row() {
                                                            Text(
                                                                text = "Precio",
                                                                fontSize = 16.sp,
                                                                color = Color(
                                                                    0xFF272727
                                                                )
                                                            )
                                                            Spacer(modifier = Modifier.width(3.dp))
                                                            val precio = remember {
                                                                if (item.precioPorHora) "${item.precio}€ Hora" else "${item.precio}€"
                                                            }
                                                            Text(
                                                                text = precio,
                                                                fontSize = 16.sp,
                                                                overflow = TextOverflow.Ellipsis,
                                                                color = Color(
                                                                    0xFF22596B
                                                                )
                                                            )
                                                        }
                                                    }
                                                    IconButton(onClick = {
                                                        anuncio.value = item
                                                        eliminando.value = true
                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Delete,
                                                            contentDescription = "Borra",
                                                            tint = Color(0xFF1783A7)
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }else {
                        show.value = false
                        showListEmpty.value = true
                    }
                }
                listAnunciosEmpty(show = showListEmpty)
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
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
