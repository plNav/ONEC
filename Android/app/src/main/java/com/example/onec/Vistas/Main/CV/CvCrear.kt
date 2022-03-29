package com.example.onec.Vistas.Main.CV

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberAsyncImagePainter
import com.example.onec.R
import com.example.onec.Soporte.FireAuth
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ui.theme.OnecTheme


@Composable
fun creaCV() {
    val valor = remember {
        mutableStateOf(StaticVariables.pasoRegistro)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    val errorMsj = remember {
        mutableStateOf("")
    }

    when (valor.value) {
        "1" -> creaCvDatos(valor = valor, error = showError, mensaje = errorMsj)
        "2" -> creaCvTitulos()
        "3" -> creaCvHabilidades()
    }
    if (showError.value) {
        dialogoError(showDialog = showError, error = errorMsj)
    }
}


@Composable
fun creaCvDatos(valor : MutableState<String>,error: MutableState<Boolean>, mensaje : MutableState<String>) {
    OnecTheme {
        val scrollState = rememberScrollState(0)

        var selectedImage = remember { mutableStateOf<Uri?>(StaticVariables.imageUri) }

        val nombre = remember {
            mutableStateOf("")
        }
        val apellidos = remember {
            mutableStateOf("")
        }

        val telefono = remember {
            mutableStateOf("")
        }

        val ubicacion = remember {
            mutableStateOf("")
        }

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                selectedImage.value = uri
                StaticVariables.imageUri = uri
                StaticVariables.fragmento = 4
            }

        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp)) {
                    Column(modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxSize()
                        .verticalScroll(scrollState, enabled = true), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
                        Text(text = "Seleccione una imagen", fontSize = 19.sp, color = Color(0xffbfc9c9))
                        Spacer(modifier = Modifier.height(10.dp))
                        if (selectedImage.value == null) {
                            Image(
                                painter = painterResource(id = R.drawable.foto),
                                contentDescription = "Foto",
                                modifier = Modifier
                                    .clickable {
                                        launcher.launch("image/*")
                                    }
                                    .fillMaxHeight(0.3f)
                                    .fillMaxWidth(0.3f)
                            )
                        }else {
                            Image(
                                painter = rememberAsyncImagePainter(model = selectedImage.value),
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier
                                    .clickable {
                                        launcher.launch("image/*")
                                    }
                                    .fillMaxHeight(0.3f)
                                    .fillMaxWidth(0.3f)
                                    .clip(CircleShape), contentScale = ContentScale.Crop
                            )
                        }

                        //Mostramos el resto de la vista de crear CV
                        //Nombre
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(value = nombre.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = { Text(text = "Nombre", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                            onValueChange = { it.also { nombre.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Nombre",
                                    tint = Color(0xFF999dba)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFF353644),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF999dba)
                            )
                        )
                        //Telefono
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(value = telefono.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = { Text(text = "Teléfono", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                            onValueChange = { it.also { telefono.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Phone,
                                    contentDescription = "Teléfono",
                                    tint = Color(0xFF999dba)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFF353644),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF999dba)
                            )
                        )
                        //Ubicación
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(value = ubicacion.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = { Text(text = "Ubicación", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
                            onValueChange = { it.also { ubicacion.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "Ubicación",
                                    tint = Color(0xFF999dba)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFF353644),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color(0xFF999dba),
                                cursorColor = Color(0xFF999dba)
                            )
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = {
                                if(nombre.value.isNotBlank() && nombre.value.isNotEmpty() && telefono.value.isNotBlank() && telefono.value.isNotEmpty() && ubicacion.value.isNotBlank() && ubicacion.value.isNotEmpty()) {
                                    StaticVariables.pasoRegistro = "2"
                                    StaticVariables.nombreCv = nombre.value
                                    StaticVariables.telefono = telefono.value
                                    StaticVariables.ubicacion = ubicacion.value
                                    valor.value = "2"
                                }else {
                                    mensaje.value = "Se deben introducir\ntodos los campos."
                                    error.value = true
                                }
                                      },Modifier.fillMaxWidth(0.7f), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                            0xFF266E86
                        )
                        )) {
                            Text(text = "Siguiente", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp),fontFamily = FontFamily(Font(R.font.comforta)))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun creaCvTitulos() {
    OnecTheme {
        val scrollState = rememberScrollState(0)
      Box(
          Modifier
              .fillMaxSize()
              .fillMaxWidth()
      ) {
          Box(
              Modifier
                  .fillMaxSize()
                  .padding(vertical = 5.dp)) {
              Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                  Spacer(modifier = Modifier.height(15.dp))
                  Text(text = "Introduce tus estudios", fontSize = 19.sp, color = Color(0xffbfc9c9))
                  Spacer(modifier = Modifier.height(30.dp))
              }
          }
      }
    }
}

@Composable
fun creaCvHabilidades() {
    OnecTheme() {

    }
}

@Composable
fun dialogoError(showDialog : MutableState<Boolean>,error : MutableState<String>) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
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
                    text = error.value,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 19.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                    onClick = { showDialog.value = false },
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
