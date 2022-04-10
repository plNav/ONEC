package com.example.onec.Vistas.Main.CV

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.onec.Models.CvModel
import com.example.onec.ViewModels.CvViewModel

@Composable
fun cvMain(selected: MutableState<Boolean>) {
    val modeloCv = remember {
        mutableStateOf(CvModel("","","","","","", ""))
    }
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
                    //El usuario ya ha creado un CV, por lo tanto se guarda
                    modeloCv.value = cv
                    resultState.value = "LOADED"
                    loading.value = false

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
                "NOCV" -> creaCV()
                "ERROR" -> dialogError(showError)
                else -> dialogError(showError = showError)
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
        CircularProgressIndicator(modifier = Modifier
            .height(50.dp)
            .width(50.dp))
    }
    
}

@Composable
fun dialogError(showError: MutableState<Boolean>) {
    if (showError.value) {
        Text(text = "Error de conexión")
    }
}

@Composable
fun muestraCv() {
    Text(text = "El usuario tiene un CV creado")
}
