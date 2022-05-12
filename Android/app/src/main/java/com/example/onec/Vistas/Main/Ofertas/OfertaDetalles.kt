package com.example.onec.Vistas.Main.Ofertas

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CandidatosOfertasViewModel
import com.example.onec.ViewModels.OfertaViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import java.util.*

@Composable
fun ofertaDetalles(navController: NavController) {
    OnecTheme {
        val scrollState = rememberScrollState()

        val selector: (String) -> Int = { str -> str.length }

        val candidatosOfertasViewModel = remember {
            CandidatosOfertasViewModel()
        }

        val ofertasViewModel = remember {
            OfertaViewModel()
        }

        val showDialogLoading = remember {
            mutableStateOf(false)
        }

        val showErr = remember {
            mutableStateOf(false)
        }

        val errMsj = remember {
            mutableStateOf("Error desconocido")
        }

        val showDid = remember {
            mutableStateOf(false)
        }



        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xff3b3d4c))
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                            text = "Detalles Oferta",
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
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 5.dp, horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(), backgroundColor = Color(
                                0xFFE1E3EE
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = StaticVariables.ofertaSeleccionada!!.nombre,
                                    fontSize = 19.sp,
                                    color = Color(0xFF202020),
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Divider(thickness = 1.dp, color = Color(0xFF404246))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Descripción", fontSize = 14.sp,  color = Color(
                                    0xFF434C5E
                                ), fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(text = StaticVariables.ofertaSeleccionada!!.descripcion, fontSize = 15.sp, color = Color(
                                    0xFF6E81A5
                                )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Titulación", fontSize = 14.sp,  color = Color(
                                    0xFF434C5E
                                ), fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(text = if (StaticVariables.ofertaSeleccionada!!.especialidad != null) StaticVariables.ofertaSeleccionada!!.especialidad else if (StaticVariables.ofertaSeleccionada!!.titulo != null) StaticVariables.ofertaSeleccionada!!.titulo else "No requiere", fontSize = 15.sp, color = Color(
                                    0xFF6E81A5
                                )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Experiencia", fontSize = 14.sp,  color = Color(
                                    0xFF434C5E
                                ), fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(text = if (StaticVariables.ofertaSeleccionada!!.experiencia > 0) StaticVariables.ofertaSeleccionada!!.experiencia.toString() + " años." else "No requiere", fontSize = 15.sp, color = Color(
                                    0xFF6E81A5
                                )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Habilidades", fontSize = 14.sp,  color = Color(
                                    0xFF434C5E
                                ), fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                        FlowRow(
                                            modifier = Modifier
                                                .background(Color.Transparent),
                                            mainAxisAlignment = MainAxisAlignment.Start,
                                            mainAxisSize = SizeMode.Expand,
                                            crossAxisSpacing = 12.dp,
                                            mainAxisSpacing = 8.dp
                                        ) {
                                            if (!StaticVariables.ofertaSeleccionada!!.habilidades.isEmpty()) {
                                                StaticVariables.ofertaSeleccionada!!.habilidades!!.sortBy(
                                                    selector
                                                )//Con esta línea Ordenamos por longitud de carácteres para que se ordene automáticamente "selector" -> Está definido arriba, es lo que le dice como debe hacer el sort .
                                                StaticVariables.ofertaSeleccionada!!.habilidades.forEach { habilidad ->
                                                    Row(
                                                        modifier = Modifier
                                                            .background(
                                                                color = Color(0xFF4D6D86),
                                                                shape = RoundedCornerShape(4.dp)
                                                            )
                                                            .padding(2.dp),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = "# $habilidad",
                                                            color = Color.White,
                                                            overflow = TextOverflow.Ellipsis,
                                                            maxLines = 1,
                                                            modifier = Modifier.padding(
                                                                10.dp,
                                                                5.dp,
                                                                10.dp,
                                                                5.dp
                                                            )
                                                        )

                                                    }

                                                }
                                            }else {
                                                Text(text = "Habilidades no requeridas", fontSize = 16.sp, color = Color(0xFF6E81A5))
                                            }
                                        }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                              navController.navigate(Rutas.OfertaCandidatos.route)
                    },
                    Modifier.fillMaxWidth(0.95f),
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
                            text = "Candidatos",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Editar",
                            tint = Color(0xfffcffff)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        showDialogLoading.value = true
                              candidatosOfertasViewModel.eliminarCandidatosOferta(StaticVariables.ofertaSeleccionada!!._id) { did ->
                                  if (did) {
                                      ofertasViewModel.eliminarOferta(StaticVariables.ofertaSeleccionada!!._id) { didit->
                                          if (didit) {
                                              showDid.value = true
                                          }else {
                                              showErr.value = true
                                              errMsj.value = "Error al eliminar oferta"
                                          }
                                      }
                                  }else {
                                      showErr.value = true
                                      errMsj.value = "Error al eliminar oferta"
                                  }
                              }
                    },
                    Modifier.fillMaxWidth(0.95f),
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
                            contentDescription = "Editar",
                            tint = Color(0xfffcffff)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
        dialogLoading(show = showDialogLoading)
        dialogError(show = showErr, msj = errMsj)
        ofertaEliminada(show = showDid , navController = navController)
    }
}

@Composable
fun ofertaEliminada(show : MutableState<Boolean>, navController: NavController) {
    if (show.value) {
        OnecTheme() {
            Dialog(onDismissRequest = { show.value = false }) {
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
                            text = "Oferta eliminada",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth() )
                        Text(
                            text = "La oferta\nha sido eliminada.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 19.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                            onClick = {
                                show.value = false
                                navController.navigate(Rutas.Main.route) { popUpTo(0) }
                            },
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