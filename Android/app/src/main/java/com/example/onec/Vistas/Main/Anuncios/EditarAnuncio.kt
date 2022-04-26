package com.example.onec.Vistas.Main.Anuncios

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Models.AnuncioPost
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ViewModels.AnuncioViewModel
import com.example.onec.Vistas.Login.dialogLoading
import com.example.onec.Vistas.Perfil.dialogError
import com.example.onec.ui.theme.OnecTheme
import java.lang.Exception

@Composable
fun editarAnuncio(navController: NavController) {
        OnecTheme {

            val showUpdated = remember {
                mutableStateOf(false)
            }

            val show = remember {
                mutableStateOf(true)
            }

            val errorMsj =  remember {
                mutableStateOf("Error desconocido")
            }
            val showError = remember {
                mutableStateOf(false)
            }

            val isLoading = remember {
                mutableStateOf(false)
            }

            val scrollState = rememberScrollState(0)

            val nombre = remember {
                mutableStateOf(StaticVariables.anunincioSeleccionado!!.nombre)
            }

            val categoria = remember {
                mutableStateOf(StaticVariables.anunincioSeleccionado!!.categoria)
            }

            val descripcion = remember {
                mutableStateOf(StaticVariables.anunincioSeleccionado!!.descripcion)
            }

            val precio = remember {
                mutableStateOf(StaticVariables.anunincioSeleccionado!!.precio.toString())
            }

            val isPrecioPorHora = remember {
                mutableStateOf(false)
            }

            val anuncioViewModel = remember {
                AnuncioViewModel()
            }
            if (show.value) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xff3b3d4c))
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Editar Anuncio",
                            fontSize = 19.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xfffcffff)
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        // TextField Categoría
                        TextField(
                            value = categoria.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = {

                            },
                            onValueChange = { it.also { categoria.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            label = {
                                Text(
                                    text = "Categoría",
                                    fontFamily = FontFamily(Font(R.font.comforta)),
                                    color = Color(0xFF999dba)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.categoria),
                                    contentDescription = "Categoria",
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
                        //Textfield nombre
                        TextField(
                            value = nombre.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            placeholder = {

                            },
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
                            placeholder = {

                            },
                            label = {
                                Text(
                                    text = "Descripción",
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
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Precio por hora",
                                fontSize = 16.sp,
                                color = Color(0xfffcffff)
                            )
                            Switch(
                                checked = isPrecioPorHora.value,
                                onCheckedChange = {
                                    isPrecioPorHora.value = !isPrecioPorHora.value
                                })
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        TextField(
                            value = precio.value,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.comforta))
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = {

                            },
                            label = {
                                Text(
                                    text = "Precio",
                                    fontFamily = FontFamily(Font(R.font.comforta)),
                                    color = Color(0xFF999dba)
                                )
                            },
                            onValueChange = { it.also { precio.value = it } },
                            shape = RoundedCornerShape(7.dp),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.euro),
                                    contentDescription = "Precio",
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
                        Spacer(modifier = Modifier.height(25.dp))
                        Button(
                            onClick = {
                                //Comprobar valores y actualizar Anuncio
                                if (nombre.value.isNullOrEmpty() || nombre.value.isNullOrBlank() || categoria.value.isNullOrEmpty() || categoria.value.isNullOrBlank() || descripcion.value.isNullOrEmpty() || descripcion.value.isNullOrBlank() || precio.value.isNullOrBlank() || precio.value.isNullOrEmpty()) {
                                    //Mostrar Error campos vacios
                                } else {
                                    try {
                                        val precioF = precio.value.toFloat()
                                        isLoading.value = true
                                        val anuncioActualizado = AnuncioPost(
                                            StaticVariables.usuario!!._id,
                                            categoria.value,
                                            nombre.value,
                                            descripcion.value,
                                            precioF,
                                            isPrecioPorHora.value,
                                            numVotos = StaticVariables!!.anunincioSeleccionado!!.numVotos,
                                            numVecesVisto = StaticVariables!!.anunincioSeleccionado!!.numVecesVisto,
                                            puntuacion = StaticVariables!!.anunincioSeleccionado!!.puntuacion
                                        )
                                        anuncioViewModel.actualizarAnuncio(
                                            StaticVariables.anunincioSeleccionado!!._id,
                                            anuncioActualizado
                                        ) { did ->
                                            if (did) {

                                                //Antes de actualizar el anuncio seleccionado, obtenemos el indice del anuncio para posteriormente remplazar dicho anuncio por el anuncio actualizado
                                                val index = StaticVariables.anunciosUsuario.indexOf(StaticVariables.anunincioSeleccionado)

                                                //Actualizamos el anuncio
                                                StaticVariables!!.anunincioSeleccionado!!.categoria = anuncioActualizado.categoria
                                                StaticVariables!!.anunincioSeleccionado!!.nombre = anuncioActualizado.nombre
                                                StaticVariables!!.anunincioSeleccionado!!.descripcion = anuncioActualizado.descripcion
                                                StaticVariables!!.anunincioSeleccionado!!.precio = precioF
                                                StaticVariables!!.anunincioSeleccionado!!.precioPorHora = anuncioActualizado.precioPorHora
                                                StaticVariables!!.anunincioSeleccionado!!.numVotos = anuncioActualizado.numVotos
                                                StaticVariables!!.anunincioSeleccionado!!.numVecesVisto = anuncioActualizado.numVecesVisto
                                                StaticVariables!!.anunincioSeleccionado!!.puntuacion = anuncioActualizado.puntuacion

                                                //Reemplazamos el AnuncioModel almacenado en dicho indice, por el anuncio actualizado
                                                StaticVariables.anunciosUsuario.set(index = index, StaticVariables.anunincioSeleccionado!!)

                                                //Mostramos que el anuncio ha sido actualizado
                                                showUpdated.value = true
                                                show.value = false
                                            } else {
                                                //Mostramos un error, de que no se ha podido actualizar
                                                errorMsj.value = "Error al actualizar el anuncio"
                                                showError.value = true
                                            }
                                            isLoading.value = false
                                        }
                                    } catch (e: Exception) {
                                        //Mostrar error de precio no válido
                                        errorMsj.value = "Debe introducir un precio válido"
                                        showError.value = true
                                    }
                                }
                            },
                            Modifier.fillMaxWidth(),
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
                                fontFamily = FontFamily(Font(R.font.comforta))
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                navController.navigate(Rutas.AnuncioDetalles.route) {
                                    popUpTo(
                                        Rutas.Main.route
                                    )
                                }
                            },
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFAD3B3F)
                            )
                        ) {
                            Text(
                                text = "Cancelar",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(0.dp, 7.dp),
                                fontFamily = FontFamily(Font(R.font.comforta))
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            updated(show = showUpdated, navController = navController)
            dialogLoading(show = isLoading)
            dialogError(show = showError , msj = errorMsj)
    }

}

@Composable
fun updated(show: MutableState<Boolean>, navController: NavController) {
    if (show.value) {
        OnecTheme() {
            Column(modifier = Modifier
                .fillMaxSize().background(Color(0xff3b3d4c)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Anuncio Actualizado",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth() )
                Text(
                    text = "El anuncio ha sido actualizado.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 19.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                    onClick = {
                        navController.navigate(Rutas.AnuncioDetalles.route) { popUpTo(Rutas.Main.route) }
                    },
                    Modifier.fillMaxWidth(0.8f),
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
            }
        }
    }
}
