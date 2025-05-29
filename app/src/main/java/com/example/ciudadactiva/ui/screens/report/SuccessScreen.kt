package com.example.ciudadactiva.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
// import androidx.compose.material3.icons.Icons
// import androidx.compose.material3.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*

@Composable
fun SuccessScreen(onDone: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Su reporte fue enviado con éxito!", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(24.dp))
        Button(onClick = onDone) {
            Text("Volver al inicio")
        }
    }
}
