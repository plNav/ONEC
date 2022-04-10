package com.example.onec.Vistas.ResetPassword

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ui.theme.OnecTheme

@Composable
fun Forgot(navController: NavController,loginRegistroViewModel: LoginRegistroViewModel){
    OnecTheme() {
        Forg(navController,loginRegistroViewModel)
    }
}



@Composable
fun Forg(navController: NavController,loginRegistroViewModel: LoginRegistroViewModel){
    val email = remember { mutableStateOf("") }
    val isDialogOpen = remember { mutableStateOf(false)}
    val dialogError = remember { mutableStateOf("")}
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color(0xff333542))) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.07f)
            .padding(horizontal = 8.dp)){
            IconButton(onClick = { navController.navigate(Rutas.Login.route) {popUpTo(0)} }, modifier = Modifier.fillMaxHeight()) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24), contentDescription = "Ir atrás", tint = Color(0xfffcffff))
            }
        }
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.fillMaxHeight(0.1f))
            Text(text = "Contraseña olvidada",Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color(0xFFfcffff), fontSize = 25.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Introduce tu correo electrónico\npara restablecer la contraseña",Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color(0xFF999dba), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(id = R.drawable.sendemail), contentDescription = "SendEmail", alignment = Alignment.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(30.dp))
            TextField(value = email.value,
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.comforta))
                ),
                placeholder = { Text(text = "Email", color = Color(0xFF999dba),fontFamily = FontFamily(
                    Font(R.font.comforta)
                )
                ) },
                onValueChange = { it.also { email.value = it } },
                shape = RoundedCornerShape(7.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email",
                        tint = Color(0xFF999dba)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xff3b3d4c),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Color(0xFF999dba),
                    cursorColor = Color(0xFF999dba)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "¿Has recordado la contraseña? ", fontSize = 15.sp, color = Color(0xFF999dba))
                Text(text = "Logear", fontSize = 15.sp, color = Color(0xfffcffff), modifier = Modifier.clickable {
                    navController.navigate(Rutas.Login.route) {popUpTo(0)}
                })
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                             //Comprobamos que el email no este vacio
                             if(email.value.isEmpty()){
                                 isDialogOpen.value = true
                                 dialogError.value = "Debe introducir un email."
                             }else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
                                 //Comprobamos que el email introducido existe
                                 loginRegistroViewModel.comprobarEmailExistente(email = email.value) { existe,cause ->
                                     if (existe && cause == "existe") {
                                         //Si existe el email mandamos el correo para cambiar la contraseña
                                         loginRegistroViewModel.mandarCorreoResetPassword(email = email.value) {enviado, cause ->
                                             if (enviado && cause == "good") {
                                                 //El correo se ha mandado navegamos a la vista de Sended pasando como parametro el email introducido
                                                 navController.navigate("Vistas.ResetPassword.SendedEmail/${email.value}") {popUpTo(Rutas.Sended.route)}
                                             }else {
                                                 //El correo no se ha mandado, mostramos el dialogo con un error.
                                                 isDialogOpen.value = true
                                                 dialogError.value = "Error al mandar el correo."
                                             }
                                         }
                                     }else {
                                         if (!existe && cause == "no existe") {
                                             //Si no existe mostramos el dialogo de error en pantalla.
                                             isDialogOpen.value = true
                                             dialogError.value =
                                                 "El correo introducido\nno pertenece a ningún usuario."
                                         }
                                         if (!existe && cause == "error" || !existe && cause == "catch") {
                                             isDialogOpen.value = true
                                             dialogError.value =
                                                 "Ha ocurrido un error\nintentelo más tarde."
                                         }
                                     }
                                 }
                             }else {
                                 isDialogOpen.value = true
                                 dialogError.value = "El correo introducido\nno es un correo válido"
                             }
                             },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(elevation = 20.dp, shape = RoundedCornerShape(7.dp)), shape = RoundedCornerShape(7.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                    0xFF266E86
                )
                )) {
                Text(text = "Enviar", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp), fontFamily = FontFamily(Font(R.font.comforta)))
            }
            if(isDialogOpen.value) {
                Dialog(onDismissRequest = { isDialogOpen.value = false }) {
                    Surface(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(1f)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                        shape = RoundedCornerShape(7.dp),
                        color = Color(0xff3b3d4c)
                    ) {
                        Column() {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Error",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                color = Color(0xfffcffff)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                painter = painterResource(id = R.drawable.errorlog),
                                contentDescription = "ErrorLog",
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = dialogError.value,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 19.sp,
                                color = Color(0xfffcffff)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(
                                //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                                onClick = { isDialogOpen.value = false },
                                Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(0.dp, 0.dp, 7.dp, 7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF266E86
                                    )
                                )
                            ) {
                                Text(
                                    text = "Aceptar",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(0.dp, 7.dp),
                                    fontFamily = FontFamily(
                                        Font(R.font.comforta)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}
