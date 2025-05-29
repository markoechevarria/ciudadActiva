package com.example.ciudadactiva.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ciudadactiva.R
import com.example.ciudadactiva.data.model.ExistingReport
import com.example.ciudadactiva.ui.theme.Primary
import com.example.ciudadactiva.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onNewReport: () -> Unit,
    onMenu: () -> Unit,
    onDrawerReport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Eliminar TopBar (Row con menú y logo)
        // Comenzar directamente con el mensaje de bienvenida
        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de bienvenida
        Text(
            text = "¡Bienvenido a Ciudad Activa!",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Botón grande "Nuevo reporte"
        Button(
            onClick = onNewReport,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFF3E0),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Nuevo reporte", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen corazon.png
        Image(
            painter = painterResource(id = R.drawable.corazon),
            contentDescription = "Amo mi ciudad",
            modifier = Modifier
                .width(180.dp)
                .height(90.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card de últimos reportes
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Últimos reportes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Ejemplo de reporte
                ReportItem(
                    title = "Bache profundo en avenida",
                    subtitle = "Bache / Daño en vía",
                    address = "Av. Arequipa 1234, Lince",
                    time = "Hace 15 minutos",
                    imageRes = R.drawable.placeholder // Cambia por tu imagen real
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReportItem(
                    title = "Basura acumulada en parque",
                    subtitle = "Residuos / Limpieza pública",
                    address = "Parque Kennedy, Miraflores",
                    time = "Hace 2 horas",
                    imageRes = R.drawable.placeholder // Cambia por tu imagen real
                )
            }
        }
    }
}

@Composable
fun ReportItem(
    title: String,
    subtitle: String,
    address: String,
    time: String,
    imageRes: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(end = 8.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(title, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
                Text(address, fontSize = 12.sp, color = Color.Gray)
                Text(time, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisReportsScreen(viewModel: HomeViewModel = viewModel()) {
    val reports by viewModel.reports.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis reportes") })
        }
    ) { padding ->
        if (reports.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes reportes registrados aún.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(reports) { report ->
                    ReportItem(report = report, onDelete = {
                        scope.launch {
                            viewModel.deleteReport(report.id)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun ReportItem(report: ExistingReport, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* navegar a detalles si se desea */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = report.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Categoría: ${report.category}")
            Text(text = "Ubicación: ${report.location}")
            Text(text = "Descripción: ${report.description}", maxLines = 2)
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}