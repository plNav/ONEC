package com.example.onec.Vistas.Main.Ofertas

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
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CandidatosOfertasViewModel
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Main.Anuncios.dialogEliminando
import com.example.onec.Vistas.Main.Anuncios.loadingAnuncio
import com.example.onec.Vistas.Main.Anuncios.loadingAnuncioDetalle
import com.example.onec.Vistas.Main.CV.dialogActualizado
import com.example.onec.Vistas.Main.CV.dialogError
import com.example.onec.Vistas.Main.CV.dialogoError
import com.example.onec.ui.theme.OnecTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun detallesCandidatoGuardado(navController: NavController) {
 OnecTheme() {

   val loading = remember {
       mutableStateOf(true)
   }

   val showCandidato = remember {
       mutableStateOf(false)
   }

   val emailCandidato = remember {
       mutableStateOf<String?>(null)
   }

   val showErrorCarga = remember {
       mutableStateOf(false)
   }

         Scaffold(modifier = Modifier.fillMaxSize(),topBar = {
             TopAppBar(
                 backgroundColor = Color(0xFF1B1C29),
                 navigationIcon = {
                     IconButton(onClick = {
                         navController.navigate(Rutas.OfertaCandidatos.route) {
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
                         text = "Detalles Candidato",
                         fontSize = 19.sp,
                         color = Color(0xfffcffff),
                         fontFamily = FontFamily(
                             Font(R.font.comforta)
                         ),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.fillMaxWidth()
                     )
                 }, actions = {
                     IconButton(onClick = { /*TODO*/ }, enabled = false) {

                     }
                 }
             )
         }) {
             Box(
                 modifier = Modifier
                     .background(Color(0xff3b3d4c))
                     .fillMaxSize()
                     .padding(0.dp, 5.dp, 0.dp, 0.dp)
             ) {
                 cargandoCandidato(show = loading, showErr = showErrorCarga, showCandidato = showCandidato, email = emailCandidato)
                 candidatoDetailLoaded(show = showCandidato, email = emailCandidato, navController = navController)
                 errorCargaCandidato(show = showErrorCarga, loading = loading)
             }
         }
    }
}

@Composable
fun candidatoDetailLoaded(show: MutableState<Boolean>, email: MutableState<String?>, navController: NavController) {
    OnecTheme {
        if (show.value) {

            val candidatosOfertasViewModel = remember {
                CandidatosOfertasViewModel()
            }

            val showLoading = remember {
                mutableStateOf(false)
            }

            val errMsj = remember {
                mutableStateOf("Error al eliminar candidato")
            }

            val showDialogErr = remember {
                mutableStateOf(false)
            }

            val showDid = remember {
                mutableStateOf(false)
            }

            val cv = remember {
                StaticVariables.candidatoGuardadoSeleccionadoCV
            }

            val scrollState = rememberScrollState(0)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c))
                    .verticalScroll(scrollState)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp)
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Datos Personales",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFCFFFF),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Correo",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C8AAF)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = email.value!!,
                            fontSize = 15.sp,
                            color = Color(0xFFEAEAEC)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Nombre",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C8AAF)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = cv!!.nombre,
                            fontSize = 15.sp,
                            color = Color(
                                0xFFEAEAEC
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Teléfono",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C8AAF)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = cv!!.telefono,
                            fontSize = 15.sp,
                            color = Color(
                                0xFFEAEAEC
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Ubicación",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C8AAF)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = cv!!.ubicacion,
                            fontSize = 15.sp,
                            color = Color(
                                0xFFEAEAEC
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(thickness = 1.dp, color = Color(0xFF5E6072))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Titulación",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFCFFFF),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Título",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C8AAF)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (cv!!.especialidad != null) cv.especialidad!! else if (cv!!.titulo != null) cv.titulo!! else "Sin estudios",
                            fontSize = 15.sp,
                            color = Color(0xFFEAEAEC)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(thickness = 1.dp, color = Color(0xFF5E6072))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Experiencia profesional",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFCFFFF),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Experiencia",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C8AAF)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (cv!!.experiencia > 0) "${cv.experiencia} años" else "Sin experiencia",
                            fontSize = 15.sp,
                            color = Color(0xFFEAEAEC)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(thickness = 1.dp, color = Color(0xFF5E6072))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Habilidades",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFCFFFF),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(5.dp))
                        FlowRow(
                            modifier = Modifier
                                .background(Color.Transparent),
                            mainAxisAlignment = MainAxisAlignment.Start,
                            mainAxisSize = SizeMode.Expand,
                            crossAxisSpacing = 12.dp,
                            mainAxisSpacing = 8.dp
                        ) {
                            cv!!.habilidades.forEach { habilidad ->
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xFF2D81A8),
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
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(thickness = 1.dp, color = Color(0xFF5E6072))
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            showLoading.value = true
                            candidatosOfertasViewModel.eliminarCandidatosOfertasId(StaticVariables.candidatoGuardadoSeleccionado!!._id) { did ->
                                if (did) {
                                    showDid.value = true
                                }else {
                                   showDialogErr.value = true
                                }
                                showLoading.value = false
                            }
                        },
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
                                text = "Eliminar candidato",
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
            dialogLoading(show = showLoading )
            dialogoError(showDialog = showDialogErr, error = errMsj)
            dialogoDid(show = showDid, navController = navController)
        }
    }
}

@Composable
fun cargandoCandidato(show: MutableState<Boolean>, showErr: MutableState<Boolean>, showCandidato : MutableState<Boolean>, email : MutableState<String?>) {
    OnecTheme() {
        if (show.value) {
            val loginRegistroViewModel = remember {
                LoginRegistroViewModel()
            }
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
            loginRegistroViewModel.obtenerUsuario(StaticVariables.candidatoGuardadoSeleccionado!!.id_usuario) { usuario ->
                if (usuario == null) {
                    showErr.value = true
                }else {
                    email.value = usuario.email
                    showCandidato.value = true
                }
                show.value = false
            }
        }
    }
}

@Composable
fun errorCargaCandidato(show: MutableState<Boolean>, loading : MutableState<Boolean>) {
    OnecTheme {
        if (show.value) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                    Text(
                        text = "Error al cargar candidato",
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
                        text = "Error al cargar candidato\n por favor inténtelo más tarde.",
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
fun dialogoDid(show: MutableState<Boolean>, navController: NavController) {
    OnecTheme() {
        if (show.value) {
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
                            text = "Candidato Eliminado",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f) )
                        Text(
                            text = "El candidato\nha sido eliminado.",
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
                                navController.navigate(Rutas.OfertaCandidatos.route) { popUpTo(0) }
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

@Preview
@Composable
fun pre(){
    OnecTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff3b3d4c))
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Datos Personales",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFCFFFF),
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Correo",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C8AAF)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "email.value!!",
                        fontSize = 15.sp,
                        color = Color(0xFFEAEAEC)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Nombre",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C8AAF)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "cv!!.nombre",
                        fontSize = 15.sp,
                        color = Color(
                            0xFFEAEAEC
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Teléfono",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C8AAF)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "cv!!.telefono",
                        fontSize = 15.sp,
                        color = Color(
                            0xFFEAEAEC
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Ubicación",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C8AAF)
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "cv!!.ubicacion",
                        fontSize = 15.sp,
                        color = Color(
                            0xFFEAEAEC
                        )
                    )
                }
            }
        }
    }
}

