package com.example.onec.Vistas.Main

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.example.onec.ViewModels.OfertaViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario.errorCargarAnunciosFav
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme

@Composable
fun ofertaEmpresario(selected: MutableState<Boolean>, navController: NavController) {
        if (selected.value) {
            OnecTheme {
                val viewModelOferta = remember {
                    OfertaViewModel()
                }
                val hasError = remember {
                    mutableStateOf(false)
                }


                val isLoading = remember {
                    mutableStateOf(true)
                }

                val mostrarLista = remember {
                    mutableStateOf(false)
                }

                val mostrarNoCreado = remember {
                    mutableStateOf(false)
                }

                val listaOfertas = remember {
                    mutableStateOf(mutableListOf<ModelOferta>().toMutableStateList())
                }
                Scaffold(topBar = {
                    TopAppBar(
                        backgroundColor = Color(0xFF1B1C29),
                        title = {
                            Text(
                                text = "Mis ofertas",
                                fontFamily = FontFamily(Font(R.font.comforta)),
                                color = Color(0xfffcffff),
                                fontWeight = FontWeight.W500
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                navController.navigate(Rutas.CrearOferta.route)
                            }) {
                                Icon(
                                    Icons.Filled.AddCircle,
                                    contentDescription = "crear oferta",
                                    tint = Color(0xfffcffff)
                                )
                            }
                        }, modifier = Modifier
                            .shadow(elevation = 0.dp)
                            .fillMaxHeight(0.08f)
                    )
                }) {
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
                    if (isLoading.value) {
                        viewModelOferta.obtenerOfertasUsuario(StaticVariables.usuario!!._id) { ofertas ->
                            if (ofertas == null) {
                                hasError.value = true
                            } else if (ofertas.isEmpty()) {
                                mostrarNoCreado.value = true
                            } else {
                                listaOfertas.value = ofertas.toMutableStateList()
                                mostrarLista.value = true
                            }
                            isLoading.value = false
                        }
                    } else {
                        mostrarItems(
                            show = mostrarLista,
                            lista = listaOfertas,
                            navController = navController
                        )
                        errorCargaOfertas(show = hasError, loading = isLoading)
                        mostrarNoEncontrado(show = mostrarNoCreado)

                    }
                }
            }
        }
}





@Composable
fun mostrarItems(show : MutableState<Boolean> , lista: MutableState<SnapshotStateList<ModelOferta>>, navController: NavController) {
    OnecTheme() {
        if (show.value) {
            val listaItemsEliminados = remember {
                mutableStateOf(mutableListOf<ModelOferta>().toMutableStateList())
            }
            val showLoadingDialog = remember {
                mutableStateOf(false)
            }

            val showErrDialog = remember {
                mutableStateOf(false)
            }

            val errMsj =   remember {
                mutableStateOf("Error desconocido")
            }

            val ofertaViewModel = remember {
                OfertaViewModel()
            }
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xff3b3d4c))
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    items(lista.value) { oferta ->
                        AnimatedVisibility(
                            visible = !listaItemsEliminados.value.contains(oferta),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 700))
                        ) {

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .clickable {
                                        StaticVariables.ofertaSeleccionada = oferta
                                        navController.navigate(Rutas.OfertaDetalles.route)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = 5.dp, horizontal = 3.dp)
                                        .background(Color.Transparent)
                                        .fillMaxWidth()
                                ) {
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color(0xFFE1E3EE),
                                        shape = RoundedCornerShape(7.dp)
                                    ) {
                                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                            Column(
                                                Modifier
                                                    .fillMaxWidth(0.9f)
                                                    .padding(5.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = oferta.nombre,
                                                    fontSize = 19.sp,
                                                    color = Color(0xFF202020),
                                                    fontWeight = FontWeight.Bold,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 1
                                                )
                                                Spacer(modifier = Modifier.height(3.dp))
                                                Divider(thickness = 1.dp, color = Color(0xFF404246))
                                                Spacer(modifier = Modifier.height(10.dp))
                                                Column(Modifier.fillMaxWidth()) {
                                                    Text(text = "Titulación", fontSize = 14.sp,  color = Color(
                                                        0xFF434C5E
                                                    ), fontWeight = FontWeight.SemiBold
                                                    )
                                                    Row(Modifier.fillMaxWidth()) {
                                                        Text(text = if (oferta.especialidad != null) oferta.especialidad else if (oferta.titulo != null) oferta.titulo else "No requiere", fontSize = 15.sp, color = Color(
                                                            0xFF6E81A5
                                                        )
                                                        , overflow = TextOverflow.Ellipsis, maxLines = 1)
                                                    }
                                                }
                                                Spacer(Modifier.height(5.dp))
                                                Column(Modifier.fillMaxWidth()) {
                                                    Text(text = "Experiencia", fontSize = 14.sp,   color = Color(
                                                        0xFF434C5E
                                                    ), fontWeight = FontWeight.SemiBold
                                                    )
                                                    Row(Modifier.fillMaxWidth()) {
                                                        Text(text = if (oferta.experiencia > 0) oferta.experiencia.toString() + " años." else "No requiere", fontSize = 15.sp,  color = Color(
                                                            0xFF6E81A5
                                                        )
                                                        )
                                                    }
                                                }
                                            }
                                            IconButton(onClick = {
                                                showLoadingDialog.value = true
                                                ofertaViewModel.eliminarOferta(oferta._id) { did ->
                                                    if (did) {
                                                        listaItemsEliminados.value.add(oferta)
                                                    }else {
                                                        showErrDialog.value = true
                                                        errMsj.value = "Error al eliminar oferta\npor favor inténtelo más tarde."
                                                    }
                                                    showLoadingDialog.value = false
                                                }

                                            }) {
                                                Icon(
                                                    imageVector = Icons.Filled.Delete,
                                                    contentDescription = "Borrar",
                                                    tint = Color(0xFF355181)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            dialogLoading(show = showLoadingDialog)
            dialogError(show =  showErrDialog, msj = errMsj)
        }
    }
}
    

@Composable
fun errorCargaOfertas(show : MutableState<Boolean>, loading: MutableState<Boolean>) {
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
                        text = "Error al cargar ofertas",
                        fontSize = 19.sp,
                        color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                    Image(
                        painter = painterResource(id = R.drawable.errorlog),
                        contentDescription = "Error log",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f)
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                    Text(
                        text = "Error al cargar ofertas\n por favor inténtelo más tarde.",
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
fun mostrarNoEncontrado(show : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Todavía no ha creado\nninguna oferta", fontSize = 19.sp, color = Color(0xfffcffff))
            }
        }
    }
}