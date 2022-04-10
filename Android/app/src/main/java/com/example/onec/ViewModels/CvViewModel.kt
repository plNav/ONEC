package com.example.onec.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.CvModel
import com.example.onec.Servicios.ApiServices
import com.example.onec.Soporte.StaticVariables
import kotlinx.coroutines.launch
import java.lang.Exception

class CvViewModel : ViewModel() {

    fun obtenerCvUsuarioActual(onComplete : (cv : CvModel?, succes : Boolean?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerCVUsuario(StaticVariables.usuario!!._id)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body(),true)
                }else {
                    onComplete(null,true)
                }
            } catch (e: Exception) {
                onComplete(null, false)
            }
        }
    }
}