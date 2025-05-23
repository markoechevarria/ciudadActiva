package com.example.ui.screens.misreports

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ciudadactiva.R

// Modelo de datos de ejemplo
data class Reporte(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val ubicacion: String,
    val fecha: String,
    val imagenRes: Int
)

@Composable
fun MisReportesScreen(
    reportesInicial: List<Reporte> = listOf(
        Reporte(1, "Bache profundo peligroso", "Bache / Daño en vía", "Av. Brasil, Breña", "Hace 1 día", R.drawable.placeholder),
        Reporte(2, "Basura en parque", "Residuos / Limpieza pública", "Parque Santa Cruz, Lurín", "Hace 6 días", R.drawable.placeholder),
        Reporte(3, "Poste de luz caído", "Poste dañado", "Parque Arenas, Punta Hermosa", "Hace 2 semanas", R.drawable.placeholder),
        Reporte(4, "Basura al frente de colegio", "Residuos", "Av. Islas Ballestas, Lurín", "Hace 1 mes", R.drawable.placeholder)
    )
) {
    var reportes by remember { mutableStateOf(reportesInicial) }
    var mostrarEliminado by remember { mutableStateOf(false) }
    var reporteDetalle by remember { mutableStateOf<Reporte?>(null) }

    when {
        mostrarEliminado -> {
            ReporteEliminadoScreen(onContinue = { mostrarEliminado = false })
        }
        reporteDetalle != null -> {
            ReporteDetalleScreen(reporte = reporteDetalle!!)
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Últimos reportes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(reportes, key = { it.id }) { reporte ->
                        MisReportesItem(
                            reporte = reporte,
                            onEliminar = {
                                reportes = reportes.filter { it.id != reporte.id }
                                mostrarEliminado = true
                            },
                            onDetalles = {
                                reporteDetalle = reporte
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MisReportesItem(
    reporte: Reporte,
    onEliminar: () -> Unit,
    onDetalles: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(reporte.titulo, fontWeight = FontWeight.Bold)
                Text(reporte.descripcion, fontSize = 13.sp, color = Color.Gray)
                Text("\uD83D\uDCCD ${reporte.ubicacion}", fontSize = 12.sp, color = Color.Gray)
                Text(reporte.fecha, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = reporte.imagenRes),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            expanded = false
                            onEliminar()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Detalles") },
                        onClick = {
                            expanded = false
                            onDetalles()
                        }
                    )
                }
            }
        }
    }
} 