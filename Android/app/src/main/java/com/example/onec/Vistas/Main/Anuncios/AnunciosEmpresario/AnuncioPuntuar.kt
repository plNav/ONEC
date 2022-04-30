package com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario

import android.widget.Space
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.ResenyaPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.ResenyaViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Main.Anuncios.anuncioCreado
import com.example.onec.Vistas.Main.Anuncios.errorDialog
import com.example.onec.Vistas.Main.errorCarga
import com.example.onec.ui.theme.OnecTheme

@Composable
fun anuncioPuntuar(navController: NavController) {

    val resenyaViewModel = remember {
        ResenyaViewModel()
    }

    val loading = remember {
        mutableStateOf(false)
    }

    val showPublicado = remember {
        mutableStateOf(false)
    }

    val showDialogError = remember {
        mutableStateOf(false)
    }

    val errorMsj = remember {
        mutableStateOf("Error desconocido")
    }

    val imagen = remember {
        mutableStateOf(R.drawable.satisfaccion)
    }
    val valoracion = remember {
        mutableStateOf(0)
    }

    val descripcion = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val msj = remember {
        mutableStateOf("Dejanos tu opinión")
    }

    val intro = remember {
        mutableStateOf(true)
    }


    val scrollState = rememberScrollState(0)
    OnecTheme() {
        if (intro.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff333542))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = msj.value,
                        fontSize = 19.sp,
                        color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = imagen.value),
                        contentDescription = "Statisfacción",
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            valoracion.value = 1
                            msj.value = "Muy insatisfecho"
                            imagen.value = R.drawable.enfadado
                        }) {
                            Icon(
                                painter = painterResource(if (valoracion.value < 1) R.drawable.emptystar else R.drawable.fullstar),
                                contentDescription = "Star",
                                tint = if (valoracion.value < 1) Color(0xfffcffff) else Color(
                                    0xFFE7E425
                                ),
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        IconButton(onClick = {
                            valoracion.value = 2
                            msj.value = "Insatisfecho"
                            imagen.value = R.drawable.pocosatis
                        }) {
                            Icon(
                                painter = painterResource(if (valoracion.value < 2) R.drawable.emptystar else R.drawable.fullstar),
                                tint = if (valoracion.value < 2) Color(0xfffcffff) else Color(
                                    0xFFE7E425
                                ),
                                contentDescription = "Star",
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        IconButton(onClick = {
                            valoracion.value = 3
                            msj.value = "Satisfecho"
                            imagen.value = R.drawable.feliz
                        }) {
                            Icon(
                                painter = painterResource(if (valoracion.value < 3) R.drawable.emptystar else R.drawable.fullstar),
                                contentDescription = "Star",
                                tint = if (valoracion.value < 3) Color(0xfffcffff) else Color(
                                    0xFFE7E425
                                ),
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        IconButton(onClick = {
                            valoracion.value = 4
                            msj.value = "Muy satisfecho"
                            imagen.value = R.drawable.muyfeliz
                        }) {
                            Icon(
                                painter = painterResource(if (valoracion.value < 4) R.drawable.emptystar else R.drawable.fullstar),
                                contentDescription = "Star",
                                tint = if (valoracion.value < 4) Color(0xfffcffff) else Color(
                                    0xFFE7E425
                                ),
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        IconButton(onClick = {
                            valoracion.value = 5
                            msj.value = "Otro nivel"
                            imagen.value = R.drawable.pro
                        }) {
                            Icon(
                                painter = painterResource(if (valoracion.value < 5) R.drawable.emptystar else R.drawable.fullstar),
                                contentDescription = "Star",
                                tint = if (valoracion.value < 5) Color(0xfffcffff) else Color(
                                    0xFFE7E425
                                ),
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = descripcion.value,
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(R.font.comforta))
                        ),
                        label = {
                            Text(
                                text = "Detalles",
                                fontFamily = FontFamily(Font(R.font.comforta)),
                                color = Color(0xFFAAAECA)
                            )
                        },
                        onValueChange = {
                            if (it.text.length <= 165) descripcion.value = it
                        },
                        shape = RoundedCornerShape(7.dp),
                        maxLines = 9,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(180.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = RoundedCornerShape(7.dp)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFF626375),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = Color(0xFFFCFFFF),
                            cursorColor = Color(0xFFFCFFFF)
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                                  //Publicar Anuncio
                                  //Primero comprobamos que este todo introducido
                                  if (valoracion.value == 0) {
                                      showDialogError.value = true
                                      errorMsj.value = "Debe introducir una valoración"
                                  }else if (descripcion.value.text.isNullOrBlank() || descripcion.value.text.isNullOrEmpty()) {
                                      showDialogError.value = true
                                      errorMsj.value = "Debe introducir una descripción"
                                  }else {
                                      loading.value = true
                                      val rev = ResenyaPost(StaticVariables.usuario!!._id, StaticVariables.anuncioFavSelect!!._id,valoracion.value, descripcion.value.text)
                                      resenyaViewModel.crearResenya(rev) { did ->
                                          if (did) {
                                              intro.value = false
                                              showPublicado.value = true
                                          }else {
                                              showDialogError.value = true
                                              errorMsj.value = "Error al publicar reseña\ninténtelo más tarde."
                                          }
                                          loading.value = false
                                      }
                                  }
                        },
                        Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFF2983A2
                            )
                        )
                    ) {
                        Text(
                            text = "Publicar",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            navController.navigate(Rutas.AnuncioFavDetalles.route) { popUpTo(Rutas.Main.route) }
                        },
                        Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFAD3B3F)
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
        dialogLoading(show = loading)
        reviewPublicada(show = showPublicado, navController = navController)
        errorDialog(show = showDialogError, error = errorMsj)
    }
}


@Composable
fun reviewPublicada(show: MutableState<Boolean> , navController: NavController) {
    if (show.value) {
    OnecTheme {
        val scrollState = rememberScrollState(0)
        Box(modifier = Modifier.fillMaxSize().background(Color(0xff333542))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Valoración publicada",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.good),
                    contentDescription = "Good",
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Su valoración\n ha sido publicada.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 19.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        navController.navigate(Rutas.AnuncioFavDetalles.route) { popUpTo(Rutas.Main.route) }
                    },
                    Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(7.dp),
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
