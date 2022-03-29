package com.example.onec.ViewModels

import androidx.lifecycle.ViewModel
import com.example.onec.Models.CvModel
import com.example.onec.Soporte.FireAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class CvViewModel : ViewModel() {

    fun obtenerCvUsuarioActual(onComplete : (cv : CvModel?, succes : Boolean?) -> Unit) {
        try {
            val db = FirebaseFirestore.getInstance()
            val ref = db.collection("cv")
                .whereEqualTo("id_usuario", FireAuth.auth!!.uid)
                .get()
                .addOnSuccessListener { documents ->
                   val result = documents.documents
                    if (result.size > 0) {
                        val resultDocument = result[0]
                        val cv = CvModel(
                            id_user = resultDocument.get("id_user").toString(),
                            foto_url = resultDocument.get("foto_url").toString(),
                            nombre = resultDocument.get("nombre").toString(),
                            telefono = resultDocument.get("telefono").toString(),
                            ubicacion = resultDocument.get("ubicacion").toString(),
                            correo = resultDocument.get("correo").toString(),
                            paso = resultDocument.get("paso").toString()
                        )
                        onComplete(cv,true)
                    }else {
                        onComplete(null,true)
                    }
                }.addOnFailureListener {
                    onComplete(null,false)
                }
        }catch (e: Exception) {
            onComplete(null,false)
        }
    }
}