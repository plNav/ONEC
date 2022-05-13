package com.example.onec.Models

data class CvPost(
    val id_user : String,
    val nombre : String,
    val telefono : String,
    val ubicacion: String,
    val correo : String,
    val experiencia : Int,
    val titulo: String,
    val especialidad : String?,
    val habilidades: MutableList<String>,
    val habilidadesLow: MutableList<String>
)
