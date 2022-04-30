package com.example.onec.Vistas.Login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ui.theme.OnecTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*


@Composable
fun Login(
    navController: NavController,
    loginRegistroViewModel: LoginRegistroViewModel,
    applicationContext: Context
){
    OnecTheme() {
        Reg(navController,loginRegistroViewModel,applicationContext)
    }
}
@Composable
fun Reg(navController: NavController, loginRegistroViewModel: LoginRegistroViewModel,applicationContext: Context){
    val showDialogLoading = remember{
        mutableStateOf(false)
    }
    val stateScroll = rememberScrollState(0)
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val isDialogOpen = remember { mutableStateOf(false)}
    val dialogError = remember { mutableStateOf("")}
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xff333542))
        , contentAlignment = Alignment.Center){
        Column(
            Modifier
                .fillMaxSize()
                .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
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
                        .verticalScroll(state = stateScroll, enabled = true)
                        ,verticalArrangement = Arrangement.Top) {
                    //Spacer(Modifier.fillMaxHeight(0.5f))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 23.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xffbfc9c9),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(value = email.value,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(R.font.comforta))
                        ),
                        //
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
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "¿Has olvidado la contraseña?", color = Color(0xFF999dba), fontSize = 15.sp, modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(Rutas.Forgot.route) }, textAlign = TextAlign.Right)
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(onClick = {
                                    //Validar Mail y logear
                                    if(email.value.isEmpty() || password.value.isEmpty()) {
                                        isDialogOpen.value = true
                                        dialogError.value = "Faltan campos por completar."
                                    }else {
                                        //Comprobamos que el email introducido existe
                                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches()) {
                                                isDialogOpen.value = true
                                                dialogError.value = "El email introducido\nno es un email válido."
                                            }else {
                                                showDialogLoading.value = true
                                                loginRegistroViewModel.comprobarEmailExistente(email = email.value.trim()) { existe,cause ->
                                                    if (existe && cause == "existe") {
                                                        //Si el email existe, intentamos hacer un login, capturando cada error.
                                                        loginRegistroViewModel.logear(
                                                            email = email.value.trim(),
                                                            password = password.value.trim()
                                                        ) {
                                                            if (it == "good") {
                                                                navController.navigate(Rutas.TipoCuenta.route) { popUpTo(0) }
                                                            }else if (it == "error") {
                                                                isDialogOpen.value = true
                                                                dialogError.value = "La contraseña es incorrecta"
                                                            }else {
                                                                isDialogOpen.value = true
                                                                dialogError.value = "Error al iniciar sesión"
                                                            }
                                                            showDialogLoading.value = false
                                                        }
                                                    } else {
                                                        showDialogLoading.value = false
                                                        if (!existe && cause == "no existe") {
                                                            //Mostramos un error diciendo que el email introducido no pertenece a ningún usuario.
                                                            isDialogOpen.value = true
                                                            dialogError.value =
                                                                "El email introducido\nno pertenece a ningún usuario."
                                                        }

                                                        if (!existe && cause == "error") {
                                                            isDialogOpen.value = true
                                                            dialogError.value =
                                                                "Error al comprobar el email."
                                                        }

                                                        if (!existe && cause == "catch") {
                                                            isDialogOpen.value = true
                                                            dialogError.value =
                                                                "Error al comprobar el email."
                                                        }
                                                    }
                                                }
                                            }
                                    }

                    },Modifier.fillMaxWidth(), shape = RoundedCornerShape(7.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                        0xFF266E86
                    )
                    )) {
                        Text(text = "Acceder", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp), fontFamily = FontFamily(Font(R.font.comforta)))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        Text(text = "¿Usuario nuevo ? ", fontSize = 15.sp, color = Color(0xFF999dba))
                        Text(text= "Registrarse", fontSize = 15.sp, color = Color(0xffbfc9c9), modifier = Modifier.clickable { navController.navigate(Rutas.Registro.route) })
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

@Composable
fun dialogLoading(show : MutableState<Boolean>) {
    if (show.value) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color(0xfffcffff), modifier = Modifier
                    .height(30.dp)
                    .width(30.dp))
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff))
            }
        }
    }
}
