package com.example.ciudadactiva.ui.screens.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ciudadactiva.viewmodel.ReportViewModel

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
