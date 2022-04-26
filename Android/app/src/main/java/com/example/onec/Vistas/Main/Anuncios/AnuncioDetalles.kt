package com.example.onec.Vistas.Main.Anuncios

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.onec.Models.AnuncioPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.ViewModels.ResenyaViewModel
import com.example.onec.Vistas.Login.dialogLoading
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

    val showAnuncioCargado = remember {
        mutableStateOf(true)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    val isLoading = remember {
        mutableStateOf(false)
    }

    val showDialogErrorReviews = remember {
        mutableStateOf(false)
    }

    val showNotEditable = remember {
        mutableStateOf(false)
    }
    loadingAnuncioDetalle(show = isLoading)
    loadAnuncioDetalleError(show = showError, showDetalle = showAnuncioCargado, isLoading = isLoading)
    anuncioCargado(show = showAnuncioCargado, showDialogError = showDialogError, showDialogLoading = showDialogLoading, navController, showDialogErrorReviews, showNotEditable)
    dialogErrorCargarReviews(show = showDialogErrorReviews)
    notEditableAnuncio(show = showNotEditable)
    dialogLoading(show = showDialogLoading)

}


@Composable
fun anuncioCargado(show: MutableState<Boolean>, showDialogError: MutableState<Boolean>, showDialogLoading : MutableState<Boolean>, navController: NavController, showDialogErrorReviews : MutableState<Boolean>, showNotEditable : MutableState<Boolean>){
    if (show.value) {
        OnecTheme {
            val resenyaViewModel = remember {
                ResenyaViewModel()
            }
            val scrollState = rememberScrollState(0)
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color(0xff333542))
                .padding(vertical = 5.dp, horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Detalles de anuncio", fontSize = 19.sp, textAlign = TextAlign.Center, color = Color(0xfffcffff))
                Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()) {
                        Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF080C1A), shape = RoundedCornerShape(5.dp,5.dp,0.dp,0.dp)) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)) {
                                Text(
                                    text = "Titulo de anuncio",
                                    fontSize = 18.sp,
                                    maxLines = 2,
                                    color = Color(0xFFFCFFFF),
                                )
                            }
                        }
                        Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF515366), shape = RoundedCornerShape(0.dp,0.dp,5.dp,5.dp)) {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(5.dp)) {
                                Text(
                                    text = StaticVariables.anunincioSeleccionado!!.nombre,
                                    fontSize = 16.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Color(0xfffcffff),
                                )
                            }
                        }
                    }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    Modifier
                        .fillMaxWidth()) {
                    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF080C1A), shape = RoundedCornerShape(5.dp,5.dp,0.dp,0.dp)) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {
                            Text(
                                text = "Categoría",
                                fontSize = 18.sp,
                                maxLines = 2,
                                color = Color(0xFFFCFFFF),
                            )
                        }
                    }
                    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF515366), shape = RoundedCornerShape(0.dp,0.dp,5.dp,5.dp)) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(5.dp)) {
                            Text(
                                text = StaticVariables.anunincioSeleccionado!!.categoria,
                                fontSize = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xfffcffff),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    Modifier
                        .fillMaxWidth()) {
                    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF080C1A), shape = RoundedCornerShape(5.dp,5.dp,0.dp,0.dp)) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {
                            Text(
                                text = "Descripción",
                                fontSize = 18.sp,
                                maxLines = 2,
                                color = Color(0xFFFCFFFF),
                            )
                        }
                    }
                    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF515366), shape = RoundedCornerShape(0.dp,0.dp,5.dp,5.dp)) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(5.dp)) {
                            Text(
                                text = StaticVariables.anunincioSeleccionado!!.descripcion,
                                fontSize = 16.sp,
                                maxLines = 8,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xfffcffff),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    Modifier
                        .fillMaxWidth()) {
                    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF080C1A), shape = RoundedCornerShape(5.dp,5.dp,0.dp,0.dp)) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {
                            Text(
                                text = "Precio",
                                fontSize = 18.sp,
                                maxLines = 2,
                                color = Color(0xFFFCFFFF),
                            )
                        }
                    }
                    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF515366), shape = RoundedCornerShape(0.dp,0.dp,5.dp,5.dp)) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(5.dp)) {
                            val precio = remember{
                                if (StaticVariables.anunincioSeleccionado!!.precioPorHora) "${StaticVariables.anunincioSeleccionado!!.precio}€ Hora" else "${StaticVariables.anunincioSeleccionado!!.precio}€"
                            }
                            Text(
                                text = precio,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xfffcffff),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
                        showDialogLoading.value = true
                        resenyaViewModel.obtenerResenyasAnuncio(StaticVariables.anunincioSeleccionado!!._id) { reviews ->
                            if (reviews == null) {
                                showDialogErrorReviews.value = true
                            }else if(reviews.isEmpty()){
                                navController.navigate(Rutas.EditarAnuncio.route)
                            }else {
                               showNotEditable.value = true
                            }
                            showDialogLoading.value = false
                        }
                    },
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            0xFF266E86
                        )
                    )
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "Editar",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar" , tint = Color(0xfffcffff) )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { },
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF209956)
                    )
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "Reseñas",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Reseñas" , tint = Color(0xfffcffff) )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = { },
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFA53535)
                    )
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "Eliminar",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar" , tint = Color(0xfffcffff) )
                    }
                }
            }
        }
    }
}

@Composable
fun loadingAnuncioDetalle(show : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c))
            ){
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier
                        .height(50.dp)
                        .width(50.dp), color = Color(0xfffcffff)
                    )
                    Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun loadAnuncioDetalleError(show: MutableState<Boolean>, showDetalle : MutableState<Boolean>, isLoading: MutableState<Boolean>) {
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
                Text(text = "Error al cargar el Anuncio", fontSize = 19.sp, color = Color(0xfffcffff))
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.errorlog),
                    contentDescription = "Error log"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Ha ocurrido un error\nal cargar el anuncio\nreinténtelo más tarde.", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
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

