package com.example.ui.screens.home

import com.example.pantallas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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