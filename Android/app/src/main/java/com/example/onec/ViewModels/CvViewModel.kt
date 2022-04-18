
package com.example.onec.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.Servicios.ApiServices
import com.example.onec.Soporte.StaticVariables
import com.google.protobuf.Api
import kotlinx.coroutines.launch
import java.lang.Exception

class CvViewModel : ViewModel() {

    fun obtenerCvEspecifico(id:String,onComplete : (cv : CvModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerCVEspecifico(id)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            } catch (e: Exception) {
                onComplete(null)
            }
        }
    }

    fun crearCv(cvPost: CvPost, onComplete: (cv: CvModel?) -> Unit) {
        viewModelScope.launch {
            try {
               val api = ApiServices.ApiServices.getInstance()
               val respuesta = api.crearCV(cvPost)
               if (respuesta.isSuccessful) {
                   onComplete(respuesta.body())
               }else {
                   onComplete(null)
               }
            }catch (e: Exception) {
                onComplete(null)
            }
        }
    }

    fun obtenerCvUsuarioActual(onComplete: (cv: CvModel?, succes : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerCvUsuario(StaticVariables.usuario!!._id)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body(),true)
                }else {
                    onComplete(null, true)
                }
            }catch (e: Exception) {
                onComplete(null, false)
            }
        }
    }
}