package com.example.onec.Vistas.Main.CV

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ui.theme.OnecTheme
import org.w3c.dom.Text
import java.lang.Exception
import java.util.regex.Pattern


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
        "2" -> creaCvTitulos(valor = valor)
        "3" -> creaCvHabilidades(valor = valor)
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
                                if(nombre.value.isNotBlank() && nombre.value.isNotEmpty() && telefono.value.isNotBlank() && telefono.value.isNotEmpty() && ubicacion.value.isNotBlank() && ubicacion.value.isNotEmpty() /*&& selectedImage != null*/) {
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
fun creaCvTitulos(valor : MutableState<String>) {
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
                  .padding(vertical = 5.dp, horizontal = 20.dp)) {
              Column(
                  Modifier
                      .fillMaxSize()
                      .verticalScroll(scrollState)
                      , horizontalAlignment = Alignment.CenterHorizontally) {
                  Spacer(modifier = Modifier.height(20.dp))
                  Text(text = "Introduce tus estudios", fontSize = 19.sp, color = Color(0xffbfc9c9))
                  Spacer(modifier = Modifier.height(30.dp))
                  selectedDropDownMenu(valor)
              }
          }
      }
    }
}

@Composable
fun creaCvHabilidades(valor: MutableState<String>) {
    OnecTheme() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 5.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Añade tus habilidades", fontSize = 19.sp, color = Color(0xffbfc9c9))
        }
    }
}

@Composable
fun dialogoError(showDialog : MutableState<Boolean>,error : MutableState<String>) {
    if (showDialog.value) {
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
}
@Composable
fun selectedDropDownMenu(valor : MutableState<String>) {
    val isDialogOpen = remember {
        mutableStateOf(false)
    }
    val focus = LocalFocusManager.current
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    val selectedText = remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val especialidad = remember {
        mutableStateOf<Array<String>?>(null)
    }
    val muestraBtnSiguiente = remember {
        mutableStateOf(false)
    }
    val muestraEspecialidad = remember {
        mutableStateOf(false)
    }
    val muestraEspecialidadList = remember {
        mutableStateOf(false)
    }

    val showExp = remember {
        mutableStateOf(false)
    }

    val especialidadSelect = remember {
        mutableStateOf("")
    }

    val experiencia = remember {
        mutableStateOf("")
    }


    val icon = if (expanded)

        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

        Column() {
            Text(text = "Títulos", fontSize = 16.sp, color = Color(0xffbfc9c9))
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = selectedText.value,
                onValueChange = { selectedText.value = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                label = {
                        Text(text = "Seleccione un título")
                },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "arrowExpanded",
                        modifier = Modifier.clickable {
                            focus.clearFocus()
                            expanded = !expanded
                        }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFEEEEEE),
                    focusedIndicatorColor = Color(
                        0xFF266E86
                    ),
                    unfocusedIndicatorColor = Color(0xff3b3d4c),
                    disabledIndicatorColor = Color(0xff3b3d4c),
                    textColor = Color(
                        0xFF266E86
                    ),
                    cursorColor = Color(0xFF999dba)
                )
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .fillMaxHeight(0.5f)
                    .background(color = Color(0xFFEEEEEE))
            ) {
                stringArrayResource(R.array.titulos).forEach { label ->
                    DropdownMenuItem(
                        modifier = Modifier.background(color = Color(0xFFEEEEEE)),
                        onClick = {
                            muestraEspecialidad.value = false
                            muestraEspecialidadList.value = false
                            muestraBtnSiguiente.value = false
                            showExp.value = false
                            selectedText.value = label
                            expanded = false
                        }
                    ) {
                        Text(text = label, color = Color(0xff3b3d4c))
                    }
                }
            }
        }
            if (selectedText.value != "") {
                dropDownEspecialidad(titulo = selectedText, especialidad = especialidad, muestraBtnSiguiente = muestraBtnSiguiente, muestraEspecialidad = muestraEspecialidad, muestraEspecialidadList = muestraEspecialidadList, showExp = showExp, especialidadSelect = especialidadSelect, experiencia = experiencia, isDialogOpen =  isDialogOpen, valor = valor)
            }
    }

@Composable
fun dropDownEspecialidad(valor : MutableState<String>,titulo: MutableState<String> , especialidad : MutableState<Array<String>?>, muestraBtnSiguiente : MutableState<Boolean>, muestraEspecialidad  : MutableState<Boolean>, muestraEspecialidadList: MutableState<Boolean>,showExp : MutableState<Boolean>, especialidadSelect : MutableState<String>, experiencia : MutableState<String>, isDialogOpen : MutableState<Boolean>) {
    val dialogError = remember {
        mutableStateOf("")
    }
    dialogoError(showDialog = isDialogOpen, error = dialogError )
    when(titulo.value) {
        "ESO","Bachiller", -> {
            muestraBtnSiguiente.value = true
        }
        "Postgrado","Máster","Otros títulos, certificaciones y carnés","Otros cursos y certificación no reglada" -> {
            muestraEspecialidad.value = true
        }
        else -> {
            muestraEspecialidadList.value = true
            when(titulo.value) {
                "FP Grado medio" -> especialidad.value = stringArrayResource(R.array.fp_medio)
                "FP Grado superior" -> especialidad.value = stringArrayResource(R.array.fp_superior)
                "Enseñanzas artísticas(regladas)" -> especialidad.value = stringArrayResource(R.array.artisticas)
                "Enseñanzas deportivas(regladas)" ->  especialidad.value = stringArrayResource(R.array.departivas)
                "Grado" -> especialidad.value = stringArrayResource(R.array.grado)
                "Licencitura" -> especialidad.value = stringArrayResource(R.array.licenciatura)
                "Diplomatura" -> especialidad.value = stringArrayResource(R.array.diplomatura)
                "Ingeniería técnica" -> especialidad.value = stringArrayResource(R.array.ing_tec)
                "Ingeniería superior" -> especialidad.value = stringArrayResource(R.array.ing_sup)
                "Doctorado" -> especialidad.value = stringArrayResource(R.array.doctorado)
                "Ciclo formativo grado medio" -> especialidad.value = stringArrayResource(R.array.ciclo_gradoMed)
                "Ciclo formativo grado superior" -> especialidad.value = stringArrayResource(R.array.ciclo_gradoSup)
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    if (muestraEspecialidadList.value) {
        //El titulo seleccionado tiene una lista de especialidades
    }
    if(muestraEspecialidad.value){
        //Muestra un Textfield normal
            Column() {
                Text(text = "Especialidad", fontSize = 16.sp, color = Color(0xffbfc9c9))
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = especialidadSelect.value,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.comforta))
                    ),
                    label = {
                        Text(text = "Introduzca su especialidad")
                    },
                    onValueChange = { it.also { especialidadSelect.value = it } },
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
            }
            Column() {
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()){
                    Text(text = "Añadir experiencia", fontSize = 16.sp, color = Color(0xffbfc9c9))
                    Switch(checked = showExp.value, onCheckedChange = {showExp.value = !showExp.value})
                }
                if (showExp.value) {
                    Spacer(modifier = Modifier.height(5.dp))
                    TextField(
                        value = experiencia.value,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(R.font.comforta))
                        ),
                        label = {
                            Text(text = "Experiencia(Años)")
                        },
                        onValueChange = { it.also { experiencia.value = it } },
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
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        )
                    )
                }
            }
    }
    if (muestraBtnSiguiente.value) {
        Column() {

            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()){
                Text(text = "Añadir experiencia", fontSize = 16.sp, color = Color(0xffbfc9c9))
                Switch(checked = showExp.value, onCheckedChange = {showExp.value = !showExp.value})
            }
            if (showExp.value) {
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = experiencia.value,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.comforta))
                    ),
                    label = {
                        Text(text = "Experiencia(Años)")
                    },
                    onValueChange = { it.also { experiencia.value = it }
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
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )
            }
        }
    }
    //Mostramos el botón de siguiente
    Spacer(modifier = Modifier.height(30.dp))
    Button(
        onClick = {
                  if (muestraEspecialidad.value) {
                      //Checkear que todos los campos son validos
                      if (especialidadSelect.value == "") {
                          isDialogOpen.value = true
                          dialogError.value = "Debe introducir una especialidad."
                      }else if (showExp.value && experiencia.value == "") {
                          isDialogOpen.value = true
                          dialogError.value = "Debe introducir la experiencia."
                      }else if (showExp.value) {
                          try {
                              val anyos = experiencia.value.toInt()
                              StaticVariables.titulo = titulo.value
                              StaticVariables.especialidad = especialidadSelect.value
                              StaticVariables.experiencia = anyos
                              valor.value = "3"
                          }catch (e : Exception) {
                             isDialogOpen.value = true
                             dialogError.value = "Debe introducir la experiencia\núnicamente en años."
                          }
                      }else {
                          StaticVariables.titulo = titulo.value
                          StaticVariables.especialidad = especialidadSelect.value
                          valor.value = "3"
                      }
                  }else if(muestraEspecialidadList.value) {
                      if (especialidadSelect.value == "") {
                          isDialogOpen.value = true
                          dialogError.value = "Debe introducir una especialidad."
                      }else if (showExp.value && experiencia.value == "") {
                          isDialogOpen.value = true
                          dialogError.value = "Debe introducir la experiencia."
                      }else if (showExp.value) {
                          try {
                              val anyos = experiencia.value.toInt()
                              StaticVariables.titulo = titulo.value
                              StaticVariables.especialidad = especialidadSelect.value
                              StaticVariables.experiencia = anyos
                              valor.value = "3"
                          }catch (e: Exception) {
                              isDialogOpen.value = true
                              dialogError.value = "Debe introducir la experiencia\núnicamente en años."
                          }
                      }else {
                          StaticVariables.titulo = titulo.value
                          StaticVariables.especialidad = especialidadSelect.value
                          valor.value = "3"
                      }
                  }else if(muestraBtnSiguiente.value) {
                        if (showExp.value && experiencia.value == "") {
                            isDialogOpen.value = true
                            dialogError.value = "Debe introducir la experiencia."
                        }else if (showExp.value){
                            try {
                                val anyos = experiencia.value.toInt()
                                StaticVariables.titulo = titulo.value
                                StaticVariables.experiencia = anyos
                                valor.value = "3"
                            }catch (e: Exception) {
                                isDialogOpen.value = true
                                dialogError.value = "Debe introducir la experiencia\núnicamente en años."
                            }
                        }else {
                            StaticVariables.titulo = titulo.value
                            StaticVariables.especialidad = titulo.value
                            valor.value = "3"
                        }
                  }
        },
        Modifier.fillMaxWidth(0.7f),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF266E86)
        )) {
        Text(text = "Siguiente", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp),fontFamily = FontFamily(Font(R.font.comforta)))
    }
}


@Composable
fun guardandoPerfil() {
    //Mostramos el icono de guardar
}

@Preview
@Composable
fun preview() {
    OnecTheme() {
        
    }
}

