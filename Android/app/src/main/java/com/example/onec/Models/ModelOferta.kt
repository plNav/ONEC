package com.example.onec.Models

data class ModelOferta(
    val _id: String,
    val id_user: String,
    var nombre: String,
    var descripcion: String,
    var experiencia: Int,
    var titulo: String,
    var especialidad: String,
    var habilidades: MutableList<String>,
    var habilidadesLow: MutableList<String>,
    var habilidadesReq : Boolean
)
