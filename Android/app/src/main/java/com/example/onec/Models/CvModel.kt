package com.example.onec.Models

data class CvModel(
    val _id : String,
    var id_user : String,
    var foto_url : String,
    var nombre : String,
    var telefono : String,
    var ubicacion: String,
    var correo : String,
    var experiencia : Int,
    var titulo: String,
    var especialidad : String?,
    var habilidades: MutableList<String>,
    var habilidadesLow: MutableList<String>
)
