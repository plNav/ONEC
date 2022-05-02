package com.example.onec.Vistas.Main.CV

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CvViewModel
import com.example.onec.R
import com.example.onec.ViewModels.CandidatosOfertasViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Perfil.dialogChangedPass
import com.example.onec.ui.theme.OnecTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import java.util.*

@Composable
fun cvMain(selected: MutableState<Boolean>) {



    val cvViewModel = remember {
        CvViewModel()
    }

    val resultState = remember {
        mutableStateOf("LOADING")
    }
    val loading = remember {
        mutableStateOf(true)
    }

    val showError = remember {
        mutableStateOf(false)
    }

    if (selected.value) {
        //Mostramos el Loading, para indicar al usuario que se están cargando datos
        if (loading.value) {
            LoadCv()
            //Comprobar si el usuario tiene ya un CV
            cvViewModel.obtenerCvUsuarioActual() { cv: CvModel?, succes: Boolean? ->
                if (cv != null && succes == true) {
                    //El usuario ya ha creado un CV, por lo tanto se guardo
                        if (cv.correo != StaticVariables.usuario!!.email) {
                            StaticVariables.cv = cv
                                val cvPost = CvPost(cv.id_user,cv.nombre,cv.telefono,cv.ubicacion,StaticVariables.usuario!!.email,cv.experiencia,cv.titulo,cv.especialidad,cv.habilidades,cv.habilidadesLow)
                                cvViewModel.actualizarCV(cv._id,cvPost) { did ->
                                   if (did) {
                                       StaticVariables!!.cv!!.correo = cvPost.correo
                                       resultState.value = "LOADED"
                                       loading.value = false
                                   }else {
                                       StaticVariables.cv = null
                                       resultState.value = "ERROR"
                                       loading.value = false
                                   }
                                }

                        }else {
                            StaticVariables.cv = cv
                            resultState.value = "LOADED"
                            loading.value = false
                        }

                } else if (cv == null && succes == true) {
                    //No tiene un CV, por lo tanto cargamos los composables de la creacion de un cv
                    resultState.value = "NOCV"
                    loading.value = false
                } else {
                    //Ha ocurrido un error, mostrar mensaje de error
                    resultState.value = "ERROR"
                    loading.value = false
                    showError.value = true
                }
            }
        }else {
            when (resultState.value) {
                "LOADED" -> muestraCv()
                "NOCV" -> creaCV(resultState)
                "ERROR" -> dialogError(showError,loading)
                else -> dialogError(showError = showError,loading)
            }

        }

    }else {
        if (resultState.value == "LOADING") {
            loading.value = true
        }
    }
}

@Composable
fun LoadCv() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column( horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
                color = Color(0xfffcffff)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff))
        }
            
    }
    
}

@Composable
fun dialogError(showError: MutableState<Boolean>, recarga : MutableState<Boolean>) {
    OnecTheme() {
    if (showError.value) {
            val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Error al cargar CV", fontSize = 19.sp, color = Color(0xfffcffff))
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.errorlog),
                    contentDescription = "Error log"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Ha ocurrido un error\nal cargar el CV del usuario\nreinténtelo más tarde.", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                              recarga.value = true
                    },
                    Modifier.fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            0xFF266E86
                        )
                    )
                ) {
                    Text(
                        text = "Reintentar",
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

@Composable
fun muestraCv() {
    OnecTheme {

        val cvViewModel = remember {
            CvViewModel()
        }

        val candidatosOfertasViewModel = remember {
            CandidatosOfertasViewModel()
        }

        val showError = remember {
            mutableStateOf(false)
        }

        val errMsj = remember {
            mutableStateOf("Error desconocido")
        }

        val showDialogActualizado = remember {
            mutableStateOf(false)
        }

        val scrollState = rememberScrollState(0)

        val nombre = remember {
            mutableStateOf(StaticVariables.cv!!.nombre)
        }

        val focus = LocalFocusManager.current

        val telefono = remember {
            mutableStateOf(StaticVariables.cv!!.telefono)
        }

        val ubicacion = remember {
            mutableStateOf(StaticVariables.cv!!.ubicacion)
        }

        val especialidad = remember {
            mutableStateOf<String?>(
                if(StaticVariables.cv!!.especialidad == null) {
                    ""
                }else {
                    StaticVariables.cv!!.especialidad
                }
            )
        }

        val experiencia = remember {
            mutableStateOf(StaticVariables.cv!!.experiencia.toString())
        }

        val loadingEsp = remember {
            mutableStateOf(false)
        }

        val titulo = remember {
            mutableStateOf(StaticVariables.cv!!.titulo)
        }
        var expanded by remember { mutableStateOf(false) }

        var expandedEsp by remember { mutableStateOf(false) }

        var mostrarEspecialidadLista by remember {
           mutableStateOf(false)
        }

        var mostrarEspecialidadTxt by remember {
            mutableStateOf(false)
        }

        var mostrarEspecialidad by remember {
            mutableStateOf(false)
        }

        val especialidadRes = remember {
            mutableStateOf<Array<String>?>(null)
        }

        val habilidadCreada = remember {
            mutableStateOf("")
        }

        val listHabilidades = remember {
            mutableStateOf(StaticVariables.cv!!.habilidades.toMutableStateList())
        }

        val loadList = remember {
            mutableStateOf(true)
        }

        val loading = remember {
            mutableStateOf(false)
        }

        var textfieldSize by remember { mutableStateOf(Size.Zero) }

        val selector: (String) -> Int = { str -> str.length }

        val icon = if (expanded)

            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(10.dp, 5.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Mi Curriculum", fontSize = 19.sp, textAlign = TextAlign.Center, color =  Color(0xfffcffff), modifier = Modifier.fillMaxWidth())
            //Nombre
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Datos personales", fontSize = 16.sp, color = Color(0xfffcffff))
            Spacer(modifier = Modifier.height(5.dp))
            Divider(thickness = 2.dp, color = Color(0xfffcffff))
            Spacer(modifier = Modifier.height(20.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Titulación", fontSize = 16.sp, color = Color(0xfffcffff))
            Spacer(modifier = Modifier.height(5.dp))
            Divider(thickness = 2.dp, color = Color(0xfffcffff))
            Spacer(modifier = Modifier.height(20.dp))
            Column() {
                Text(text = "Título", fontSize = 14.sp, color = Color(0xffbfc9c9))
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = titulo.value,
                    singleLine = true
                    ,
                    onValueChange = { titulo.value = it },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textfieldSize = coordinates.size.toSize()
                        },
                    label = {
                        Text(text = "Seleccione un título", overflow = TextOverflow.Ellipsis)
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
                                titulo.value = label
                                especialidad.value = ""
                                expanded = false
                            }
                        ) {
                            Text(text = label, color = Color(0xff3b3d4c))
                        }
                    }
                }
                when(titulo.value) {
                    "ESO","Bachiller" -> {
                        mostrarEspecialidad = false
                        especialidad.value = null
                        mostrarEspecialidad = false
                    }
                    "Postgrado","Máster","Otros títulos, certificaciones y carnés","Otros cursos y certificación no reglada" -> {
                        mostrarEspecialidad = true
                        mostrarEspecialidadLista = false
                        mostrarEspecialidadTxt = false
                    }
                    else -> {
                        mostrarEspecialidad = true
                        mostrarEspecialidadTxt = false
                        mostrarEspecialidadLista = true
                        when(titulo.value) {
                            "FP Grado medio" -> especialidadRes.value = stringArrayResource(R.array.fp_medio)
                            "FP Grado superior" -> especialidadRes.value = stringArrayResource(R.array.fp_superior)
                            "Enseñanzas artísticas(regladas)" -> especialidadRes.value = stringArrayResource(R.array.artisticas)
                            "Enseñanzas deportivas(regladas)" ->  especialidadRes.value = stringArrayResource(R.array.deportivas)
                            "Grado" -> especialidadRes.value = stringArrayResource(R.array.grado)
                            "Licenciatura" -> especialidadRes.value = stringArrayResource(R.array.licenciatura)
                            "Diplomatura" -> especialidadRes.value = stringArrayResource(R.array.diplomatura)
                            "Ingeniería técnica" -> especialidadRes.value = stringArrayResource(R.array.ing_tec)
                            "Ingeniería superior" -> especialidadRes.value = stringArrayResource(R.array.ing_sup)
                            "Doctorado" -> especialidadRes.value = stringArrayResource(R.array.doctorado)
                            "Ciclo formativo grado medio" -> especialidadRes.value = stringArrayResource(R.array.ciclo_gradoMed)
                            "Ciclo formativo grado superior" -> especialidadRes.value = stringArrayResource(R.array.ciclo_gradoSup)
                        }
                    }
                }
                
                if (mostrarEspecialidad) {
                    if (mostrarEspecialidadTxt) {
                        Text(text = "Especialidad", fontSize = 14.sp, color = Color(0xfffcffff))
                        Spacer(modifier = Modifier.height(3.dp))
                        TextField(
                            value = especialidad.value!!,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            label = {
                                Text(
                                    text = "Introduzca su especialidad",
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            onValueChange = { it.also { especialidad.value = it } },
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
                    if (mostrarEspecialidadLista) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Especialidad", fontSize = 14.sp, color = Color(0xffbfc9c9))
                        Spacer(modifier = Modifier.height(5.dp))
                        TextField(
                            value = especialidad.value!!,
                            singleLine = true,
                            onValueChange = { especialidad.value = it },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    textfieldSize = coordinates.size.toSize()
                                },
                            label = {
                                Text(
                                    text = "Seleccione una especialidad",
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "arrowExpanded",
                                    modifier = Modifier
                                        .clickable {
                                            focus.clearFocus()
                                            if (expandedEsp) {
                                                expandedEsp = false
                                            } else {
                                                expandedEsp = true
                                                loadingEsp.value = true
                                            }
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
                        if (loadingEsp.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.4f)
                                    .fillMaxWidth()
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(5.dp),
                                    color = Color(0xfffcffff),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Cargando...",
                                        modifier = Modifier.padding(5.dp),
                                        color = Color(
                                            0xFF266E86
                                        )
                                    )
                                }
                            }
                        }

                        DropdownMenu(expanded = expandedEsp,
                            onDismissRequest = { expandedEsp = false },
                            modifier = Modifier
                                .fillMaxHeight(0.4f)
                                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                        ) {
                            if (expandedEsp) {
                                especialidadRes.value!!.forEach { label ->
                                    DropdownMenuItem(
                                        onClick = {
                                            especialidad.value = label
                                            expandedEsp = false
                                        }
                                    ) {
                                        Text(text = label, color = Color(0xff3b3d4c))
                                    }
                                }
                                loadingEsp.value = false
                            } else {
                                DropdownMenu(expanded = expandedEsp, onDismissRequest = {}) {

                                }
                            }
                        }
                    }
                }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Experiencia", fontSize = 16.sp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(thickness = 2.dp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(20.dp))
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
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Habilidades", fontSize = 16.sp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(thickness = 2.dp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = habilidadCreada.value,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(R.font.comforta)),
                            color = Color(0xFF266E86)
                        ),
                        placeholder = {
                            Text(
                                text = "# Habilidad...",
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        onValueChange = { it.also { habilidadCreada.value = it } },
                        shape = RoundedCornerShape(7.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Add",
                                tint = Color(0xFF388BA7),
                                modifier = Modifier.clickable {
                                    if (!habilidadCreada.value.isNullOrEmpty() && !habilidadCreada.value.isNullOrBlank()) {
                                        val habilidadesLow = listHabilidades.value
                                        if (!habilidadesLow.contains(habilidadCreada.value.lowercase())) {
                                            listHabilidades.value.add(habilidadCreada.value)
                                            StaticVariables.habilidades.add(habilidadCreada.value)
                                            StaticVariables.habilidadesLow.add(habilidadCreada.value.lowercase(
                                                Locale.getDefault()
                                            ))
                                            habilidadCreada.value = ""
                                        } else {
                                            habilidadCreada.value = ""
                                        }
                                    }
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFFCFFFF),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            textColor = Color(0xFF999dba),
                            cursorColor = Color(0xFF388BA7)
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        Modifier
                            .fillMaxSize()
                    ) {
                            if (listHabilidades.value != null && !listHabilidades.value.isEmpty()) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.8f),
                                    shape = RoundedCornerShape(7.dp),
                                    color = Color(0xFF2F303A)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(5.dp)
                                            .background(Color.Transparent)
                                    ) {
                                        FlowRow(
                                            modifier = Modifier
                                                .background(Color.Transparent),
                                            mainAxisAlignment = MainAxisAlignment.Start,
                                            mainAxisSize = SizeMode.Expand,
                                            crossAxisSpacing = 12.dp,
                                            mainAxisSpacing = 8.dp
                                        ) {
                                            listHabilidades.value!!.sortBy(selector)//Con esta línea Ordenamos por longitud de carácteres para que se ordene automáticamente "selector" -> Está definido arriba, es lo que le dice como debe hacer el sort .
                                            listHabilidades.value!!.forEach { habilidad ->
                                                Row(
                                                    modifier = Modifier
                                                        .background(
                                                            color = Color(0xFF388BA7),
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                        .padding(2.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "# $habilidad",
                                                        color = Color.White,
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1,
                                                        modifier = Modifier.padding(
                                                            10.dp,
                                                            0.dp,
                                                            0.dp,
                                                            0.dp
                                                        )
                                                    )
                                                    IconButton(onClick = {
                                                        listHabilidades.value.remove(habilidad)
                                                        Log.d("Size",listHabilidades.value.size.toString())
                                                        StaticVariables.habilidades.remove(habilidad)
                                                        StaticVariables.habilidadesLow.remove(
                                                            habilidad.lowercase(
                                                                Locale.getDefault()
                                                            )
                                                        )
                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Delete,
                                                            contentDescription = "delete",
                                                            tint = Color(
                                                                0xFF34565F
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(7.dp),
                                    color = Color(0xFF2F303A)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.7f)
                                    ) {
                                        Text(
                                            text = "Ninguna habilidad especificada",
                                            fontSize = 19.sp,
                                            color = Color(0xfffcffff)
                                        )
                                    }
                                }
                            }
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            onClick = {
                               //Primero comprobamos los campos
                                      if (nombre.value.isNullOrEmpty() || nombre.value.isNullOrBlank() || telefono.value.isNullOrEmpty() || telefono.value.isNullOrEmpty() || telefono.value.isNullOrBlank() || ubicacion.value.isNullOrEmpty() || ubicacion.value.isNullOrBlank()) {
                                          showError.value = true
                                          errMsj.value = "Faltan campos por introducir."
                                      }else if (mostrarEspecialidad && especialidad.value.isNullOrEmpty() || mostrarEspecialidad && especialidad.value.isNullOrBlank()) {
                                          showError.value = true
                                          errMsj.value = "Debe introducir una especialidad."
                                      }else if (experiencia.value.contains(" ")) {
                                          showError.value = true
                                          errMsj.value = "La experiencia introducida\ntiene un formato no válido"
                                      }else {
                                          loading.value = true
                                          val exp = if (experiencia.value == "") 0 else experiencia.value.toInt()
                                          val esp = if (especialidad.value == "") null else especialidad.value
                                          val habilidadesLow = listHabilidades.value.map { it.lowercase() }
                                          val cv = CvPost(StaticVariables.usuario!!._id,nombre.value,telefono.value,ubicacion.value,StaticVariables.usuario!!.email,exp,titulo.value,esp,listHabilidades.value,habilidadesLow.toMutableList())
                                          if (titulo.value != StaticVariables.cv!!.titulo || especialidad.value != StaticVariables.cv!!.especialidad) {
                                              //El curriculum ha variado en estudios, por lo tanto hay que eliminarlo de las ofertas en las que ha sido aceptado

                                              candidatosOfertasViewModel.eliminarOfertasCandidato(StaticVariables.cv!!._id) { did ->
                                                  if (did) {
                                                      cvViewModel.actualizarCV(StaticVariables.cv!!._id,cv) { did
                                                          if (did) {
                                                              StaticVariables.cv!!.nombre = cv.nombre
                                                              StaticVariables.cv!!.telefono = cv.telefono
                                                              StaticVariables.cv!!.ubicacion = cv.ubicacion
                                                              StaticVariables.cv!!.experiencia = cv.experiencia
                                                              StaticVariables.cv!!.titulo = cv.titulo
                                                              StaticVariables.cv!!.especialidad = cv.especialidad
                                                              StaticVariables.cv!!.habilidades = cv.habilidades
                                                              StaticVariables.cv!!.habilidadesLow = cv.habilidadesLow
                                                              showDialogActualizado.value = true
                                                          }else {
                                                              showError.value = true
                                                              errMsj.value = "Error al actualizar el CV"
                                                          }
                                                          loading.value = false
                                                      }
                                                  }else {
                                                      showError.value = true
                                                      errMsj.value = "Error al actualizar el CV"
                                                      loading.value = false
                                                  }
                                              }
                                          }else {
                                              //El usuario, ha añadido mas información importante a sus estudios, por lo tanto actualizamos el cv pero sin eliminar las ofertas aceptadas
                                                      cvViewModel.actualizarCV(StaticVariables.cv!!._id,cv) { did ->
                                                          if (did) {
                                                              StaticVariables.cv!!.nombre = cv.nombre
                                                              StaticVariables.cv!!.telefono = cv.telefono
                                                              StaticVariables.cv!!.ubicacion = cv.ubicacion
                                                              StaticVariables.cv!!.experiencia = cv.experiencia
                                                              StaticVariables.cv!!.titulo = cv.titulo
                                                              StaticVariables.cv!!.especialidad = cv.especialidad
                                                              StaticVariables.cv!!.habilidades = cv.habilidades
                                                              StaticVariables.cv!!.habilidadesLow = cv.habilidadesLow
                                                              showDialogActualizado.value = true
                                                          }else {
                                                              showError.value = true
                                                              errMsj.value = "Error al actualizar el CV"
                                                          }
                                                          loading.value = false
                                                      }
                                          }
                                      }
                            },
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    0xFF266E86
                                )
                            )
                        ) {
                            Text(
                                text = "Guardar cambios",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(0.dp, 2.dp),
                                fontFamily = FontFamily(Font(R.font.comforta))
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        dialogLoading(show = loading)
                        dialogActualizado(show = showDialogActualizado )
                        dialogoError(showDialog = showError, error = errMsj)

                }

            }
        }
    }
}

@Composable
fun dialogActualizado(show : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
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
                            text = "Curriculum actualizado",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth() )
                        Text(
                            text = "Su curriculum vitae\nha sido actualizado.",
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

