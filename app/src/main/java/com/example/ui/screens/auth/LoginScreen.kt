package com.example.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.ui.components.PrimaryButton
import com.example.ui.components.PrimaryTextField
import com.example.ui.components.TextButton
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ciudad Activa",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo electrónico"
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            supportingText = "Mínimo 6 caracteres"
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(
            text = "Iniciar Sesión",
            onClick = onLogin
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            text = "¿No tienes cuenta? Regístrate",
            onClick = onRegister
        )
    }
} 