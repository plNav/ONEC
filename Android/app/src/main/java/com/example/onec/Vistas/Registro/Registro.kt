package com.example.onec.Vistas.Registro

import android.view.Surface
import android.widget.ImageButton
import androidx.compose.foundation.*
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
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.Vistas.Login.dialogLoading
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
    val scrollState = rememberScrollState(0)
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val repass = remember { mutableStateOf("") }
    val isDialogOpen = remember { mutableStateOf(false)}
    val dialogError = remember { mutableStateOf("")}
    val showDialogLoading = remember {
        mutableStateOf(false)
    }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rpasswordVisibility by remember { mutableStateOf(false) }
   Box(modifier = Modifier
       .fillMaxSize()
       .background(Color(0xff333542))
   , contentAlignment = Alignment.Center){
       Column(
           Modifier
               .fillMaxSize()
               .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Spacer(modifier = Modifier.fillMaxHeight(0.05f))
           Image(painter = painterResource(id = R.drawable.onec), contentDescription = "Logo", alignment = Alignment.Center, modifier = Modifier
               .fillMaxWidth(0.3f)
               .fillMaxHeight(0.2f))
           Spacer(modifier = Modifier.fillMaxHeight(0.05f))
           Surface(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(50.dp,50.dp,0.dp,0.dp), color = Color(0xff434557)) {
               Column(
                   Modifier
                       .fillMaxSize()
                       .padding(horizontal = 20.dp)
                       .verticalScroll(scrollState)
               ) {
                   Spacer(modifier = Modifier.height(20.dp))
                   Text(
                       text = "Crear una cuenta",
                       fontSize = 23.sp,
                       textAlign = TextAlign.Center,
                       color = Color(0xffbfc9c9),
                       modifier = Modifier.fillMaxWidth()
                   )
                   Spacer(modifier = Modifier.height(20.dp))
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

                   Spacer(modifier = Modifier.height(10.dp))
                   TextField(value = password.value,
                       singleLine = true,
                       textStyle = TextStyle(
                           fontFamily = FontFamily(Font(R.font.comforta))
                       ),
                       placeholder = { Text(text = "Contrase??a", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
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
                   Spacer(modifier = Modifier.height(10.dp))
                   TextField(value = repass.value,
                       singleLine = true,
                       textStyle = TextStyle(
                           fontFamily = FontFamily(Font(R.font.comforta))
                       ),
                       placeholder = { Text(text = "Repetir contrase??a", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
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
                   Spacer(modifier = Modifier.height(15.dp))
                   Button(onClick = {
                                    showDialogLoading.value = true
                                    StaticVariables.clean()
                                    if(email.value.isEmpty() || password.value.isEmpty() || repass.value.isEmpty()) {
                                        showDialogLoading.value = false
                                        isDialogOpen.value = true
                                        dialogError.value = "Se deben de rellenar\ntodos los campos."
                                    }else {
                                      //Comprobamos cada campo y registramos al usuario
                                        //Comprobamos que el valor de email sea un email v??lido
                                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches()) {
                                            showDialogLoading.value = false
                                            isDialogOpen.value = true
                                            dialogError.value = "El campo de email\ncontiene un formato no v??lido."

                                        }else {
                                            //Comprobamos que el email no est?? en uso
                                            loginRegistroViewModel.comprobarEmailExistente(email = email.value){ existe,cause ->
                                                if (existe && cause == "existe") {
                                                    //El correo ya existe, mostramos un error
                                                    showDialogLoading.value = false
                                                    isDialogOpen.value = true
                                                    dialogError.value = "El correo introducido\nya pertenece a un usuario."
                                                }else if (!existe && cause == "no existe"){
                                                        //Todos los campos son v??lidos, comprobar que las contrase??as coinciden
                                                            if (password.value.contains(" ") || repass.value.contains(" ")) {
                                                                showDialogLoading.value = false
                                                                isDialogOpen.value = true
                                                                dialogError.value = "La contrase??a\nno puede contener espacios."
                                                            }else if (password.value == repass.value) {
                                                                loginRegistroViewModel.registrar(email = email.value.trim(), password = password.value) { registrado, cause ->
                                                                    if (registrado && cause == "good") {
                                                                        showDialogLoading.value = false
                                                                //El usuario se ha registrado, navegar hacia la vista de Main
                                                                        navController.navigate(Rutas.TipoCuenta.route) {
                                                                            popUpTo(0)
                                                                        }
                                                                    } else {
                                                                        //Ha ocurrido un error al registrar el usuario, mostrar el dialog.
                                                                        showDialogLoading.value = false
                                                                        isDialogOpen.value = true
                                                                        dialogError.value = "Ha ocurrido un error\npor favor, intentelo m??s tarde."
                                                                    }
                                                                }
                                                            }else {
                                                                showDialogLoading.value = false
                                                                isDialogOpen.value = true
                                                                dialogError.value = "Las contrase??as introducidas\ndeben coincidir"
                                                            }
                                                }else {
                                                    showDialogLoading.value = false
                                                    isDialogOpen.value = true
                                                    dialogError.value = "Ha ocurrido un error\npor favor, intentelo m??s tarde."
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
                   Spacer(modifier = Modifier.height(10.dp))
                   Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                       Text(text = "Ya tienes una cuenta? ", fontSize = 15.sp, color = Color(0xFF999dba))
                       Text(text= "Iniciar sesi??n", fontSize = 15.sp, color = Color(0xffbfc9c9), modifier = Modifier.clickable { navController.navigate(Rutas.Login.route) {popUpTo(0)} })
                   }
                   //Indicamos que el di??logo, solo ser?? visible si el value de isDialoOpen es igual a true.
                   if(isDialogOpen.value) {
                       Dialog(onDismissRequest = { isDialogOpen.value = false }) {
                           Surface(
                               modifier = Modifier
                                   .padding(5.dp)
                                   .fillMaxWidth()
                                   .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                               shape = RoundedCornerShape(7.dp),
                               color = Color(0xff3b3d4c)
                           ) {
                               Column(verticalArrangement = Arrangement.SpaceBetween) {
                                   Spacer(modifier = Modifier.fillMaxHeight(0.01f))
                                   Text(
                                       text = "Error",
                                       modifier = Modifier.fillMaxWidth(),
                                       textAlign = TextAlign.Center,
                                       fontSize = 25.sp,
                                       color = Color(0xfffcffff)
                                   )
                                   Image(
                                       painter = painterResource(id = R.drawable.errorlog),
                                       contentDescription = "ErrorLog",
                                       alignment = Alignment.Center,
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .fillMaxHeight(0.4f)
                                   )
                                   Text(
                                       text = dialogError.value,
                                       textAlign = TextAlign.Center,
                                       modifier = Modifier.fillMaxWidth(),
                                       fontSize = 19.sp,
                                       color = Color(0xfffcffff)
                                   )
                                   Spacer(modifier = Modifier.fillMaxHeight(0.01f))
                                   Button(
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
    dialogLoading(show = showDialogLoading)
}