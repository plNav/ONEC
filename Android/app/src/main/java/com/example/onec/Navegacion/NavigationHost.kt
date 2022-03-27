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
import com.example.onec.Vistas.Main.Ofertas.crearOferta
import com.example.onec.Vistas.Registro.Registro
import com.example.onec.Vistas.ResetPassword.Forgot
import com.example.onec.Vistas.ResetPassword.SendedEmail
import com.example.onec.Vistas.TipoUsuario.tipoUsuario

@Composable
fun navHost(context: Context,logged: Boolean) {
    val loginRegistroViewModel = LoginRegistroViewModel()
    val navController = rememberNavController()

   NavHost(
       navController = navController,
       startDestination = if(logged){Rutas.TipoCuenta.route}else{Rutas.Login.route}) {

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
       
       composable(route = Rutas.CrearOferta.route) {
           crearOferta(navController = navController)
       }

       composable(route = Rutas.TipoCuenta.route) {
           tipoUsuario(navController = navController)
       }
   }
}