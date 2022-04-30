package com.example.onec.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.ResenyaModel
import com.example.onec.Models.ResenyaPost
import com.example.onec.Servicios.ApiServices
import kotlinx.coroutines.launch
import java.lang.Exception

class ResenyaViewModel : ViewModel() {


    fun crearResenya(resenyaPost: ResenyaPost, onComplete : (did : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.crearResenya(resenyaPost)
                if (respuesta.isSuccessful) {
                    onComplete(true)
                }else {
                    onComplete(false)
                }
            }catch (e:Exception) {
                onComplete(false)
            }
        }
    }

    fun obtenerResenyasAnuncio(id: String, onComplete: (MutableList<ResenyaModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerResenyasAnuncio(id = id)
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

    fun obtenerResenyasUsuarioAnuncio(id_anuncio : String, id_usuario : String, onComplete: (List<ResenyaModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerResenyasAnuncioUsuario(id = id_anuncio, id_user = id_usuario)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            }catch (e : Exception) {
                onComplete(null)
            }
        }
    }

    fun calcularPuntuacionAnuncio(id: String , onComplete: (Float?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.calcularPuntuacionAnuncio(id)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            }catch (e: Exception) {
                onComplete(null)
                Log.d("Entra",e.message.toString())
            }
        }
    }

    fun eliminarReviewsAnuncio(id : String, onComplete: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.borrarResenyasAnuncio(id)
                if (respuesta.isSuccessful) {
                    onComplete(true)
                }else {
                    onComplete(false)
                }
            }catch (e: Exception) {
                onComplete(false)
            }
        }
    }
}