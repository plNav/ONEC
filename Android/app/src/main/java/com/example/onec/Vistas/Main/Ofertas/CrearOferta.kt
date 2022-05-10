package com.example.onec.Vistas.Main.Ofertas

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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.onec.Models.CvPost
import com.example.onec.Models.OfertaPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.OfertaViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Main.Anuncios.loadingAnuncio
import com.example.onec.Vistas.Main.CV.dialogActualizado
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import java.util.*

@Composable
fun crearOferta(navController: NavController) {
    OnecTheme {

        val ofertasViewModel = remember {
            OfertaViewModel()
        }

        val scrollState = rememberScrollState()

        val nombre = remember {
            mutableStateOf("")
        }

        val descripcion = remember {
            mutableStateOf("")
        }

        val titulo = remember {
            mutableStateOf<String>("")
        }

        val especialidad = remember {
            mutableStateOf<String?>("")
        }

        val showDid = remember {
            mutableStateOf(false)
        }

        val experiencia = remember {
            mutableStateOf("")
        }

        val requiredTitulo = remember {
            mutableStateOf(true)
        }

        val mostrarEspecialidad = remember {
            mutableStateOf(false)
        }

        val loadingEsp = remember {
            mutableStateOf(false)
        }

        val loading = remember {
            mutableStateOf(false)
        }

        var mostrarEspecialidadLista by remember {
            mutableStateOf(false)
        }

        val showError = remember {
            mutableStateOf(false)
        }

        val errMsj = remember {
            mutableStateOf("Error desconocido")
        }

        var mostrarEspecialidadTxt by remember {
            mutableStateOf(false)
        }

        var expandedEsp by remember { mutableStateOf(false) }

        val requiredHabilidades = remember {
            mutableStateOf(true)
        }

        val requiredExp = remember {
            mutableStateOf(false)
        }

        val habilidadCreada = remember {
            mutableStateOf("")
        }

        val requerirHabilidades = remember {
            mutableStateOf(false)
        }

        val listHabilidades = remember {
            mutableStateOf(mutableListOf<String>().toMutableStateList())
        }

        val selector: (String) -> Int = { str -> str.length }

        val especialidadRes = remember {
            mutableStateOf<Array<String>?>(null)
        }

        val focus = LocalFocusManager.current

        var expanded by remember { mutableStateOf(false) }

        var textfieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (expanded)

            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color(0xff3b3d4c)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
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
                        text = "Crear Oferta",
                        fontSize = 19.sp,
                        color = Color(0xfffcffff),
                        fontFamily = FontFamily(
                            Font(R.font.comforta)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        content = {}) //Lo hacemos sin contenido, solo servirá para hacer que el titulo se quede centrado
                }
            )
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.9f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = nombre.value,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.comforta))
                    ),
                    label = {
                        Text(
                            text = "Nombre",
                            fontFamily = FontFamily(Font(R.font.comforta)),
                            color = Color(0xFF999dba)
                        )
                    },
                    onValueChange = { it.also { nombre.value = it } },
                    shape = RoundedCornerShape(7.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Nombre",
                            tint = Color(0xFF999dba),
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
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
                        textColor = Color(0xFFFCFFFF),
                        cursorColor = Color(0xFFFCFFFF)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                //TextFiled descripcion
                TextField(
                    value = descripcion.value,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.comforta))
                    ),
                    label = {
                        Text(
                            text = "Descripcion",
                            fontFamily = FontFamily(Font(R.font.comforta)),
                            color = Color(0xFF999dba)
                        )
                    },
                    onValueChange = { it.also { descripcion.value = it } },
                    shape = RoundedCornerShape(7.dp),
                    maxLines = 8,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(7.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFF353644),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = Color(0xFFFCFFFF),
                        cursorColor = Color(0xFFFCFFFF)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Titulo requerido", fontSize = 16.sp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.width(3.dp))
                    Switch(checked = requiredTitulo.value, onCheckedChange = {
                        requiredTitulo.value = !requiredTitulo.value
                        titulo.value = ""
                        especialidad.value = null
                        mostrarEspecialidad.value = false
                        especialidadRes.value = null
                    })
                }
                if (requiredTitulo.value) {
                    Spacer(modifier = Modifier.height(3.dp))
                    TextField(
                        value = titulo.value,
                        singleLine = true,
                        onValueChange = {
                            titulo.value = it
                            listHabilidades.value.clear()
                                        },
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
                                    listHabilidades.value.clear()
                                    expanded = false
                                }
                            ) {
                                Text(text = label, color = Color(0xff3b3d4c))
                            }
                        }
                    }
                    if (!titulo.value.isNullOrEmpty() || !titulo.value.isNullOrBlank()) {
                        when (titulo.value) {
                            "ESO", "Bachiller" -> {
                                mostrarEspecialidad.value = false
                                especialidad.value = null
                                mostrarEspecialidad.value = false
                                requerirHabilidades.value = true
                            }
                            "Postgrado", "Máster", "Otros títulos, certificaciones y carnés", "Otros cursos y certificación no reglada" -> {
                                mostrarEspecialidad.value = true
                                mostrarEspecialidadLista = false
                                mostrarEspecialidadTxt = true
                            }
                            else -> {
                                mostrarEspecialidad.value = true
                                mostrarEspecialidadTxt = false
                                mostrarEspecialidadLista = true
                                when (titulo.value) {
                                    "FP Grado medio" -> especialidadRes.value =
                                        stringArrayResource(R.array.fp_medio)
                                    "FP Grado superior" -> especialidadRes.value =
                                        stringArrayResource(R.array.fp_superior)
                                    "Enseñanzas artísticas(regladas)" -> especialidadRes.value =
                                        stringArrayResource(R.array.artisticas)
                                    "Enseñanzas deportivas(regladas)" -> especialidadRes.value =
                                        stringArrayResource(R.array.deportivas)
                                    "Grado" -> especialidadRes.value =
                                        stringArrayResource(R.array.grado)
                                    "Licenciatura" -> especialidadRes.value =
                                        stringArrayResource(R.array.licenciatura)
                                    "Diplomatura" -> especialidadRes.value =
                                        stringArrayResource(R.array.diplomatura)
                                    "Ingeniería técnica" -> especialidadRes.value =
                                        stringArrayResource(R.array.ing_tec)
                                    "Ingeniería superior" -> especialidadRes.value =
                                        stringArrayResource(R.array.ing_sup)
                                    "Doctorado" -> especialidadRes.value =
                                        stringArrayResource(R.array.doctorado)
                                    "Ciclo formativo grado medio" -> especialidadRes.value =
                                        stringArrayResource(R.array.ciclo_gradoMed)
                                    "Ciclo formativo grado superior" -> especialidadRes.value =
                                        stringArrayResource(R.array.ciclo_gradoSup)
                                }
                            }
                        }
                    }
                }
                if (mostrarEspecialidad.value && requiredTitulo.value) {
                    if (mostrarEspecialidadTxt) {
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                            Spacer(modifier = Modifier.height(10.dp))
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
                                onValueChange = { it.also {
                                    especialidad.value = it
                                    listHabilidades.value.clear()
                                } },
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
                    }
                    if (mostrarEspecialidadLista) {
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Especialidad", fontSize = 14.sp, color = Color(0xfffcffff))
                            Spacer(modifier = Modifier.height(5.dp))
                            TextField(
                                value = especialidad.value!!,
                                singleLine = true,
                                onValueChange = {
                                    especialidad.value = it
                                    listHabilidades.value.clear()
                                                },
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

                            DropdownMenu(
                                expanded = expandedEsp,
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
                                                listHabilidades.value.clear()
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
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Requerir experiencia", fontSize = 16.sp, color = Color(0xfffcffff))
                    Switch(checked = requiredExp.value, onCheckedChange = {
                        requiredExp.value = !requiredExp.value
                        experiencia.value = ""
                    })
                }
                if (requiredExp.value) {
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
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Requerir habilidades", fontSize = 16.sp, color = Color(0xfffcffff))
                    Switch(checked = requerirHabilidades.value, onCheckedChange = {
                        requerirHabilidades.value = !requerirHabilidades.value
                        listHabilidades.value.clear()
                    })
                }
                if (requerirHabilidades.value) {
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
                                            StaticVariables.habilidadesLow.add(
                                                habilidadCreada.value.lowercase(
                                                    Locale.getDefault()
                                                )
                                            )
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
                            Column(Modifier.fillMaxWidth()) {
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
                                                    Log.d(
                                                        "Size",
                                                        listHabilidades.value.size.toString()
                                                    )
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
                                Spacer(modifier = Modifier.height(5.dp))
                                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Requerir todas", color = Color(0xfffcffff))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Checkbox(checked = requiredHabilidades.value , onCheckedChange = {
                                        requiredHabilidades.value = !requiredHabilidades.value
                                    })
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
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Ninguna habilidad especificada",
                                        fontSize = 19.sp,
                                        color = Color(0xfffcffff)
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                              if (nombre.value.isNullOrEmpty() || nombre.value.isNullOrBlank() || descripcion.value.isNullOrEmpty() || descripcion.value.isNullOrBlank()) {
                                  showError.value = true
                                  errMsj.value = "Faltan campos por introducir"
                              }else if (requiredTitulo.value && titulo.value.isNullOrEmpty() || requiredTitulo.value && titulo.value.isNullOrBlank()) {
                                  showError.value = true
                                  errMsj.value = "De introducir un título"
                              }else if (mostrarEspecialidad.value && especialidad.value.isNullOrBlank() || mostrarEspecialidad.value && especialidad.value.isNullOrEmpty()) {
                                  showError.value = true
                                  errMsj.value = "Debe introducir una especialidad"
                              }else if (!requiredTitulo.value && !requerirHabilidades.value) {
                                  showError.value = true
                                  errMsj.value = "Debe crear la oferta\ncomo mínimo con un titulo\n o con unas habilidades."
                              }else if (requiredExp.value && experiencia.value.isNullOrEmpty() || requiredExp.value && experiencia.value.isNullOrBlank()) {
                                  showError.value = true
                                  errMsj.value = "Debe introducir una experiencia"
                              }else if (requiredExp.value && experiencia.value.toIntOrNull() == null) {
                                  showError.value = true
                                  errMsj.value = "Debe introducir una experiencia válida"
                              }else if (requerirHabilidades.value && listHabilidades.value.isEmpty()){
                                  showError.value = true
                                  errMsj.value = "Debe introducir almenos una habilidad"
                              }else {
                                  val titu =
                                      if (titulo.value.isNullOrEmpty() || titulo.value.isNullOrBlank()) null else titulo.value
                                  val espe =
                                      if (especialidad.value.isNullOrEmpty() || especialidad.value.isNullOrBlank()) null else especialidad.value
                                  val exp =
                                      if (experiencia.value.isNullOrBlank() || experiencia.value.isNullOrEmpty()) 0 else experiencia.value.toInt()
                                  val habilidadesLow = listHabilidades.value.map{ it.lowercase(
                                      Locale.getDefault()
                                  ) }.toMutableList()
                                  val reqHab = if (requerirHabilidades.value) requiredHabilidades.value else null
                                  val oferta = OfertaPost(StaticVariables.usuario!!._id,nombre.value, descripcion.value,exp,titu,espe,listHabilidades.value,habilidadesLow, reqHab)
                                  loading.value = true
                                  ofertasViewModel.crearOferta(oferta) { oferta ->
                                      if (oferta != null) {
                                          showDid.value = true
                                      }else {
                                          showError.value = true
                                          errMsj.value = "Error al crear la oferta"
                                      }
                                      loading.value = false
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
                        text = "Aceptar",
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 2.dp),
                        fontFamily = FontFamily(Font(R.font.comforta))
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                dialogLoading(show = loading)
                dialogError(show = showError , msj = errMsj )
                did(show = showDid, navController = navController)
            }
        }
    }
}

@Composable
fun did(show : MutableState<Boolean>, navController: NavController) {
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
                            text = "Oferta Creada",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth() )
                        Text(
                            text = "La oferta ha sido actualizada.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 19.sp,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                            onClick = {
                                show.value = false
                                navController.navigate(Rutas.Main.route) { popUpTo(0) }
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