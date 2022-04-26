package com.example.onec.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.VisualizacionesModel
import com.example.onec.Models.VisualizacionesPost
import com.example.onec.Servicios.ApiServices
import kotlinx.coroutines.launch
import java.lang.Exception

class VisualizacionesViewModel : ViewModel() {

    fun crearVisualizacion(visualizacion : VisualizacionesPost, onComplete : (VisualizacionesModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.crearVisualizacion(visualizacion = visualizacion)
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

    fun obtenerVisualizacionesUsuarioAnuncio(id_usuario : String, id_anuncio : String, onComplete: (MutableList<VisualizacionesModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerVisualizacionesUsuarioAnuncio(id_usuario = id_usuario, id_anuncio = id_anuncio)
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
}