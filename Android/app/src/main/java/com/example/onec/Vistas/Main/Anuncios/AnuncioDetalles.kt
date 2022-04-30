package com.example.onec.Vistas.Main.Anuncios

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Models.AnuncioModel
import com.example.onec.Models.AnuncioPost
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
import java.lang.Exception

@Composable
fun anuncioDetalle(navController: NavController){

    val showDialogLoading = remember {
        mutableStateOf(false)
    }

    val showDialogError = remember {
        mutableStateOf(false)
    }

    val errorMsj = remember {
        mutableStateOf("Error desconocido")
    }

    val showAnuncioCargado = remember {
        mutableStateOf(false)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    val isLoading = remember {
        mutableStateOf(true)
    }

    val showDialogErrorReviews = remember {
        mutableStateOf(false)
    }

    val showNotEditable = remember {
        mutableStateOf(false)
    }

    val resenyaViewModel = remember {
        ResenyaViewModel()
    }

    val eliminando = remember {
        mutableStateOf(false)
    }



    LaunchedEffect(key1 = "nothing") {
        resenyaViewModel.calcularPuntuacionAnuncio(StaticVariables.anuncioSeleccionado!!._id) { puntuacion ->
            if (puntuacion == null) {
                showError.value = true
            } else {
                StaticVariables.puntuacionAnuncioSelect = puntuacion
                isLoading.value = false
                showAnuncioCargado.value = true
            }
        }

    }

    loadingAnuncioDetalle(show = isLoading)
    loadAnuncioDetalleError(show = showError, showDetalle = showAnuncioCargado, isLoading = isLoading)
    anuncioCargado(show = showAnuncioCargado,  showDialogLoading = showDialogLoading, navController, showDialogErrorReviews, showNotEditable, eliminando)
    dialogErrorCargarReviews(show = showDialogErrorReviews)
    notEditableAnuncio(show = showNotEditable)
    dialogLoading(show = showDialogLoading)
    dialogEliminando(show = eliminando , showDialogError = showDialogError , anuncio = StaticVariables.anuncioSeleccionado, errorMsj, navController = navController)
    dialogError(show = showDialogError, msj = errorMsj)

}


@Composable
fun anuncioCargado(show: MutableState<Boolean>, showDialogLoading : MutableState<Boolean>, navController: NavController, showDialogErrorReviews : MutableState<Boolean>, showNotEditable : MutableState<Boolean>, eliminando : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme {
            val resenyaViewModel = remember {
                ResenyaViewModel()
            }
            val scrollState = rememberScrollState(0)
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c))) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)) {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigate(Rutas.Main.route) {
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
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,
                        actions = {
                            IconButton(
                                onClick = { /*TODO*/ },
                                enabled = false,
                                content = {}) //Lo hacemos sin contenido, solo servirá para hacer que el titulo se quede centrado
                        }
                    )
                    Box(
                        Modifier
                            .fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 5.dp, horizontal = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                                text = StaticVariables.anuncioSeleccionado!!.categoria,
                                                fontSize = 23.sp,
                                                color = Color(0xFF202020),
                                                fontWeight = FontWeight.Bold,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Divider(thickness = 1.dp)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = StaticVariables.anuncioSeleccionado!!.nombre,
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
                                                    text = StaticVariables.anuncioSeleccionado!!.descripcion,
                                                    fontSize = 14.sp,
                                                    color = Color(0xFF202020),
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(10.dp))
                                            val precio = remember {
                                                if (StaticVariables.anuncioSeleccionado!!.precioPorHora) "${StaticVariables.anuncioSeleccionado!!.precio}€ Hora" else "${StaticVariables.anuncioSeleccionado!!.precio}"
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
                                                    text = StaticVariables.usuario!!.email,
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
                                                text = StaticVariables.anuncioSeleccionado!!.numVecesVisto.toString(),
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
                                                text = StaticVariables.puntuacionAnuncioSelect.toString()
                                                    .toString(), fontSize = 13.sp, color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(
                                onClick = {
                                    showDialogLoading.value = true
                                    resenyaViewModel.obtenerResenyasAnuncio(StaticVariables.anuncioSeleccionado!!._id) { reviews ->
                                        if (reviews == null) {
                                            showDialogErrorReviews.value = true
                                        } else if (reviews.isEmpty()) {
                                            navController.navigate(Rutas.EditarAnuncio.route)
                                        } else {
                                            showNotEditable.value = true
                                        }
                                        showDialogLoading.value = false
                                    }
                                },
                                Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFDF9234)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Editar",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(0.dp, 7.dp),
                                        fontFamily = FontFamily(Font(R.font.comforta))
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Editar",
                                        tint = Color(0xfffcffff)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(
                                onClick = {
                                          navController.navigate(Rutas.AnuncioReviews.route)
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
                                        text = "Reseñas",
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
                                        tint = Color(0xfffcffff)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(
                                onClick = {
                                          eliminando.value = true
                                },
                                Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFA53535)
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
                                        contentDescription = "Eliminar",
                                        tint = Color(0xfffcffff)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

    @Composable
    fun loadingAnuncioDetalle(show: MutableState<Boolean>) {
        if (show.value) {
            OnecTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xff3b3d4c))
                ) {
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
                            text = "Cargando...",
                            fontSize = 16.sp,
                            color = Color(0xfffcffff),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun loadAnuncioDetalleError(
        show: MutableState<Boolean>,
        showDetalle: MutableState<Boolean>,
        isLoading: MutableState<Boolean>
    ) {
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
                    Text(
                        text = "Error al cargar el Anuncio",
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
                        text = "Ha ocurrido un error\nal cargar el anuncio\nreinténtelo más tarde.",
                        fontSize = 16.sp,
                        color = Color(0xfffcffff),
                        textAlign = TextAlign.Center
                    )
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

    @Composable
    fun dialogErrorCargarReviews(show: MutableState<Boolean>) {
        if (show.value) {
            OnecTheme() {
                Dialog(onDismissRequest = {}) {
                    Surface(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(1f)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                        shape = RoundedCornerShape(7.dp),
                        color = Color(0xff3b3d4c)
                    ) {
                        Column() {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Error",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                color = Color(0xfffcffff)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                painter = painterResource(id = R.drawable.errorlog),
                                contentDescription = "ErrorLog",
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Error al cargar el anuncio\nInténtelo más tarde",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 19.sp,
                                color = Color(0xfffcffff)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(
                                onClick = { show.value = false },
                                Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(0.dp, 0.dp, 7.dp, 7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF266E86
                                    )
                                )
                            ) {
                                Text(
                                    text = "Aceptar",
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
    }

    @Composable
    fun notEditableAnuncio(show: MutableState<Boolean>) {
        if (show.value) {
            OnecTheme() {
                Dialog(onDismissRequest = {}) {
                    Surface(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(1f)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                        shape = RoundedCornerShape(7.dp),
                        color = Color(0xff3b3d4c)
                    ) {
                        Column() {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "No se puede editar",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                color = Color(0xfffcffff)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                painter = painterResource(id = R.drawable.info),
                                contentDescription = "ErrorLog",
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "El anuncio no se puede editar\nya que ha sido reseñado por otros usuarios.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 19.sp,
                                color = Color(0xfffcffff)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(
                                onClick = { show.value = false },
                                Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(0.dp, 0.dp, 7.dp, 7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF266E86
                                    )
                                )
                            ) {
                                Text(
                                    text = "Aceptar",
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
    }
@Composable
fun dialogEliminando(show: MutableState<Boolean>, showDialogError : MutableState<Boolean>, anuncio : AnuncioModel?, errorMsj : MutableState<String>, navController: NavController) {
    if (show.value) {
        OnecTheme() {

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
                                                StaticVariables.anunciosUsuario.remove(anuncio)
                                                StaticVariables.anuncioSeleccionado = null
                                                navController.navigate(Rutas.Main.route) { popUpTo(0) }
                                            }else {
                                                showDialogError.value = true
                                                show.value = false
                                                errorMsj.value = "Error al eliminar el anuncio"
                                            }
                                        }
                                    }else {
                                        showDialogError.value = true
                                        show.value = false
                                        errorMsj.value = "Error al eliminar el anuncio"
                                    }
                                }
                            }else {
                                showDialogError.value = true
                                show.value = false
                                errorMsj.value = "Error al eliminar el anuncio"
                            }
                        }
                    } else {
                        showDialogError.value = true
                        show.value = false
                        errorMsj.value = "Error al eliminar el anuncio"
                    }
                }
            }
        }
    }
}
