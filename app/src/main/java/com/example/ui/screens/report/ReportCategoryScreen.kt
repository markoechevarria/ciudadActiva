package com.example.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pantallas.R

@Composable
fun ReportCategoryScreen(
    onBack: () -> Unit,
    onCategorySelected: (String) -> Unit
) {
    val brown = Color(0xFF8B4C22)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Barra marrón
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brown)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Selecciona la categoría del reporte",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Grid de categorías
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                CategoryButton("Daño en vía", R.drawable.placeholder, onCategorySelected, Modifier.weight(1f))
                CategoryButton("Otros", R.drawable.placeholder, onCategorySelected, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                CategoryButton("Accidente vehicular", R.drawable.placeholder, onCategorySelected, Modifier.weight(1f))
                CategoryButton("Poste dañado", R.drawable.placeholder, onCategorySelected, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                CategoryButton("Incendio", R.drawable.placeholder, onCategorySelected, Modifier.weight(1f))
                CategoryButton("Residuos", R.drawable.placeholder, onCategorySelected, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CategoryButton(label: String, imageRes: Int, onClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick(label) }
    ) {
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
} 