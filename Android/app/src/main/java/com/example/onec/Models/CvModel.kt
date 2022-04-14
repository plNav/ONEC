package com.example.onec.Models

data class CvModel(
    val _id : String,
    val id_usuario : String,
    val foto_url : String,
    val nombre : String,
    val telefono : String,
    val ubicacion: String,
    val correo : String,
    val experiencia : Int,
    val titulo: String,
    val especialidad : String?,
    val habilidades: MutableList<String>
)
