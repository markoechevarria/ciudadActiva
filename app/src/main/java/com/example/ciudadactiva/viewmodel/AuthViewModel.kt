package com.example.ciudadactiva.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AuthViewModel : ViewModel() {

    // — Login state —
    var loginEmail by mutableStateOf("")
        private set
    var loginPassword by mutableStateOf("")
        private set
    var loginError by mutableStateOf<String?>(null)
        private set

    // — Register state —
    var nombres by mutableStateOf("")
        private set
    var apellidos by mutableStateOf("")
        private set
    var dni by mutableStateOf("")
        private set
    var pais by mutableStateOf("")
        private set
    var ciudad by mutableStateOf("")
        private set
    var distrito by mutableStateOf("")
        private set
    var registerEmail by mutableStateOf("")
        private set
    var registerError by mutableStateOf<String?>(null)
        private set

    // — Login handlers —
    fun onLoginEmailChange(value: String) {
        loginEmail = value
    }

    fun onLoginPasswordChange(value: String) {
        loginPassword = value
    }

    fun login(onSuccess: () -> Unit) {
        if (loginEmail.isBlank() || loginPassword.isBlank()) {
            loginError = "Completa todos los campos"
        } else {
            loginError = null
            onSuccess()
        }
    }

    // — Register handlers —
    fun onNombresChange(value: String) { nombres = value }
    fun onApellidosChange(value: String) { apellidos = value }
    fun onDniChange(value: String)       { dni = value }
    fun onPaisChange(value: String)      { pais = value }
    fun onCiudadChange(value: String)    { ciudad = value }
    fun onDistritoChange(value: String)  { distrito = value }
    fun onRegisterEmailChange(value: String) { registerEmail = value }

    fun register(onRegistered: () -> Unit) {
        // Validar que no haya campos vacíos
        if (nombres.isBlank() || apellidos.isBlank() || dni.isBlank()
            || pais.isBlank() || ciudad.isBlank() || distrito.isBlank()
            || registerEmail.isBlank()
        ) {
            registerError = "Completa todos los campos"
        } else {
            registerError = null
            onRegistered()
        }
    }
}