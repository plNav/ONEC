package com.example.onec.Vistas.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.Vistas.Main.CV.cvMain
import com.example.onec.Vistas.Main.MainEmpresario.anunciosEmpresario
import kotlinx.coroutines.launch

@Composable
fun main(navController: NavController, elemento: Int){
    val state = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isOfertaEmpresarioSelected = remember {
        mutableStateOf(false)
    }
    val isAnunciosEmpresarioSelected = remember {
        mutableStateOf(false)
    }


    val isOfertaSelected = remember { mutableStateOf(false)}
    val isAnunciosSelected = remember { mutableStateOf(false)}
    val isPerfilSelected = remember { mutableStateOf(false)}
    val isCvSelected = remember {
        mutableStateOf(false)
    }

    when (elemento) {
         1 -> isOfertaSelected.value = true
         2 -> if (!StaticVariables.appModo!!) isAnunciosSelected.value = true else {isAnunciosEmpresarioSelected.value = true}
         3 -> isCvSelected.value = true
         4 -> isPerfilSelected.value = true
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalDrawer(
            drawerContent = { perfil(navController = navController) },
            gesturesEnabled = true,
            drawerState = state,
            drawerShape = cusShape(),
            scrimColor = Color(0x2D000000),
            drawerBackgroundColor = Color(0xFF060609)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = Color(0xFF1B1C29),
                            elevation = 7.dp,
                            modifier = Modifier.fillMaxHeight(0.1f)
                        ) {

                            //Ofertas
                            if (StaticVariables.appModo!!) {
                                BottomNavigationItem(
                                    selected = isOfertaSelected.value,
                                    onClick = {
                                        if (!StaticVariables.appModo!!) {
                                            isOfertaSelected.value = true
                                            isAnunciosSelected.value = false
                                            isPerfilSelected.value = false
                                            isCvSelected.value = false
                                        } else {
                                            isOfertaSelected.value = true
                                            isAnunciosEmpresarioSelected.value = false
                                            isPerfilSelected.value = false
                                        }
                                        StaticVariables.fragmento = 1
                                    },
                                    icon = {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.oferta),
                                                contentDescription = "Ofertas",
                                                modifier = Modifier
                                                    .height(25.dp)
                                                    .width(25.dp)
                                            )
                                            if (isOfertaSelected.value || isOfertaEmpresarioSelected.value) {
                                                Text(
                                                    text = "Ofertas",
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 10.sp,
                                                    fontFamily = FontFamily(Font(R.font.comforta))
                                                )
                                            }
                                        }
                                    },
                                    selectedContentColor = Color(0xFF266E86),
                                    unselectedContentColor = Color(0xfffcffff)
                                )
                            }

                            //Anuncios
                            BottomNavigationItem(
                                selected = if(!StaticVariables.appModo!!)isAnunciosSelected.value else isAnunciosEmpresarioSelected.value,
                                onClick = {
                                    if (!StaticVariables.appModo!!) {
                                        isAnunciosSelected.value = true
                                        isOfertaSelected.value = false
                                        isPerfilSelected.value = false
                                        isCvSelected.value = false
                                    }else {
                                        isAnunciosEmpresarioSelected.value = true
                                        isOfertaSelected.value = false
                                        isPerfilSelected.value = false
                                    }
                                    StaticVariables.fragmento = 2
                                },
                                icon = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.anuncios),
                                            contentDescription = "Anuncios",
                                            modifier = Modifier
                                                .height(25.dp)
                                                .width(25.dp)
                                        )
                                        if (isAnunciosSelected.value || isAnunciosEmpresarioSelected.value) {
                                            Text(
                                                text = "Anuncios",
                                                textAlign = TextAlign.Center,
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily(Font(R.font.comforta))
                                            )
                                        }
                                    }
                                },
                                selectedContentColor = Color(0xFF266E86),
                                unselectedContentColor = Color(0xfffcffff)
                            )

                            //CV  -> Lo iniciamos con un if, ya que este solo se verá si estamos en modo Estándar
                            if (!StaticVariables.appModo!!) {
                                BottomNavigationItem(
                                    selected = isCvSelected.value,
                                    onClick = {
                                        isOfertaSelected.value = false
                                        isAnunciosSelected.value = false
                                        isPerfilSelected.value = false
                                        isCvSelected.value = true
                                        StaticVariables.fragmento = 3
                                    },
                                    icon = {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.cv),
                                                contentDescription = "CV",
                                                modifier = Modifier
                                                    .height(25.dp)
                                                    .width(25.dp)
                                            )
                                            if (isCvSelected.value) {
                                                Text(
                                                    text = "CV",
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 10.sp,
                                                    fontFamily = FontFamily(Font(R.font.comforta))
                                                )
                                            }
                                        }
                                    },
                                    selectedContentColor = Color(0xFF266E86),
                                    unselectedContentColor = Color(0xfffcffff)
                                )
                            }

                            //Perfil
                            BottomNavigationItem(
                                selected = isPerfilSelected.value,
                                onClick = {
                                    //isPerfilSelected.value = true
                                    scope.launch {
                                        state.open()
                                    }
                                },
                                icon = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.perfil),
                                            contentDescription = "Perfil",
                                            modifier = Modifier
                                                .height(25.dp)
                                                .width(25.dp)
                                        )
                                        if (isPerfilSelected.value) {
                                            Text(
                                                text = "Perfil",
                                                textAlign = TextAlign.Center,
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily(Font(R.font.comforta))
                                            )
                                        }
                                    }
                                },
                                selectedContentColor = Color(0xFF266E86),
                                unselectedContentColor = Color(0xfffcffff)
                            )

                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xff3b3d4c))
                            .fillMaxWidth()
                            .fillMaxHeight(0.90f)
                    ) {
                        //perfil(selected = isPerfilSelected)
                        anunciosEmpresario(selected = isAnunciosEmpresarioSelected, navController = navController)
                        ofertaEmpresario(selected = isOfertaSelected, navController = navController)
                        //ofertas(selected = isOfertaSelected, navController =  navController)
                        anuncios(selected = isAnunciosSelected, navController = navController)
                        cvMain(selected = isCvSelected)
                    }

                }
            }

        }
    }
}