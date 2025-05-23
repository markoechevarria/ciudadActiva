package com.example.ciudadactiva.ui.screen.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*

@Composable
fun IAResultScreen(
    viewModel: ReportViewModel = viewModel(),
    onNext: () -> Unit
) {
    val result = viewModel.iaResult ?: "Procesando..."
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (result == "Procesando...") {
            viewModel.detectWithIA { /* una vez detectado, recompose mostrará el resultado */ }
        }
        Text("La IA reconoció la imagen como:", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        Text("\"$result\"", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            viewModel.confirmIAResult()
            onNext()
        }) {
            Text("Siguiente")
        }
    }
}
