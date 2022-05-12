package com.example.onec.Navegacion

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.media.Image
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.onec.R

sealed class Rutas(
    val route:String
){
    object Login: Rutas(route = "Vistas.Login.Login")
    object Registro: Rutas(route = "Vistas.Registro.Registro")
    object Forgot: Rutas(route = "Vistas.ResetPassword.Forgot")
    object Sended: Rutas(route = "Vistas.ResetPassword.SendedEmail/{email}")
    object Main: Rutas(route = "main")
    object CrearOferta : Rutas(route = "Main.Ofertas.CrearOferta")
    object TipoCuenta : Rutas(route = "TipoCuenta")
    object Configuracion : Rutas(route = "Vistas.Main.Perfil.Configuracion")
    object CrearAnuncio : Rutas(route = "CrearAnuncio")
    object AnuncioDetalles : Rutas(route = "AnuncioDetalle")
    object EditarAnuncio : Rutas(route = "EditarAnuncio")
    object BuscarAnuncio : Rutas(route = "BuscarAnuncio")
    object AnuncioBuscadoDetalles : Rutas(route = "AnuncioBuscadoDetalle")
    object AnuncioFavDetalles : Rutas(route = "AnunciosFavDetalles")
    object AnuncioPuntuar : Rutas(route = "AnuncioPuntuar")
    object AnuncioFavReviews : Rutas(route = "AnuncioFavReviews")
    object AnunciosBuscadosReviews : Rutas(route  = "AnunciosBuscadosReviews")
    object AnuncioReviews: Rutas(route  = "AnuncioSelecViews")
    object OfertaDetalles: Rutas(route = "OfertaDetalels")
    object OfertaCandidatos : Rutas(route = "OfertaCandidato")
    object BuscarCandidatos : Rutas(route = "BuscarCandidatos")
    object DetallesCandidatoGuardado : Rutas(route = "DetallesCandidatosGuardado")
}