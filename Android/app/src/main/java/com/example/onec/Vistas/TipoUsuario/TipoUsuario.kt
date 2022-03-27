package com.example.onec.Vistas.TipoUsuario

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onec.Navegacion.Rutas
import com.example.onec.R
import com.example.onec.Soporte.StaticVariables
import com.example.onec.ui.theme.OnecTheme

@Composable
fun tipoUsuario(navController: NavController) {
    OnecTheme() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff3b3d4c)), contentAlignment = Alignment.Center) {
            Column(
                Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.9f)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.26f), contentAlignment = Alignment.BottomCenter) {
                    Image(painter = painterResource(id = R.drawable.onec), contentDescription = "Logo", alignment = Alignment.Center, modifier = Modifier.fillMaxWidth())
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.04f))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)) {
                    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.TopCenter) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Bienvenido a Onec",
                                    color = Color(0xfffcffff),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.W500
                                )
                                Spacer(modifier = Modifier.fillMaxHeight(0.05f))

                                Text(
                                    text = "En que modo desea",
                                    color = Color(0xfffcffff),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.fillMaxHeight(0.02f))
                                Text(
                                    text = "Iniciar la aplicación",
                                    color = Color(0xfffcffff),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.fillMaxHeight(0.35f))
                                Button(onClick = {
                                    StaticVariables.appModo = true // Esto quiere decir que El modo de app es Empresario
                                    navController.navigate(Rutas.Main.route)
                                },Modifier.fillMaxWidth(0.6f), shape = RoundedCornerShape(30.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                                    0xFF2F9234
                                )
                                )) {
                                    Text(text = "Buscador", fontSize = 19.sp, fontWeight = FontWeight.W300, color = Color(0xfffcffff))
                                }
                                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                                Button(onClick = {navController.navigate(Rutas.Main.route)},Modifier.fillMaxWidth(0.6f), shape = RoundedCornerShape(30.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                                    0xFF2E7F9B
                                )
                                )) {
                                    Text(text = "Estándar", fontSize = 19.sp, fontWeight = FontWeight.W300, color = Color(0xfffcffff))
                                }
                            }

                    }

                }
            }
        }
    }
}