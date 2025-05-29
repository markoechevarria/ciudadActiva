package com.example.ciudadactiva.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ciudadactiva.R
import com.example.ciudadactiva.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
/**
    val email by authViewModel::loginEmail
    val password by authViewModel::loginPassword
    val error by authViewModel::loginError
**/

    val email = authViewModel.loginEmail
    val password = authViewModel.loginPassword
    val error = authViewModel.loginError


    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ciudad),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Card(
            Modifier.fillMaxWidth().padding(16.dp),
            elevation = cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(Modifier.padding(24.dp)) {
                Text("¡Bienvenido a Ciudad Activa!", fontSize = 18.sp, color = Color.Black)
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = authViewModel::onLoginEmailChange,
                    label = { Text("Correo") },
                    isError = error != null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = authViewModel::onLoginPasswordChange,
                    label = { Text("Contraseña") },
                    isError = error != null,
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let {
                    Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { authViewModel.login(onLoginSuccess) },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))
                ) {
                    Text("Iniciar sesión", color = Color.Black)
                }
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = onRegisterClick) {
                    Text("¿No tienes cuenta? Regístrate", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
