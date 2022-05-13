package com.example.onec.Vistas.Perfil

import android.util.Log
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Models.UsuarioModel
import com.example.onec.Models.UsuarioPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.Soporte.getSHA256
import com.example.onec.ViewModels.LoginRegistroViewModel
import com.example.onec.ui.theme.OnecTheme
import java.lang.Exception

@Composable
fun configuracion(navController: NavController) {
    OnecTheme {

        /**
         * texto -> Contendrá el mensaje del diálogo, cuando se realice una operación de manera correcta
         * */
        val texto = remember {
            mutableStateOf("")
        }

        /**
         * titulo -> Contendrá el titulo del diálogo, cuando se realice una operación de manera correcta
         * */
        val titulo = remember {
            mutableStateOf("")
        }

        /**
         *  tipo -> Controla que se hará después de introducir la contraseña en el dialogPass, dependiendo del tipo
         * */
        val tipo = remember {
            mutableStateOf("")
        }

        /**
         * ShowLoading -> Controla la visualización del Composable loading
         * */
        val showLoading = remember {
            mutableStateOf(false)
        }

        /**
         * showDialogPass -> Controla la visualización del composable dialogPass
         * */
        val showDialogPass = remember {
            mutableStateOf(false)
        }

        /**
         *  showDialogError -> Controla la visualización del composable dialogError
         * */
        val showDialogError = remember {
            mutableStateOf(false)
        }

        /**
         * dialogErrMsj -> Contiene el mensaje de error del dialogError
         * */
        val dialogErrMsj = remember {
            mutableStateOf("Ha ocurrido un error\nal realizar la operación")
        }

        /**
         * email -> Contiene el email del usuario actual
         * */
        val email = remember {
            mutableStateOf(StaticVariables.usuario!!.email)
        }

        /**
         * readOnly -> Controla si el Email se puede editar
         * */
        val readOnly = remember {
            mutableStateOf(true)
        }

        /**
         * mostrarBtnsEditar -> Controla la visualización de los botones de guardar y cancelar
         * */
        val mostrarBtnsEditar = remember {
            mutableStateOf(false)
        }
        
        /**
         * mostrarPassChanged -> Controla la visualización del diálogo que nos dice que se ha actualizado bien la contraseña
         * */
        val mostrarPassChanged = remember {
            mutableStateOf(false)
        }

        /**
         * showDialogToChangePass -> Controla la visualización del composable dialogToChangePass
         * */
        val showDialogToChangePass = remember {
            mutableStateOf(false)
        }

        val loginRegistroViewModel = remember {
            LoginRegistroViewModel()
        }

        val scrollState = rememberScrollState(0)

        Scaffold(modifier = Modifier.fillMaxSize(),topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF1B1C29),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Rutas.Main.route) {
                            popUpTo(
                                0
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = Color(0xfffcffff)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Configuración",
                        fontSize = 19.sp,
                        color = Color(0xfffcffff),
                        fontFamily = FontFamily(
                            Font(R.font.comforta)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, actions = {
                    IconButton(onClick = { /*TODO*/ }, enabled = false) {

                    }
                }
            )
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xff3b3d4c))
                    .padding(horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(0.dp,20.dp,0.dp,0.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    TextField(
                        value = email.value,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(R.font.comforta))
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Email",
                                tint = Color(0xFF858585)
                            )
                        },
                        trailingIcon = {
                            if (readOnly.value) {
                                IconButton(onClick = { readOnly.value = false }) {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = "Editar",
                                        tint = Color(0xFF31778F)
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    readOnly.value = true
                                    email.value = StaticVariables.usuario!!.email
                                    mostrarBtnsEditar.value = false
                                }) {
                                    Icon(
                                        Icons.Filled.Clear,
                                        contentDescription = "Editar",
                                        tint = Color(0xFFC03B3B)
                                    )
                                }
                            }
                        }, label = {
                            Text(text = "Email", overflow = TextOverflow.Ellipsis)
                        },
                        readOnly = readOnly.value,
                        onValueChange = {
                            it.also { email.value = it }
                            if (!mostrarBtnsEditar.value) mostrarBtnsEditar.value = true
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFEEEEEE),
                            focusedIndicatorColor = Color(
                                0xFF266E86
                            ),
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = Color(
                                0xFF266E86
                            ),
                            cursorColor = Color(
                                0xFF266E86
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            showDialogPass.value = true
                            tipo.value = "pass"
                        },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFF2F9234)
                        )
                    ) {
                        Text(
                            text = "Cambiar contraseña",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 7.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                    }
                    if (mostrarBtnsEditar.value) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                if (email.value.isNullOrEmpty() || email.value.isNullOrBlank()) {
                                    dialogErrMsj.value = "El email no puede estar vacío"
                                    showDialogError.value = true
                                } else {
                                    showLoading.value = true
                                    loginRegistroViewModel.comprobarEmailExistente(email = email.value) { did, cause ->
                                        if (did && cause == "existe") {
                                            showLoading.value = false
                                            dialogErrMsj.value =
                                                "El email introducido\nya pertenece a un usuario"
                                            showDialogError.value = true
                                        } else if (!did && cause == "no existe") {
                                            showLoading.value = false
                                            showDialogPass.value = true
                                            tipo.value = "save"
                                        } else {
                                            showLoading.value = false
                                            dialogErrMsj.value =
                                                "Error al actualizar el correo\ninténtelo más tarde."
                                            showDialogError.value = true
                                        }
                                    }
                                }
                            },
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF266E86)
                            )
                        ) {
                            Text(
                                text = "Guardar Cambios",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(0.dp, 7.dp),
                                fontFamily = FontFamily(Font(R.font.comforta))
                            )
                        }
                    }
                }
            }
        }
        loading(loading = showLoading)
        dialogPass(show = showDialogPass, showLoading = showLoading, showDid = mostrarPassChanged, showError = showDialogError, errorMsj = dialogErrMsj, showDialogToChangePass = showDialogToChangePass, tipo, email, titulo, texto)
        dialogError(show = showDialogError, msj = dialogErrMsj)
        dialogChangedPass(show = mostrarPassChanged, titulo = titulo, texto = texto)
        dialogToChangePass(show = showDialogToChangePass, showLoading = showLoading, showError = showDialogError, showDid = mostrarPassChanged, errorMsj = dialogErrMsj, titulo, texto)
    }
    BackHandler(onBack = {
        navController.navigate(Rutas.Main.route) { popUpTo(0) }
    })
}

/**
 * Composable loading -> Se mostrará mientras se realicen operaciones en BD
 */
@Composable
fun loading(loading : MutableState<Boolean>) {
    OnecTheme() {
        if (loading.value) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color(0xfffcffff), modifier = Modifier
                        .height(30.dp)
                        .width(30.dp))
                    Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff))
                }
            }
        }
    }
}


/**
 * Composable dialogPass -> Se mostrará al darle click al botón de cambiar contraseña
 * */
@Composable
fun dialogPass(show : MutableState<Boolean>, showLoading : MutableState<Boolean>, showDid : MutableState<Boolean>, showError: MutableState<Boolean>, errorMsj : MutableState<String>, showDialogToChangePass : MutableState<Boolean>, tipo : MutableState<String>, email : MutableState<String>, titulo: MutableState<String>, texto: MutableState<String>) {

    /**
     * loginRegViewMod -> Contiene el view Model de login
     * */
    val loginRegViewMod = remember {
        LoginRegistroViewModel()
    }

    val pass = remember {
        mutableStateOf("")
    }

    val passwordVisibility = remember {
        mutableStateOf(false)
    }

    OnecTheme() {
        if (show.value) {
            Dialog(onDismissRequest = { show.value = false }) {
                Surface(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(1f)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                    shape = RoundedCornerShape(7.dp),
                    color = Color(0xff3b3d4c)
                ) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Verificación",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(value = pass.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = { Text(text = "Contraseña", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                            onValueChange = { it.also { pass.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "Password",
                                    tint = Color(0xFF999dba)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp))
                                .align(CenterHorizontally)
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xff3b3d4c),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF999dba)
                            ), visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image = if (passwordVisibility.value)
                                    R.drawable.ic_baseline_visibility_24
                                else R.drawable.ic_baseline_visibility_off_24

                                IconButton(onClick = {
                                    passwordVisibility.value = !passwordVisibility.value
                                }) {
                                    Icon(painter = painterResource(id = image), "", tint = Color(0xFF999dba))
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            //Le damos el valor de false para que se cierre el diálogo al darle click en el botón.
                            onClick = {
                                show.value = false
                                showLoading.value = true
                                loginRegViewMod.logear(StaticVariables.usuario!!.email,pass.value){
                                    when (it) {
                                        "good" -> {
                                            //showDialogToChangePass.value = true
                                            if (tipo.value == "pass") {
                                                showDialogToChangePass.value = true
                                                showLoading.value = false
                                                show.value = false
                                            }else {
                                                //Cambiar el correo
                                                    val usuario = UsuarioPost(email.value,StaticVariables!!.usuario!!.password)
                                                    loginRegViewMod.actualizarUsuario(StaticVariables!!.usuario!!._id, usuario) {did ->
                                                    if (did) {
                                                        StaticVariables!!.usuario!!.email = email.value
                                                        showLoading.value = false
                                                        show.value = false
                                                        titulo.value = "Correo actualizado"
                                                        texto.value = "El correo del usuario\nha sido actualizado."
                                                        showDid.value = true
                                                    }else {
                                                        showLoading.value = false
                                                        show.value = false
                                                        errorMsj.value = "Error al cambiar el correo,"
                                                        showError.value = true
                                                    }
                                                }
                                            }
                                            pass.value = ""
                                        }
                                        "error" -> {
                                            showLoading.value = false
                                            show.value = false
                                            errorMsj.value = "La contraseña introducida\nes incorrecta"
                                            showError.value = true
                                            pass.value = ""

                                        }
                                        else -> {
                                            showLoading.value = false
                                            show.value = false
                                            errorMsj.value = "Error en la verificación\nInténtelo más tarde"
                                            showError.value = true
                                            pass.value = ""
                                        }
                                    }
                                }
                                      },
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

/**
 * Composable dialogChangePass -> Contiene al diálogo para cambiar la contraseña
 * */
@Composable
fun dialogToChangePass(show: MutableState<Boolean>, showLoading: MutableState<Boolean>, showError: MutableState<Boolean>, showDid: MutableState<Boolean>, errorMsj: MutableState<String>, titulo: MutableState<String>, texto: MutableState<String>) {
    OnecTheme() {
        val loginRegViewMod = remember {
            LoginRegistroViewModel()
        }

        val pass = remember {
            mutableStateOf("")
        }
        val repass = remember {
            mutableStateOf("")
        }

        val passwordVisibility = remember {
            mutableStateOf(false)
        }

        val repassVisibility = remember {
            mutableStateOf(false)
        }

        if (show.value) {
            Dialog(onDismissRequest = {}) {
                Surface(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(1f)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                    shape = RoundedCornerShape(7.dp),
                    color = Color(0xff3b3d4c)
                ) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Cambio contraseña",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(value = pass.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = { Text(text = "Contraseña", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                            onValueChange = { it.also { pass.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "Password",
                                    tint = Color(0xFF999dba)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp))
                                .align(CenterHorizontally)
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xff3b3d4c),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF999dba)
                            ), visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image = if (passwordVisibility.value)
                                    R.drawable.ic_baseline_visibility_24
                                else R.drawable.ic_baseline_visibility_off_24

                                IconButton(onClick = {
                                    passwordVisibility.value = !passwordVisibility.value
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
                            placeholder = { Text(text = "Repetir", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
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
                                .fillMaxWidth(0.8f)
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp))
                                .align(CenterHorizontally)
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xff3b3d4c),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF999dba)
                            ), visualTransformation = if (repassVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image = if (repassVisibility.value)
                                    R.drawable.ic_baseline_visibility_24
                                else R.drawable.ic_baseline_visibility_off_24

                                IconButton(onClick = {
                                    repassVisibility.value = !repassVisibility.value
                                }) {
                                    Icon(painter = painterResource(id = image), "", tint = Color(0xFF999dba))
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(CenterHorizontally), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                //Le damos el valor de false para que se cierre el diálogo al darle click en el botón.
                                onClick = {
                                    if (pass.value.isNullOrEmpty() || pass.value.isNullOrBlank() || repass.value.isNullOrBlank() || repass.value.isNullOrEmpty()) {
                                        //show.value = false
                                        errorMsj.value = "Debe introducir todos los campos."
                                        showError.value = true
                                    }else if(pass.value != repass.value) {
                                        errorMsj.value = "Las contraseñas no coinciden."
                                        showError.value = true
                                    }else {
                                        showLoading.value = true
                                        val usuario = UsuarioPost(StaticVariables!!.usuario!!.email, password = getSHA256(pass.value))
                                        loginRegViewMod.actualizarUsuario(StaticVariables!!.usuario!!._id, usuarioPost = usuario) {  did ->
                                            if (did) {
                                                loginRegViewMod.logear(StaticVariables!!.usuario!!.email, getSHA256(pass.value)) {
                                                    showLoading.value = false
                                                    show.value = false
                                                    titulo.value = "Contraseña actualizada"
                                                    texto.value = "La contraseña del usuario\nha sido actualizada."
                                                    showDid.value = true
                                                    pass.value = ""
                                                    repass.value = ""
                                                }
                                            }else {
                                                showLoading.value = false
                                                show.value = false
                                                errorMsj.value = "Error al actualizar\nla contraseña"
                                                showError.value = true
                                                pass.value = ""
                                                repass.value = ""
                                            }
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(7.dp),
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
                            Button(
                                //Le damos el valor de false para que se cierre el diálogo al darle click en el botón.
                                onClick = {
                                    pass.value = ""
                                    repass.value = ""
                                    show.value = false
                                },
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFA83636)
                                )
                            ) {
                                Text(
                                    text = "Cancelar",
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
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

/**
 * Composable dialogError -> Se mostrará si ocurre un Error
 * */
@Composable
fun dialogError(show: MutableState<Boolean>, msj: MutableState<String>) {
    OnecTheme() {
        if (show.value) {
            Dialog(onDismissRequest = { show.value = false }) {
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
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = msj.value,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 19.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                            onClick = { show.value = false },
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


/**
 * Composable dialogChangedPass -> Se mostrará si la contraseña fué actualizada
 * */
@Composable
fun dialogChangedPass(show: MutableState<Boolean>, titulo : MutableState<String>, texto : MutableState<String>) {
  OnecTheme() {
      if (show.value) {
          Dialog(onDismissRequest = { show.value = false }) {
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
                          text = titulo.value,
                          modifier = Modifier.fillMaxWidth(),
                          textAlign = TextAlign.Center,
                          fontSize = 25.sp,
                          color = Color(0xfffcffff)
                      )
                      Spacer(modifier = Modifier.height(20.dp))
                      Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                          modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f) )
                      Text(
                          text = texto.value,
                          textAlign = TextAlign.Center,
                          modifier = Modifier.fillMaxWidth(),
                          fontSize = 19.sp,
                          color = Color(0xfffcffff)
                      )
                      Spacer(modifier = Modifier.height(15.dp))
                      Button(
                          //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                          onClick = { show.value = false },
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