package com.example.onec.ViewModels

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

    fun obtenerResenyasAnuncio(id: String, onComplete: (List<ResenyaModel>?) -> Unit) {
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
}