package com.example.onec.Vistas.Registro

import android.view.Surface
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Models.UsuarioPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ui.theme.OnecTheme

@Composable
fun Registro(
    navController: NavController,
    loginRegistroViewModel: LoginRegistroViewModel
){
    OnecTheme() {
        Reg(navController,loginRegistroViewModel)
    }
}

@Composable 
fun Reg(navController: NavController,loginRegistroViewModel: LoginRegistroViewModel){
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val repass = remember { mutableStateOf("") }
    val isDialogOpen = remember { mutableStateOf(false)}
    val dialogError = remember { mutableStateOf("")}
    var passwordVisibility by remember { mutableStateOf(false) }
    var rpasswordVisibility by remember { mutableStateOf(false) }
   Box(modifier = Modifier
       .fillMaxSize()
       .background(Color(0xff333542))
   , contentAlignment = Alignment.Center){
       Column(
           Modifier
               .fillMaxSize()
               .align(Alignment.Center)) {
           Spacer(modifier = Modifier.height(50.dp))
           Image(painter = painterResource(id = R.drawable.onec), contentDescription = "Logo", alignment = Alignment.Center, modifier = Modifier.fillMaxWidth())
           Spacer(modifier = Modifier.height(50.dp))
           Surface(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(50.dp,50.dp,0.dp,0.dp), color = Color(0xff434557)) {
               Column(
                   Modifier
                       .fillMaxSize()
                       .padding(horizontal = 20.dp)) {
                   Spacer(Modifier.height(30.dp))
                   Text(
                       text = "Crear una cuenta",
                       fontSize = 23.sp,
                       textAlign = TextAlign.Center,
                       color = Color(0xffbfc9c9),
                       modifier = Modifier.fillMaxWidth()
                   )
                   Spacer(Modifier.height(20.dp))
                   Spacer(modifier = Modifier.height(10.dp))

                   TextField(value = email.value,
                       singleLine = true,
                       textStyle = TextStyle(
                           fontFamily = FontFamily(Font(R.font.comforta))
                       ),
                       placeholder = { Text(text = "Email", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
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

                   Spacer(modifier = Modifier.height(20.dp))
                   TextField(value = password.value,
                       singleLine = true,
                       textStyle = TextStyle(
                           fontFamily = FontFamily(Font(R.font.comforta))
                       ),
                       placeholder = { Text(text = "Contraseña", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                       onValueChange = { it.also { password.value = it } },
                       shape = RoundedCornerShape(7.dp),
                       leadingIcon = {
                           Icon(
                               imageVector = Icons.Filled.Lock,
                               contentDescription = "Password",
                               tint = Color(0xFF999dba)
                           )
                       },
                       modifier = Modifier
                           .fillMaxWidth()
                           .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                       colors = TextFieldDefaults.textFieldColors(
                           backgroundColor = Color(0xff3b3d4c),
                           focusedIndicatorColor = Color.Transparent,
                           unfocusedIndicatorColor = Color.Transparent,
                           disabledIndicatorColor = Color.Transparent,
                           textColor = Color(0xFF999dba),
                           cursorColor = Color(0xFF999dba)
                       ), visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                       trailingIcon = {
                           val image = if (passwordVisibility)
                               R.drawable.ic_baseline_visibility_24
                           else R.drawable.ic_baseline_visibility_off_24

                           IconButton(onClick = {
                               passwordVisibility = !passwordVisibility
                           }) {
                               Icon(painter = painterResource(id = image), "", tint = Color(0xFF999dba))
                           }
                       }
                   )
                   Spacer(modifier = Modifier.height(20.dp))
                   TextField(value = repass.value,
                       singleLine = true,
                       textStyle = TextStyle(
                           fontFamily = FontFamily(Font(R.font.comforta))
                       ),
                       placeholder = { Text(text = "Repetir contraseña", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                       onValueChange = { it.also { repass.value = it } },
                       shape = RoundedCornerShape(7.dp),
                       leadingIcon = {
                           Icon(
                               imageVector = Icons.Filled.Lock,
                               contentDescription = "Password",
                               tint = Color(0xFF999dba)
                           )
                       },
                       modifier = Modifier
                           .fillMaxWidth()
                           .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                       colors = TextFieldDefaults.textFieldColors(
                           backgroundColor = Color(0xff3b3d4c),
                           focusedIndicatorColor = Color.Transparent,
                           unfocusedIndicatorColor = Color.Transparent,
                           disabledIndicatorColor = Color.Transparent,
                           textColor = Color(0xFF999dba),
                           cursorColor = Color(0xFF999dba)
                       ), visualTransformation = if (rpasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                       trailingIcon = {
                           val image = if (rpasswordVisibility)
                               R.drawable.ic_baseline_visibility_24
                           else R.drawable.ic_baseline_visibility_off_24

                           IconButton(onClick = {
                               rpasswordVisibility = !rpasswordVisibility
                           }) {
                               Icon(painter = painterResource(id = image), "", tint = Color(0xFF999dba))
                           }
                       }
                   )
                   Spacer(modifier = Modifier
                       .height(20.dp)
                       .fillMaxWidth())
                   Button(onClick = {
                                    if(email.value.isEmpty() || password.value.isEmpty() || repass.value.isEmpty()) {
                                        isDialogOpen.value = true
                                        dialogError.value = "Se deben de rellenar\ntodos los campos."
                                    }else {
                                      //Comprobamos cada campo y registramos al usuario
                                        //Comprobamos que el valor de email sea un email válido
                                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                                            isDialogOpen.value = true
                                            dialogError.value = "El campo de email\ncontiene un formato no válido."

                                        }else {
                                            //Comprobamos que el email no esté en uso
                                            loginRegistroViewModel.comprobarEmailExistente(email = email.value){ existe ->
                                                if (existe) {
                                                    //El correo ya existe, mostramos un error
                                                    isDialogOpen.value = true
                                                    dialogError.value = "El correo introducido\nya pertenece a un usuario."
                                                }else {
                                                    //Comprobamos la longitud de la contraseña
                                                    if (password.value.length < 6) {
                                                        //Mostramos un error, la longitud debe ser mayor o igual a 6 (Firebase)
                                                        isDialogOpen.value = true
                                                        dialogError.value = "La contraseña introducida\ndebe tener almenos 6 carácteres."
                                                    }else if(password.value.contains(' ')) {
                                                        isDialogOpen.value = true
                                                        dialogError.value = "La contraseña introducida\nno puede contener espacios."
                                                    }else {
                                                        //Todos los campos son válidos, registrar usuario
                                                        loginRegistroViewModel.registrar(email = email.value.trim(), password = password.value) { registrado ->
                                                            if (registrado) {
                                                                //El usuario se ha registrado, navegar hacia la vista de Main
                                                                navController.navigate(Rutas.TipoCuenta.route) {
                                                                    popUpTo(0)
                                                                }
                                                            }else {
                                                                //Ha ocurrido un error al registrar el usuario, mostrar el dialog.
                                                                isDialogOpen.value = true
                                                                dialogError.value = "Ha ocurrido un error\npor favor, intentelo más tarde."
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                   },Modifier.fillMaxWidth(), shape = RoundedCornerShape(7.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                       0xFF266E86
                   )
                   )) {
                       Text(text = "Registrarse", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp),fontFamily = FontFamily(Font(R.font.comforta)))
                   }
                   Spacer(modifier = Modifier.height(8.dp))
                   Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                       Text(text = "Ya tienes una cuenta? ", fontSize = 15.sp, color = Color(0xFF999dba))
                       Text(text= "Iniciar sesión", fontSize = 15.sp, color = Color(0xffbfc9c9), modifier = Modifier.clickable { navController.navigate(Rutas.Login.route) {popUpTo(0)} })
                   }
                   //Indicamos que el diálogo, solo será visible si el value de isDialoOpen es igual a true.
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
}