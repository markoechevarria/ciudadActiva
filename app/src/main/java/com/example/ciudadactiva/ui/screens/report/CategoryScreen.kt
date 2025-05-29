package com.example.ciudadactiva.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ciudadactiva.R
import com.example.ciudadactiva.viewmodel.ReportViewModel

@Composable
fun CategoryScreen(
    viewModel: ReportViewModel = viewModel(),
    onNext: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selecciona la categoría del reporte", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.selectCategory("Detectar con IA")
                onNext()
            },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))
        ) {
            Icon(Icons.Default.Memory, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Detectar con IA")
        }
        Spacer(Modifier.height(16.dp))
        val cats = listOf(
            "Daño en vía" to R.drawable.basurero,
            "Accidente vehicular" to R.drawable.basurero,
            "Poste dañado" to R.drawable.basurero,
            "Incendio" to R.drawable.basurero,
            "Residuos" to R.drawable.basurero,
            "Otros" to R.drawable.basurero
        )
        for (row in cats.chunked(2)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { (cat, iconRes) ->
                    Card(
                        Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable {
                                viewModel.selectCategory(cat)
                                onNext()
                            },
                        elevation = cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            Modifier.fillMaxSize().padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(cat, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
