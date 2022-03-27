package com.example.onec.Models

data class ModelUsuario(
    var nombre: String,
    var email: String,
    var password: String,
    var primeraSeleccion : Boolean,
    var cuentaEmpresa : Boolean
)