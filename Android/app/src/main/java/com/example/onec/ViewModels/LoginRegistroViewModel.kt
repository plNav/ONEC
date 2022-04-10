package com.example.onec.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.UsuarioPost
import com.example.onec.Servicios.ApiServices
import com.example.onec.Soporte.StaticVariables
import com.example.onec.Soporte.getSHA256
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginRegistroViewModel : ViewModel() {

    //Función para logear a un usuario con email y password
    fun logear(email: String, password: String, onSucces: (did: String) -> Unit) {
        viewModelScope.launch {
            val api = ApiServices.ApiServices.getInstance()
            try {
                val pass = getSHA256(password)
               val respuesta = api.validarUsuario(email = email, passw = pass)
                Log.e("Pass","Pass "+ password +"\nencrypt "+pass)
                if (respuesta.isSuccessful) {
                    StaticVariables.usuario = respuesta.body()!![0]
                    onSucces("good")
                }else {
                    onSucces("error")
                }
            }catch (e: Exception) {
                onSucces("unknown")
            }
        }
    }

    //Función para registrar a un usuario con email y password
    fun registrar(email:String,password: String,onSucces: (did: Boolean,cause :String) -> Unit) {
        viewModelScope.launch {
            val api = ApiServices.ApiServices.getInstance()
            try {
               val user = UsuarioPost(email = email, password = getSHA256(password))
                val respuesta = api.registrar(user)
                if (respuesta.isSuccessful) {
                    StaticVariables.usuario = respuesta.body()
                    onSucces(true,"good")
                }else {
                    onSucces(false,"error")
                }
            }catch (e: Exception) {
                onSucces(false,"catch")
            }
        }
    }

    //Función para comprobar que el email introducido es válido
    fun comprobarEmailExistente(email:String,onSucces: (did: Boolean,cause: String) -> Unit) {
        viewModelScope.launch {
            val api = ApiServices.ApiServices.getInstance()
            try {
                val respuesta = api.checkEmail(email = email)
                if (respuesta.isSuccessful) {
                    if (respuesta.body()!!) {
                        onSucces(true,"existe")
                    }else {
                        onSucces(false,"no existe")
                    }
                }else {
                    onSucces(false,"error")
                }
            }catch (e: Exception) {
                onSucces(false,"catch")
            }
        }
    }

    //Función para mandar el correo de reseteo de password
    fun mandarCorreoResetPassword(email: String,onSucces: (did: Boolean,cause : String) -> Unit) {
        viewModelScope.launch {
            val api = ApiServices.ApiServices.getInstance()
            try {
                val respuesta = api.resetPass(email = email)
                if (respuesta.isSuccessful) {
                    onSucces(true,"good")
                }else {
                    onSucces(false,"error")
                }
            }catch (e: Exception) {
                onSucces(false,"catch")
            }
        }
    }

    //Función para cerrar sesión
    fun logOut() {
        viewModelScope.launch {

        }
    }

}
