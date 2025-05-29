package com.example.ciudadactiva.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ciudadactiva.viewmodel.ReportViewModel

@Composable
fun SubmitScreen(
    viewModel: ReportViewModel = viewModel(),
    onSubmitSuccess: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Nuevo Reporte", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        viewModel.photoUri?.let { uri ->
            AsyncImage(model = uri, contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp))
        }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.title,
            onValueChange = viewModel::onTitleChanged,
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.description,
            onValueChange = viewModel::onDescriptionChanged,
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text("Categoría: ${viewModel.category}", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(24.dp))
        Button(onClick = { viewModel.submit(onSubmitSuccess) }, Modifier.fillMaxWidth()) {
            Text("Enviar")
        }
    }
}
