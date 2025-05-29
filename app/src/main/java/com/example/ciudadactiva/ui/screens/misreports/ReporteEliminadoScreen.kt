package com.example.ciudadactiva.ui.screens.misreports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ReporteEliminadoScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Su reporte fue\neliminado con\néxito!",
            color = Color(0xFF8B4C22),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF9C4)),
            contentAlignment = Alignment.Center
        ) {
            // Círculo amarillo con check
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Éxito",
                tint = Color(0xFF8B4C22),
                modifier = Modifier.size(72.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onContinue,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Text("Continuar", color = Color(0xFF8B4C22), fontWeight = FontWeight.Bold)
        }
    }
} 