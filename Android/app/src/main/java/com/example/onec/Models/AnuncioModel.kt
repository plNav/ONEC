package com.example.onec.Models

data class AnuncioModel(
    val _id : String,
    val id_user : String,
    var categoria : String,
    var nombre: String,
    var descripcion : String,
    var precio : Float,
    var votos : MutableList<Int>
)

