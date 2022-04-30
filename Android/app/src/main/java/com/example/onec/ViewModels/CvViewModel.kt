
package com.example.onec.ViewModels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.Servicios.ApiServices
import com.example.onec.Soporte.StaticVariables
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import retrofit2.http.Part
import java.io.ByteArrayOutputStream
import java.io.File


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

    fun actualizarCV(id: String, cvPost: CvPost, onComplete: (did : Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val respuesta = api.actualizarCV(id,cvPost)
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

    fun subirImagen(imagen:Bitmap, onComplete: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val api = ApiServices.ApiServices.getInstance()
                val stream = ByteArrayOutputStream()
                imagen.compress(Bitmap.CompressFormat.PNG,90,stream)
                val imageByte = stream.toByteArray()
                val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),imageByte)
                val parte = createFormData("upload",StaticVariables.usuario!!._id, requestBody)
                val respuesta = api.postImage(parte)
                if (respuesta.isExecuted) {
                    onComplete(true)
                }else {
                    onComplete(false)
                    Log.d("respuesta",respuesta.toString())
                }
            }catch (e: Exception) {
                onComplete(false)
                Log.d("respuesta",e.message.toString())
            }

        }
    }
}