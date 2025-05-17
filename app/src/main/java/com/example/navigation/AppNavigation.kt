package com.example.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.enums.Screen
import com.example.data.models.ExistingReport
import com.example.data.models.ReportState
import com.example.ui.components.AppDrawer
import com.example.ui.components.BackTopBar
import com.example.ui.components.HomeTopBar
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.auth.RegisterScreen
import com.example.ui.screens.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    var screen by remember { mutableStateOf(Screen.Login) }
    var drawerOpen by remember { mutableStateOf(false) }
    val reportState = remember { ReportState() }
    val existingReports = remember { mutableStateListOf<ExistingReport>() }
    var selectedExisting by remember { mutableStateOf<ExistingReport?>(null) }

    Scaffold(
        topBar = {
            when(screen) {
                Screen.Login    -> { /* no le pongan nada aun */ }
                Screen.Register -> BackTopBar("Registro")     { screen = Screen.Login }
                Screen.Home     -> HomeTopBar(onMenu = { drawerOpen = true })
                Screen.ReportCategory -> BackTopBar("Selecciona categoría") { screen = Screen.Home }
                Screen.ReportIAInput  -> BackTopBar("IA reconoce")       { screen = Screen.ReportCategory }
                Screen.ReportIAProcess-> BackTopBar("Procesando IA")     { screen = Screen.ReportIAInput }
                Screen.ReportIAResult -> BackTopBar("Resultado IA")      { screen = Screen.ReportIAInput }
                Screen.ReportLocation -> BackTopBar("Selecciona ubicación") {
                    screen = if (reportState.iaFlow) Screen.ReportIAResult else Screen.ReportCategory
                }
                Screen.ReportSubmit -> BackTopBar("Enviar reporte") { screen = Screen.ReportPhoto }
                Screen.ReportSuccess  -> BackTopBar("¡Listo!")           { screen = Screen.Home }
                Screen.ReportMap -> HomeTopBar(onMenu = { drawerOpen = true })
                Screen.ReportPhoto -> BackTopBar("Subir imágenes") { screen = Screen.ReportLocation }
                Screen.MisReports -> HomeTopBar(onMenu = { drawerOpen = true })
                Screen.MisReportDetails, Screen.MisReportDeleteSuccess ->
                    BackTopBar(
                        if (screen == Screen.MisReportDetails) "Detalles" else "¡Eliminado!"
                    ) { screen = Screen.MisReports }
                else -> {}
            }
        },
        content = { padding ->
            Box(Modifier.padding(padding)) {
                when(screen) {
                    Screen.Login -> LoginScreen(
                        onLogin    = { screen = Screen.Home },
                        onRegister = { screen = Screen.Register }
                    )
                    Screen.Register -> RegisterScreen { screen = Screen.Login }
                    Screen.Home -> HomeScreen(
                        onNewReport    = { screen = Screen.ReportCategory },
                        onMenu         = { drawerOpen = true },
                        onDrawerReport = { screen = Screen.ReportCategory }
                    )
                    // Aquí irán las demás pantallas
                    else -> {}
                }
            }
        }
    )

    if (drawerOpen) {
        AppDrawer(
            onClose      = { drawerOpen = false },
            onLogout     = { screen = Screen.Login; drawerOpen = false },
            onReport     = { screen = Screen.ReportCategory; drawerOpen = false },
            onMap        = { screen = Screen.ReportMap; drawerOpen = false },
            onMisReports = { screen = Screen.MisReports; drawerOpen = false },
            onHome       = { screen = Screen.Home; drawerOpen = false }
        )
    }
} 