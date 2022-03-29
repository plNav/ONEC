package com.example.onec.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Soporte.FireAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginRegistroViewModel : ViewModel() {

    //Función para logear a un usuario con email y password
    fun logear(email: String, password: String, onSucces: (did: String) -> Unit) {
        viewModelScope.launch {
            FireAuth.auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        FireAuth.user = FireAuth.auth!!.currentUser!!
                        onSucces("good")
                    }else {
                        try {
                            throw task.exception!!
                        }catch (e: FirebaseAuthInvalidCredentialsException){
                            onSucces("pass")
                        }catch (ex: Exception) {
                            onSucces("unknown")
                        }
                    }
                }
        }
    }

    //Función para registrar a un usuario con email y password
    fun registrar(email:String,password: String,onSucces: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            FireAuth.auth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener() {task ->
                    if (task.isSuccessful) {
                       FireAuth.user = FireAuth.auth!!.currentUser!!
                        onSucces(true)
                    }else {
                        onSucces(false)
                    }
                }
        }
    }

    //Función para comprobar que el email introducido es válido
    fun comprobarEmailExistente(email:String,onSucces: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            FireAuth.auth!!.fetchSignInMethodsForEmail(email).addOnCompleteListener() { task ->
                if(task.result?.signInMethods?.size != 0) {
                    onSucces(true)
                }else {
                    onSucces(false)
                }
            }
        }
    }

    //Función para mandar el correo de reseteo de password
    fun mandarCorreoResetPassword(email: String,onSucces: (did: Boolean) -> Unit) {
        viewModelScope.launch {
            FireAuth.auth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        onSucces(true)
                    }else {
                       onSucces(false)
                    }
                }
        }
    }

    //Función para cerrar sesión
    fun logOut() {
        viewModelScope.launch {
            FireAuth.auth!!.signOut()
            FireAuth.user = null
        }
    }

    //Función para crear coleccion de Usuario
    fun crearColeccionUsuario() {

    }

}
