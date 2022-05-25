package com.example.onec.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.AnuncioModel
import com.example.onec.Models.AnuncioPost
import com.example.onec.Models.AnunciosGuardadosModel
import com.example.onec.Servicios.ApiServices
import com.example.onec.Soporte.StaticVariables
import kotlinx.coroutines.launch

class AnuncioViewModel : ViewModel() {

    fun crearAnuncio(anuncio: AnuncioPost,onComplete : (anuncioModel : AnuncioModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.crearAnuncio(anuncio = anuncio)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            }catch (e: Exception){
                onComplete(null)
            }
        }
    }

    fun obtenerAnuncio(id: String,onComplete: (anuncioModel: AnuncioModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerAnuncio(id)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            }catch (e: Exception){
                onComplete(null)
            }
        }
    }

    fun obtenerAnunciosUsuario(id: String, onComplete: (anuncios: List<AnuncioModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerAnunciosUsuario(id)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(listOf())
                }
            }catch (e: Exception){
                onComplete(null)
            }
        }
    }

    fun actualizarAnuncio(id:String, anuncio: AnuncioPost, onComplete: (did : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.actualizarAnuncio(id, anuncio)
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

    fun eliminarAnuncio(id: String, onComplete: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.borrarAnuncio(id)
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


    fun buscarAnuncios(campo : String, onComplete: (anuncios : MutableList<AnuncioModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.buscarAnuncio(campo = campo)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            }catch (e: Exception) {
                Log.d("CATCH",e.message.toString())
                onComplete(null)
            }
        }
    }

}