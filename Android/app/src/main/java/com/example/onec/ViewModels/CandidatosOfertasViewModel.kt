package com.example.onec.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.CandidatosOfertasModel
import com.example.onec.Models.CandidatosOfertasPost
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.Servicios.ApiServices
import com.google.protobuf.Api
import kotlinx.coroutines.launch

class CandidatosOfertasViewModel : ViewModel() {

    fun crearCandidatosOfertas(candidatosOfertasPost: CandidatosOfertasPost, onComplete : (candidatosOfertas : CandidatosOfertasModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.crearCandidatoOferta(candidatosOfertasPost)
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

    fun obtenerCandidatosOferta(id : String, onComplete: (candidatosOfertas: MutableList<CandidatosOfertasModel>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerCandidatosOferta(id)
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

    fun eliminarCandidatosOfertasId(id: String, onComplete: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.eliminarCandidatosOfertasID(id)
                if (respuesta.isSuccessful) {
                    onComplete(true)
                }else {
                   onComplete(false)
                    Log.d("why", respuesta.message())
                }
            }catch (e: Exception) {
                Log.d("why",e.message.toString())
                onComplete(false)
            }
        }
    }


    fun eliminarOfertasCandidato(id: String, onComplete : (did : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.eliminarOfertasCandidato(id)
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

    fun eliminarCandidatosOferta(id: String, onComplete : (did : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.eliminarCandidatosOferta(id)
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