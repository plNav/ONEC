package com.example.onec.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onec.Models.ModelOferta
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 *Creamos un isLoading para que se muestre un Progress indicator, mientras dure la función
 */
class OfertaViewModel : ViewModel() {

    var ofertaCreada : ModelOferta? = null

    fun crearOferta(oferta: ModelOferta, onComplete : (ModelOferta?) -> Unit) {
        viewModelScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                db.collection("ofertas").document().set(oferta).addOnSuccessListener {
                    Log.e("Oferta_Creada", "La oferta ha sido creada correctamente")
                    ofertaCreada = oferta
                    onComplete(oferta)
                }.addOnFailureListener { fail ->
                    onComplete(null)
                    Log.e("Error_Failure", fail.message.toString())
                }
            } catch (e: Exception) {
                Log.e("Error", "Error al crear la oferta")
                onComplete(null)
            }
        }
    }
}