package com.example.onec.Models

data class OfertaPost(
    val id_user: String,
    val nombre: String,
    val descripcion: String,
    val experiencia: Int,
    val titulo: String?,
    val especialidad: String?,
    val habilidades: MutableList<String>,
    val habilidadesLow: MutableList<String>,
    val habilidadesReq : Boolean?
)
