package com.example.onec.Models

data class ResenyaModel(
    val _id : String,
    val id_user : String,
    val id_anuncio : String,
    var puntuacion : Int,
    var descripcion : String
)
