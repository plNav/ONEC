package com.example.onec.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.ModelOferta
import com.example.onec.Models.OfertaPost
import com.example.onec.Servicios.ApiServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.Api
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 *Creamos un isLoading para que se muestre un Progress indicator, mientras dure la función
 */
class OfertaViewModel : ViewModel() {

    var ofertaCreada : ModelOferta? = null

    fun crearOferta(oferta: OfertaPost, onComplete : (ModelOferta?) -> Unit) {
        viewModelScope.launch {
            try {
               val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.crearOferta(oferta)
                if (respuesta.isSuccessful) {
                    onComplete(respuesta.body())
                }else {
                    onComplete(null)
                }
            } catch (e: Exception) {
                Log.e("Error", "Error al crear la oferta")
                onComplete(null)
            }
        }
    }

    fun obtenerOfertasUsuario(id : String, onComplete: (MutableList<ModelOferta>?) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.obtenerOfertasUsuario(id)
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

    fun eliminarOferta(id: String, onComplete: (did : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.eliminarOferta(id)
                if (respuesta.isSuccessful) {
                    onComplete(true)
                }else {
                    onComplete(false)
                }
            }catch (e : Exception) {
                onComplete(false)
            }
        }
    }
}