package com.example.ciudadactiva.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors
import androidx.compose.runtime.Composable
import com.example.ciudadactiva.ui.theme.Primary
import com.example.ciudadactiva.ui.theme.SurfaceLight
import com.example.ciudadactiva.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(title: String, onMenu: () -> Unit) {
    TopAppBar(
        title = { 
            Text(
                title,
                color = TextPrimary
            ) 
        },
        navigationIcon = {
            IconButton(onClick = onMenu) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Primary
                )
            }
        },
        colors = smallTopAppBarColors(
            containerColor = SurfaceLight,
            navigationIconContentColor = Primary,
            titleContentColor = TextPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { 
            Text(
                title,
                color = TextPrimary
            ) 
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Primary
                )
            }
        },
        colors = smallTopAppBarColors(
            containerColor = SurfaceLight,
            navigationIconContentColor = Primary,
            titleContentColor = TextPrimary
        )
    )
} 