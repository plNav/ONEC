package com.example.onec.Models

data class AnuncioPost(
    val id_user : String,
    val categoria: String,
    val nombre: String,
    val descripcion : String,
    val precio : Float,
    val precioPorHora : Boolean,
    val numVecesVisto : Int,
    val numVotos : Int,
    val puntuacion : Int
)
