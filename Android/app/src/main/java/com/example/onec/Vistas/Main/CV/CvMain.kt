package com.example.onec.Vistas.Main.CV

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CvViewModel
import com.example.onec.R
import com.example.onec.ui.theme.OnecTheme

@Composable
fun cvMain(selected: MutableState<Boolean>) {

    val cvViewModel = remember {
        CvViewModel()
    }

    val resultState = remember {
        mutableStateOf("LOADING")
    }
    val loading = remember {
        mutableStateOf(true)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    if (selected.value) {
        //Mostramos el Loading, para indicar al usuario que se están cargando datos
        if (loading.value) {
            LoadCv()
            //Comprobar si el usuario tiene ya un CV
            cvViewModel.obtenerCvUsuarioActual() { cv: CvModel?, succes: Boolean? ->
                if (cv != null && succes == true) {
                    //El usuario ya ha creado un CV, por lo tanto se guardo
                        if (cv.correo != StaticVariables.usuario!!.email) {
                            StaticVariables.cv = cv
                                val cvPost = CvPost(cv.id_user,cv.foto_url,cv.nombre,cv.telefono,cv.ubicacion,StaticVariables.usuario!!.email,cv.experiencia,cv.titulo,cv.especialidad,cv.habilidades)
                                cvViewModel.actualizarCV(cv._id,cvPost) { did ->
                                   if (did) {
                                       StaticVariables!!.cv!!.correo = cvPost.correo
                                       resultState.value = "LOADED"
                                       loading.value = false
                                   }else {
                                       StaticVariables.cv = null
                                       resultState.value = "ERROR"
                                       loading.value = false
                                   }
                                }

                        }else {
                            StaticVariables.cv = cv
                            resultState.value = "LOADED"
                            loading.value = false
                        }

                } else if (cv == null && succes == true) {
                    //No tiene un CV, por lo tanto cargamos los composables de la creacion de un cv
                    resultState.value = "NOCV"
                    loading.value = false
                } else {
                    //Ha ocurrido un error, mostrar mensaje de error
                    resultState.value = "ERROR"
                    loading.value = false
                    showError.value = true
                }
            }
        }else {
            when (resultState.value) {
                "LOADED" -> muestraCv()
                "NOCV" -> creaCV(resultState)
                "ERROR" -> dialogError(showError,loading)
                else -> dialogError(showError = showError,loading)
            }

        }

    }else {
        if (resultState.value == "LOADING") {
            loading.value = true
        }
    }
}

@Composable
fun LoadCv() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column( horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
                color = Color(0xfffcffff)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff))
        }
            
    }
    
}

@Composable
fun dialogError(showError: MutableState<Boolean>, recarga : MutableState<Boolean>) {
    OnecTheme() {
    if (showError.value) {
            val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Error al cargar CV", fontSize = 19.sp, color = Color(0xfffcffff))
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.errorlog),
                    contentDescription = "Error log"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Ha ocurrido un error\nal cargar el CV del usuario\nreinténtelo más tarde.", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                              recarga.value = true
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
fun muestraCv() {
    OnecTheme {
        val scrollState = rememberScrollState(0)
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
            Spacer(modifier = Modifier.height(15.dp))
            Image(painter = painterResource(id = R.drawable.foto), contentDescription = "Foto CV")

        }
    }
}
