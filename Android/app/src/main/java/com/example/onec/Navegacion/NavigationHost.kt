package com.example.onec.Navegacion

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.Vistas.Login.Login
import com.example.onec.Vistas.Main.*
import com.example.onec.Vistas.Main.Anuncios.AnunciosEmpresario.*
import com.example.onec.Vistas.Main.Anuncios.anuncioDetalle
import com.example.onec.Vistas.Main.Anuncios.anuncioReviews
import com.example.onec.Vistas.Main.Anuncios.crearAnuncio
import com.example.onec.Vistas.Main.Anuncios.editarAnuncio
import com.example.onec.Vistas.Main.Ofertas.crearOferta
import com.example.onec.Vistas.Perfil.configuracion
import com.example.onec.Vistas.Registro.Registro
import com.example.onec.Vistas.ResetPassword.Forgot
import com.example.onec.Vistas.ResetPassword.SendedEmail
import com.example.onec.Vistas.TipoUsuario.tipoUsuario

@Composable
fun navHost(context: Context) {
    val loginRegistroViewModel = LoginRegistroViewModel()
    val navController = rememberNavController()

   NavHost(
       navController = navController,
       startDestination = Rutas.Login.route) {

       //Ruta Login
       composable(route = Rutas.Login.route) {
           Login(
               navController = navController,
               loginRegistroViewModel = loginRegistroViewModel,
               applicationContext = context
           )
       }

       //Ruta registro
       composable(route = Rutas.Registro.route) {
           Registro(
               navController = navController,
               loginRegistroViewModel = loginRegistroViewModel
           )
       }

       //Ruta contraseña olvidada
       composable(route = Rutas.Forgot.route) {
           Forgot(
               navController = navController,
               loginRegistroViewModel = loginRegistroViewModel
           )
       }

       //Ruta Email Enviado
       composable(route = Rutas.Sended.route, arguments = listOf(
           navArgument("email") {type = NavType.StringType}
       )) {
           backStackEntry ->
           val email: String? = backStackEntry?.arguments?.getString("email")
            SendedEmail(
                navController = navController,
                loginRegistroViewModel = loginRegistroViewModel,
                applicationContext = context,
                email = email
            )
       }
       
       //Ruta Main
       composable(route = Rutas.Main.route) {
           main(navController = navController, elemento = StaticVariables.fragmento)
       }

       //Ruta Crear Oferta
       composable(route = Rutas.CrearOferta.route) {
           crearOferta(navController = navController)
       }

       //Ruta Tipo de Cuenta
       composable(route = Rutas.TipoCuenta.route) {
           tipoUsuario(navController = navController)
       }

       //Ruta Configuración
       composable(route = Rutas.Configuracion.route) {
           configuracion(navController = navController)
       }

       //Ruta crear anuncio
       composable(route = Rutas.CrearAnuncio.route) {
           crearAnuncio(navController = navController)
       }
       
       //Ruta Anuncio Detalles
       composable(route = Rutas.AnuncioDetalles.route) {
           anuncioDetalle(navController = navController)
       }

       //Ruta Editar Anuncio
       composable(route = Rutas.EditarAnuncio.route) {
           editarAnuncio(navController = navController)
       }
       
       //Ruta Buscar Anuncio
       composable(route = Rutas.BuscarAnuncio.route) {
           buscarAnuncio(navController = navController)
       }
       
       //Ruta Detalles Anuncio Buscado
       composable(route = Rutas.AnuncioBuscadoDetalles.route) {
           anuncioBuscadoDetalles(navController = navController)
       }

       //Ruta Detalles Anuncio Favorito
       composable(route = Rutas.AnuncioFavDetalles.route) {
           anuncioFavoritoDetalles(navController = navController)
       }
       
       //Ruta Puntuar Anuncio
       composable(route = Rutas.AnuncioPuntuar.route) {
           anuncioPuntuar(navController = navController)
       }
       
       //Ruta revies Anuncios Favoritos
       composable(route = Rutas.AnuncioFavReviews.route) {
           anuncioFavReviews(navController = navController)
       }

       //Ruta reviews anuncio Buscado seleccionado
       composable(route = Rutas.AnunciosBuscadosReviews.route) {
           anunciosBuscadosReviews(navController = navController)
       }
       
       //Ruta reviews anuncio Subido por usuario
       composable(route = Rutas.AnuncioReviews.route) {
           anuncioReviews(navController = navController)
       }
   }
}