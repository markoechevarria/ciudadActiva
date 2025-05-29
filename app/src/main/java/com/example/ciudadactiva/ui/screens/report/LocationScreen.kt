package com.example.ciudadactiva.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ciudadactiva.R
import com.example.ciudadactiva.viewmodel.ReportViewModel

@Composable
fun LocationScreen(
    viewModel: ReportViewModel = viewModel(),
    onNext: () -> Unit
) {
    var localAddress: String by remember { mutableStateOf(viewModel.address) }
    Column(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.mapa_google),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = localAddress,
            onValueChange = { localAddress = it },
            label = { Text("Buscar aquí") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text("Detalles:", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
        Text("Dirección: $localAddress", modifier = Modifier.padding(horizontal = 16.dp))
        Text(
            "Coordenadas: ${viewModel.coords?.first ?: 0.0}, ${viewModel.coords?.second ?: 0.0}",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            viewModel.onAddressChanged(localAddress)
            viewModel.onCoordsSelected(viewModel.coords ?: 0.0 to 0.0)
            onNext()
        }, Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Continuar")
        }
    }
}
