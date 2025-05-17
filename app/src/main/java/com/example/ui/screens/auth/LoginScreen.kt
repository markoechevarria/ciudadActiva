package com.example.ui.screens.auth

import com.example.pantallas.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.components.PrimaryButton
import com.example.ui.components.PrimaryTextField
import com.example.ui.components.TextButton
import com.example.ui.theme.Primary
import com.example.ui.theme.TextPrimary

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Fondo general claro
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen superior (logo + ciudad)
        Image(
            painter = painterResource(id = R.drawable.ciudad),
            contentDescription = "Logo Ciudad Activa",
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card con formulario
        Card(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .offset(y = (-60).dp), // Usar offset en vez de padding negativo
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Bienvenido a Ciudad Activa!",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo"
                )
                Spacer(modifier = Modifier.height(12.dp))
                PrimaryTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña"
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFF3E0), // Color similar al de la imagen
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Iniciar sesión")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de registro y aclaraciones
        TextButton(
            text = "¿No tienes cuenta? Regístrate",
            onClick = onRegister
        )
        Text(
            text = "Al iniciar sesión aceptas los Términos y Condiciones y la Política de Privacidad.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
} 