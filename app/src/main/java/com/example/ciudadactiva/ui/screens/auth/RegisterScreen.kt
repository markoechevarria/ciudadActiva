package com.example.ciudadactiva.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ciudadactiva.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = viewModel(),
    onRegistered: () -> Unit
) {
    val nombres   by authViewModel::nombres
    val apellidos by authViewModel::apellidos
    val dni       by authViewModel::dni
    val pais      by authViewModel::pais
    val ciudad    by authViewModel::ciudad
    val distrito  by authViewModel::distrito
    val email     by authViewModel::registerEmail
    val error     by authViewModel::registerError

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de usuario", fontSize = 20.sp)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(nombres,   authViewModel::onNombresChange,   label = { Text("Nombres") },   modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(apellidos, authViewModel::onApellidosChange, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(dni,       authViewModel::onDniChange,       label = { Text("DNI") },       modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pais,      authViewModel::onPaisChange,      label = { Text("País") },      modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(ciudad,    authViewModel::onCiudadChange,    label = { Text("Ciudad") },    modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(distrito,  authViewModel::onDistritoChange,  label = { Text("Distrito") },  modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(email,     authViewModel::onRegisterEmailChange, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { authViewModel.register(onRegistered) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A))
        ) {
            Text("Registrar", color = Color.White)
        }
    }
}