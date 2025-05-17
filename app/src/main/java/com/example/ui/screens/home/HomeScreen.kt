package com.example.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Primary
import com.example.ui.theme.TextPrimary

@Composable
fun HomeScreen(
    onNewReport: () -> Unit,
    onMenu: () -> Unit,
    onDrawerReport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Qué quieres reportar hoy?",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNewReport),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Nuevo Reporte",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
            }
        }
    }
} 