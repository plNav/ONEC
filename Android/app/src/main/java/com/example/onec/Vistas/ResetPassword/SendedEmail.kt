package com.example.onec.Vistas.ResetPassword

import android.content.Context
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
fun SendedEmail(navController: NavController,loginRegistroViewModel: LoginRegistroViewModel,email: String?,applicationContext: Context){
    OnecTheme {
        sended(navController,loginRegistroViewModel,email,applicationContext)
    }
}

@Composable
fun sended(navController: NavController,loginRegistroViewModel: LoginRegistroViewModel,email: String?,applicationContext: Context){
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
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(text = "Email enviado!",
                Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color(0xFFfcffff), fontSize = 25.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Revise su correo electrónico\npara restablecer su contraseña",
                Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color(0xFF999dba), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(40.dp))
            Image(painter = painterResource(id = R.drawable.revemail), contentDescription = "SendEmail", alignment = Alignment.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = { navController.navigate(Rutas.Login.route) { popUpTo(0) } },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(elevation = 20.dp, shape = RoundedCornerShape(7.dp)), shape = RoundedCornerShape(7.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                    0xFF266E86
                )
                )) {
                Text(text = "Acceder", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp), fontFamily = FontFamily(
                    Font(R.font.comforta)
                )
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "¿No has recibido ningún correo? ", fontSize = 15.sp, color = Color(0xFF999dba))
                Text(text = "Reenviar", fontSize = 15.sp, color = Color(0xFFFCFFFF), modifier = Modifier.clickable {
                    if(email == null) {
                        //Mostrar un dialogo de error
                        isDialogOpen.value = true
                        dialogError.value = "Ha ocurrido un error\nal enviar el correo."
                    }else {
                       //Mandamos un correo
                        loginRegistroViewModel.mandarCorreoResetPassword(email = email) {enviado ->
                            if (enviado) {
                                //El correo se ha mandado, navegar al login
                                navController.navigate(Rutas.Login.route) {popUpTo(0)}
                            }else {
                                //El correo no se ha mandado, mostrar error
                                isDialogOpen.value = true
                                dialogError.value = "Error al mandar el correo."
                            }
                        }
                    }
                })
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
}
