package com.example.onec.Vistas.Main.Anuncios

import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.Vistas.Main.circuloLoading
import com.example.onec.Vistas.Perfil.loading
import com.example.onec.ui.theme.OnecTheme
import kotlin.text.Typography


@Composable
fun crearAnuncio(navController: NavController) {
    OnecTheme {
        val scrollState = rememberScrollState(0)
        val isLoading = remember {
            mutableStateOf(false)
        }

        val showAnuncioCreated = remember {
            mutableStateOf(false)
        }

        val showError = remember {
            mutableStateOf(false)
        }

        val showAnuncioMain = remember {
            mutableStateOf(true)
        }
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color(0xff3b3d4c))){
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = {
                        StaticVariables.fragmento = 2
                        navController.navigate(Rutas.Main.route) {popUpTo(0)}
                    }) {
                       Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24), contentDescription = "Back", tint = Color(0xfffcffff))
                    }
                }, backgroundColor = Color(0xff3b3d4c), elevation = 0.dp, contentColor = Color(0xfffcffff))
                Column(modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()) {
                    crearAnuncioMain(
                        show = showAnuncioMain,
                        isLoading = isLoading,
                        showCreated = showAnuncioCreated,
                        showError = showError
                    )
                    anuncioCreado(show = showAnuncioCreated)
                    errorAnuncioCrear(show = showError, isLoading = isLoading)
                    loadingAnuncio(show = isLoading)
                }
        }
    }

    //Configuramos el Back Handler
    BackHandler() {
        StaticVariables.fragmento = 2
        navController.navigate(Rutas.Main.route) {popUpTo(0)}
    }
}

@Composable
fun crearAnuncioMain(show: MutableState<Boolean>, isLoading: MutableState<Boolean>, showCreated: MutableState<Boolean>, showError : MutableState<Boolean> ){
   OnecTheme {

       val nombre = remember {
           mutableStateOf("")
       }

       val categoria = remember {
           mutableStateOf("")
       }

       val descripcion = remember {
           mutableStateOf("")
       }

       val precio = remember {
           mutableStateOf("")
       }

       val isPrecioPorHora = remember {
           mutableStateOf(false)
       }

      Column(
          Modifier
              .fillMaxSize()
              .padding(horizontal = 10.dp)
          , horizontalAlignment = Alignment.CenterHorizontally) {
          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "Publicar Anuncio", fontSize = 19.sp, textAlign = TextAlign.Center, color = Color(0xfffcffff))
          Spacer(modifier = Modifier.height(25.dp))
          // TextField Categoría
          TextField(value = categoria.value,
              singleLine = true,
              textStyle = TextStyle(
                  fontFamily = FontFamily(Font(R.font.comforta))
              ),
              placeholder = { Text(text = "Categoria", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
              onValueChange = { it.also { categoria.value = it } },
              shape = RoundedCornerShape(7.dp),
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
                  textColor = Color(0xFF999dba),
                  cursorColor = Color(0xFF999dba)
              )
          )
          Spacer(modifier = Modifier.height(20.dp))
          //Textfield nombre
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
                  textColor = Color(0xFF999dba),
                  cursorColor = Color(0xFF999dba)
              )
          )
          Spacer(modifier = Modifier.height(20.dp))
          //TextFiled descripcion
          TextField(value = descripcion.value,
              textStyle = TextStyle(
                  fontFamily = FontFamily(Font(R.font.comforta))
              ),
              placeholder = { Text(text = "Descripción...", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
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
                  textColor = Color(0xFF999dba),
                  cursorColor = Color(0xFF999dba)
              )
          )
          Spacer(modifier = Modifier.height(20.dp))
          Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
              Text(text = "Precio por hora", fontSize = 16.sp, color = Color(0xfffcffff))
              Switch(checked = isPrecioPorHora.value, onCheckedChange = {isPrecioPorHora.value = !isPrecioPorHora.value})
          }
          Spacer(modifier = Modifier.height(5.dp))
          TextField(value = precio.value,
              singleLine = true,
              textStyle = TextStyle(
                  fontFamily = FontFamily(Font(R.font.comforta))
              ),
              placeholder = { Text(text = "Precio", color = Color(0xFF999dba),fontFamily = FontFamily(Font(R.font.comforta))) },
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
                  textColor = Color(0xFF999dba),
                  cursorColor = Color(0xFF999dba)
              )
          )
          Spacer(modifier = Modifier.height(25.dp))
          Button(onClick = {},Modifier.fillMaxWidth(), shape = RoundedCornerShape(7.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
              0xFF266E86
          )
          )) {
              Text(text = "Aceptar", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp,7.dp), fontFamily = FontFamily(Font(R.font.comforta)))
          }
          Spacer(modifier = Modifier.height(10.dp))
      } 
   } 
}

@Composable
fun anuncioCreado(show: MutableState<Boolean>) {
    if (show.value) {
        OnecTheme {
            val scrollState = rememberScrollState(0)
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Anuncio publicado",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(painter = painterResource(id = R.drawable.good), contentDescription = "Good", alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth() )
                Text(
                    text = "El anuncio ha sido públicado.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 19.sp,
                    color = Color(0xfffcffff)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    //Le damos el valo de false para que se cierre el diálogo al darle click en el botón.
                    onClick = {
                              //Navegamos atrás
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

@Composable
fun errorAnuncioCrear(show: MutableState<Boolean>, isLoading : MutableState<Boolean>) {
    if (show.value) {
        OnecTheme() {
            val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color(0xff3b3d4c)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Error al cargar Anuncios", fontSize = 19.sp, color = Color(0xfffcffff))
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.errorlog),
                    contentDescription = "Error log"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Ha ocurrido un error\nal cargar los anuncios del usuario\nreinténtelo más tarde.", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        isLoading.value = true
                        show.value = false
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
fun loadingAnuncio(show: MutableState<Boolean>){
    if (show.value) {
        OnecTheme() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff3b3d4c))
            ){
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier
                        .height(50.dp)
                        .width(50.dp), color = Color(0xfffcffff)
                    )
                    Text(text = "Cargando...", fontSize = 16.sp, color = Color(0xfffcffff), textAlign = TextAlign.Center)
                }
            }
        }
    }
}