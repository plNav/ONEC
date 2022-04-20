package com.example.onec.Vistas.Main.CV

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import coil.Coil
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.onec.Models.CvModel
import com.example.onec.Models.CvPost
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.CvViewModel
import com.example.onec.ui.theme.OnecTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.io.File
import java.lang.Exception
import java.util.regex.Pattern
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round


@Composable
fun creaCV(resultState: MutableState<String>) {


    /** valor -> Valor que tendra cada pestaña de creación de CV*/
    val valor = remember {
        mutableStateOf(StaticVariables.pasoRegistro)
    }

    /** showError -> Muestra o no el Dialog de Error*/
    val showError = remember {
        mutableStateOf(false)
    }

    /** errorMsj -> Contiene el error que se le pasa por parámetro al dialogo de error*/
    val errorMsj = remember {
        mutableStateOf("")
    }

    when (valor.value) {
        "1" -> creaCvDatos(valor = valor, error = showError, mensaje = errorMsj,)
        "2" -> creaCvTitulos(valor = valor)
        "3" -> creaCvHabilidades(valor = valor , resultState)
        else -> creaCvDatos(valor = valor, error = showError, mensaje = errorMsj,)
    }
    if (showError.value) {
        dialogoError(showDialog = showError, error = errorMsj)
    }
}


@Composable
fun creaCvDatos(valor : MutableState<String>,error: MutableState<Boolean>, mensaje : MutableState<String>) {
    OnecTheme {
        /** scrollState -> Almacena el estado del scroll*/
        val scrollState = rememberScrollState(0)

        /** selectedImage -> Almacena la imagen seleccionada*/
        var selectedImage = remember { mutableStateOf<Uri?>(null) }

        /** nombre -> Almacena el nombre del usuario*/
        val nombre = remember {
            mutableStateOf("")
        }

        /** telefono -> Almacena el teléfono del usuario*/
        val telefono = remember {
            mutableStateOf("")
        }

        /** ubicacion -> Almacena la ubicación del usuario*/
        val ubicacion = remember {
            mutableStateOf("")
        }

        /** launcher -> Sirve para poder abrir la galeria y seleccionar una imagen*/
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    selectedImage.value = uri
                    StaticVariables.imageUri = uri
                    //Creamos un archivo que apunta al uri que hemos sacado antes
                    StaticVariables.imagen = File(uri.path)
                }
                StaticVariables.fragmento = 3
                Log.e("Uri",selectedImage.value.toString())
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
                            SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current)
                                .data(selectedImage.value)
                                .crossfade(true)
                                .transformations(CircleCropTransformation())
                                .build(), loading = { CircularProgressIndicator()}, contentDescription = "Imagen",modifier = Modifier
                                .clickable {
                                    launcher.launch("image/*")
                                }
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth(0.3f),
                                error = {
                                    Image(
                                        painter = painterResource(id = R.drawable.foto),
                                        contentDescription = "Default",
                                        modifier = Modifier
                                            .fillMaxHeight(0.3f)
                                            .fillMaxWidth(0.3f)
                                    )
                                },
                                contentScale = ContentScale.Crop
                               )

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
                                if(nombre.value.isNotBlank() && nombre.value.isNotEmpty() && telefono.value.isNotBlank() && telefono.value.isNotEmpty() && ubicacion.value.isNotBlank() && ubicacion.value.isNotEmpty() && selectedImage.value != null) {
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

@OptIn(ExperimentalFoundationApi::class)
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
fun creaCvHabilidades(valor: MutableState<String>, resultState: MutableState<String>) {
    OnecTheme() {

        val guardarCV = remember {
            mutableStateOf(false)
        }

        val habilidadCreada = remember {
            mutableStateOf("")
        }

        /**showPopUp -> Booleano que controla si se muestra o no el dialogo para crear una nueva habilidad*/
        val showPopUp = remember {
            mutableStateOf(false)
        }

        /** scrollState -> Almacena el estado del scroll*/
        val scrollState = rememberScrollState(0)

        /** selector -> Se utiliza para ordenar posteriormente los elementos de listHabilidades, por longitud de carácteres*/
        val selector: (String) -> Int = { str -> str.length }

        /** listHabilidades -> Lista que almacena las habilidades insertadas por el usuario, por defecto será null*/
        val listHabilidades = remember {
            mutableStateOf(StaticVariables.habilidades)
        }

        if(!guardarCV.value) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Añade tus habilidades",
                    fontSize = 19.sp,
                    color = Color(0xffbfc9c9),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(15.dp))
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
                                    val habilidadesLow =
                                        listHabilidades.value.map { it.lowercase() }
                                    if (!habilidadesLow.contains(habilidadCreada.value.lowercase())) {
                                        StaticVariables.habilidades.add(habilidadCreada.value)
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
                                        .fillMaxSize()
                                        .verticalScroll(scrollState)
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
                                                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                            )
                                            IconButton(onClick = {
                                                listHabilidades.value.remove(habilidad)
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
                        Surface(shape = RoundedCornerShape(7.dp), color = Color(0xFF2F303A)) {
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
                            guardarCV.value = true
                        },
                        Modifier
                            .fillMaxWidth()
                            .align(CenterHorizontally),
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

                }

            }
        }else {
           guardandoPerfil(resultState = resultState)
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
@ExperimentalFoundationApi
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
                singleLine = true
                ,
                onValueChange = { selectedText.value = it },
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
                            muestraEspecialidad.value = false
                            muestraEspecialidadList.value = false
                            muestraBtnSiguiente.value = false
                            showExp.value = false
                            selectedText.value = label
                            especialidadSelect.value = ""
                            experiencia.value = ""
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

@ExperimentalFoundationApi
@Composable
fun dropDownEspecialidad(valor : MutableState<String>,titulo: MutableState<String> , especialidad : MutableState<Array<String>?>, muestraBtnSiguiente : MutableState<Boolean>, muestraEspecialidad  : MutableState<Boolean>, muestraEspecialidadList: MutableState<Boolean>,showExp : MutableState<Boolean>, especialidadSelect : MutableState<String>, experiencia : MutableState<String>, isDialogOpen : MutableState<Boolean>) {
    val dialogError = remember {
        mutableStateOf("")
    }

    val loading = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

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
                "Enseñanzas deportivas(regladas)" ->  especialidad.value = stringArrayResource(R.array.deportivas)
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

        val contentNull : @Composable () -> Unit? = {null}

        val focus = LocalFocusManager.current

        var expanded by remember { mutableStateOf(false) }

        var textfieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (expanded)

            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column {
            Text(text = "Especialidad", fontSize = 16.sp, color = Color(0xffbfc9c9))
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = especialidadSelect.value,
                singleLine = true,
                onValueChange = { especialidadSelect.value = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                label = {
                    Text(text = "Seleccione una especialidad", overflow = TextOverflow.Ellipsis)
                },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "arrowExpanded",
                        modifier = Modifier
                            .clickable {
                                focus.clearFocus()
                                if (expanded) {
                                    expanded = false
                                }else {
                                    expanded = true
                                    loading.value = true
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
            val scope = rememberCoroutineScope()
                if (loading.value) {
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

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}, modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })) {
                    if (expanded) {
                        especialidad.value!!.forEach { label ->
                            DropdownMenuItem(
                                onClick = {
                                    muestraEspecialidad.value = false
                                    muestraEspecialidadList.value = false
                                    muestraBtnSiguiente.value = false
                                    showExp.value = false
                                    especialidadSelect.value = label
                                    expanded = false
                                }
                            ) {
                                Text(text = label, color = Color(0xff3b3d4c))
                            }
                        }
                        loading.value = false
                    }else {
                        DropdownMenu(expanded = expanded, onDismissRequest = {}) {

                        }
                    }
                }
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
                        Text(text = "Introduzca su especialidad", overflow = TextOverflow.Ellipsis)
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
                            Text(text = "Experiencia(Años)", overflow = TextOverflow.Ellipsis)
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
                        Text(text = "Experiencia(Años)", overflow = TextOverflow.Ellipsis)
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
                              StaticVariables.pasoRegistro = "3"
                              valor.value = "3"
                              Log.e("Resultado","imagen ${StaticVariables.imageUri}\nNombre ${StaticVariables.nombreCv}\nTelefono ${StaticVariables.telefono}\nUbicacion ${StaticVariables.ubicacion}\nExperiencia ${StaticVariables.experiencia}\nTitulo ${StaticVariables.titulo}\nEspecialidad ${StaticVariables.especialidad}")
                          }catch (e : Exception) {
                             isDialogOpen.value = true
                             dialogError.value = "Debe introducir la experiencia\núnicamente en años."
                          }
                      }else {
                          StaticVariables.titulo = titulo.value
                          StaticVariables.especialidad = especialidadSelect.value
                          StaticVariables.pasoRegistro = "3"
                          valor.value = "3"
                          Log.e("Resultado","imagen ${StaticVariables.imageUri}\nNombre ${StaticVariables.nombreCv}\nTelefono ${StaticVariables.telefono}\nUbicacion ${StaticVariables.ubicacion}\nExperiencia ${StaticVariables.experiencia}\nTitulo ${StaticVariables.titulo}\nEspecialidad ${StaticVariables.especialidad}")
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
                              StaticVariables.pasoRegistro = "3"
                              valor.value = "3"
                              Log.e("Resultado","imagen ${StaticVariables.imageUri}\nNombre ${StaticVariables.nombreCv}\nTelefono ${StaticVariables.telefono}\nUbicacion ${StaticVariables.ubicacion}\nExperiencia ${StaticVariables.experiencia}\nTitulo ${StaticVariables.titulo}\nEspecialidad ${StaticVariables.especialidad}")
                          }catch (e: Exception) {
                              isDialogOpen.value = true
                              dialogError.value = "Debe introducir la experiencia\núnicamente en años."
                          }
                      }else {
                          StaticVariables.titulo = titulo.value
                          StaticVariables.especialidad = especialidadSelect.value
                          StaticVariables.pasoRegistro = "3"
                          valor.value = "3"
                          Log.e("Resultado","imagen ${StaticVariables.imageUri}\nNombre ${StaticVariables.nombreCv}\nTelefono ${StaticVariables.telefono}\nUbicacion ${StaticVariables.ubicacion}\nExperiencia ${StaticVariables.experiencia}\nTitulo ${StaticVariables.titulo}\nEspecialidad ${StaticVariables.especialidad}")
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
                                StaticVariables.pasoRegistro = "3"
                                valor.value = "3"
                                Log.e("Resultado","imagen ${StaticVariables.imageUri}\nNombre ${StaticVariables.nombreCv}\nTelefono ${StaticVariables.telefono}\nUbicacion ${StaticVariables.ubicacion}\nExperiencia ${StaticVariables.experiencia}\nTitulo ${StaticVariables.titulo}\nEspecialidad ${StaticVariables.especialidad}")
                            }catch (e: Exception) {
                                isDialogOpen.value = true
                                dialogError.value = "Debe introducir la experiencia\núnicamente en años."
                            }
                        }else {
                            StaticVariables.titulo = titulo.value
                            StaticVariables.pasoRegistro = "3"
                            valor.value = "3"
                            Log.e("Resultado","imagen ${StaticVariables.imageUri}\nNombre ${StaticVariables.nombreCv}\nTelefono ${StaticVariables.telefono}\nUbicacion ${StaticVariables.ubicacion}\nExperiencia ${StaticVariables.experiencia}\nTitulo ${StaticVariables.titulo}\nEspecialidad ${StaticVariables.especialidad}")
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
fun guardandoPerfil(resultState: MutableState<String>) {

    OnecTheme() {
        val cv = remember {
            mutableStateOf<CvPost?>(null)
        }

        val mostrarError = remember {
            mutableStateOf(false)
        }

        val viewModelCv = CvViewModel()

        val loading = remember {
            mutableStateOf(true)
        }
        if (loading.value) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp), color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Creando CV...", fontSize = 16.sp, color = Color(0xfffcffff))
            }
             cv.value = CvPost(
                StaticVariables.usuario!!._id,
                "Prueba",
                StaticVariables.nombreCv,
                StaticVariables.telefono,
                StaticVariables.ubicacion,
                StaticVariables.usuario!!.email,
                StaticVariables.experiencia,
                StaticVariables.titulo,
                StaticVariables.especialidad,
                StaticVariables.habilidades
            ) //No está mal, la imagen tiene que ser Uri, pero primero hay que subirla a un servidor y después guardarla como String
                viewModelCv.crearCv(cv.value!!) { cvModel ->
                    if (cvModel != null) {
                        StaticVariables.cv = cvModel
                        loading.value = false
                        resultState.value = "LOADED"
                    } else {
                        //Mostramos un error y volvemos a intentarlo
                        loading.value = false
                        mostrarError.value = true
                    }
                }
        }
        guardarPerfilError(mostrarError,cv.value!!,resultState)
    }
}


@Composable
fun guardarPerfilError(visible : MutableState<Boolean>, cv : CvPost, resultState: MutableState<String>) {
    //Si la variable es true, es decir que se quiera mostrar el error entonces muestra la vista del composable
    OnecTheme {
        val cvViewModel = remember {
            CvViewModel()
        }

        val scrollState = rememberScrollState(0)

        val loading = remember {
            mutableStateOf(false)
        }

        if (visible.value) {
            if (!loading.value) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error al crear CV", fontSize = 23.sp, color = Color(0xfffcffff))
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.errorlog),
                        contentDescription = "Error"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Error producido durante la creación de su CV\n por favor, inténtelo más tarde.",
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {loading.value = true},
                        Modifier
                            .fillMaxWidth(0.8f),
                        shape = RoundedCornerShape(8.dp),
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
                            modifier = Modifier.padding(0.dp, 2.dp),
                            fontFamily = FontFamily(Font(R.font.comforta))
                        )
                    }
                }
            }else {
                //Muestra el simbolo de carga
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp), color = Color(0xfffcffff)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Creando CV...", fontSize = 16.sp, color = Color(0xfffcffff))
                }

                cvViewModel.crearCv(cv) { cvModel ->
                    if (cvModel != null) {
                        StaticVariables.cv = cvModel
                        loading.value = false
                        resultState.value = "LOADED"
                    } else {

                        loading.value = false
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun prev() {
    Surface(modifier = Modifier.fillMaxHeight(0.5f), shape = RoundedCornerShape(5.dp), color = Color(0xfffcffff)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                color = Color(
                    0xFF266E86
                )
            )
            Text(
                text = "Cargando...", modifier = Modifier.padding(5.dp), color = Color(
                    0xFF266E86
                )
            )
        }
    }
}