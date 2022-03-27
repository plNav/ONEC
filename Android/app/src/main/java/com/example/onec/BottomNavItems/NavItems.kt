package com.example.onec.BottomNavItems

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val nombre: String,
    val ruta: String,
    val icono: Painter,
    val contador: Int = 0
)