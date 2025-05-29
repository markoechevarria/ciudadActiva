package com.example.ciudadactiva.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ciudadactiva.ui.components.PrimaryButton
import com.example.ciudadactiva.ui.components.PrimaryTextField
import com.example.ciudadactiva.ui.components.SectionTitle
import com.example.ciudadactiva.ui.theme.Primary
import com.example.ciudadactiva.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Logo o imagen
        Card(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Primary.copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título y subtítulo
        SectionTitle(
            text = "Crear Cuenta",
            modifier = Modifier.fillMaxWidth()
        )
        
        Text(
            text = "Únete a Ciudad Activa y ayuda a mejorar tu comunidad",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campos de entrada
        PrimaryTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nombre completo",
            modifier = Modifier.padding(bottom = 16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        )

        PrimaryTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo electrónico",
            modifier = Modifier.padding(bottom = 16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        )

        PrimaryTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            supportingText = "Mínimo 6 caracteres",
            modifier = Modifier.padding(bottom = 16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        )

        PrimaryTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar contraseña",
            supportingText = "Las contraseñas deben coincidir",
            modifier = Modifier.padding(bottom = 24.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        )

        // Botón de registro
        PrimaryButton(
            text = "Crear Cuenta",
            onClick = onBack,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Términos y condiciones
        Text(
            text = "Al registrarte, aceptas nuestros Términos y Condiciones y Política de Privacidad",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
} 