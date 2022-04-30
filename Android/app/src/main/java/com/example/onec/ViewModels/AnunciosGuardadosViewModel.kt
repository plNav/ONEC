package com.example.onec.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.AnuncioModel
import com.example.onec.Models.AnuncioPost
import com.example.onec.Models.AnunciosGuardadosModel
import com.example.onec.Models.AnunciosGuardadosPost
import com.example.onec.Servicios.ApiServices
import com.example.onec.Soporte.StaticVariables
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class AnunciosGuardadosViewModel : ViewModel() {

    fun agregarAnuncioFavoritos(anuncio : AnunciosGuardadosPost, onComplete : (anuncio : AnunciosGuardadosModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.agregarAnuncioFavorito(anuncio = anuncio)
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

    fun obtenerAnunciosFavoritosUsuario(onComplete: (MutableList<AnunciosGuardadosModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerAnunciosFavUsuario(StaticVariables.usuario!!._id)
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

    fun borrarAnunciosGuardadosIdAnuncio(id : String, onComplete: (did: Boolean) -> Unit) {
       viewModelScope.launch {
           try {
               val api = ApiServices.ApiServices.getInstance()
               val respuesta = api.borrarAnunciosGuardadosIdAnuncio(id)
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

    fun borrarAnuncioFavoritos(id : String, onComplete: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.borrarAnuncioFavoritos(id)
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