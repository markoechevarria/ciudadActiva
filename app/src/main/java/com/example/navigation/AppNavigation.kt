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
import com.example.ui.screens.report.ReportCategoryScreen
import com.example.ui.screens.misreports.MisReportesScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    var screen by remember { mutableStateOf(Screen.Login) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val reportState = remember { ReportState() }
    val existingReports = remember { mutableStateListOf<ExistingReport>() }
    var selectedExisting by remember { mutableStateOf<ExistingReport?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onClose      = { scope.launch { drawerState.close() } },
                onLogout     = { screen = Screen.Login; scope.launch { drawerState.close() } },
                onReport     = { screen = Screen.ReportCategory; scope.launch { drawerState.close() } },
                onMap        = { screen = Screen.ReportMap; scope.launch { drawerState.close() } },
                onMisReports = { screen = Screen.MisReports; scope.launch { drawerState.close() } },
                onHome       = { screen = Screen.Home; scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                when(screen) {
                    Screen.Login    -> { /* no le pongan nada aun */ }
                    Screen.Register -> BackTopBar("Registro")     { screen = Screen.Login }
                    Screen.Home     -> HomeTopBar(title = "Home", onMenu = { scope.launch { drawerState.open() } })
                    Screen.ReportCategory -> BackTopBar("Nuevo Reporte") { screen = Screen.Home }
                    Screen.ReportIAInput  -> BackTopBar("IA reconoce")       { screen = Screen.ReportCategory }
                    Screen.ReportIAProcess-> BackTopBar("Procesando IA")     { screen = Screen.ReportIAInput }
                    Screen.ReportIAResult -> BackTopBar("Resultado IA")      { screen = Screen.ReportIAInput }
                    Screen.ReportLocation -> BackTopBar("Selecciona ubicación") {
                        screen = if (reportState.iaFlow) Screen.ReportIAResult else Screen.ReportCategory
                    }
                    Screen.ReportSubmit -> BackTopBar("Enviar reporte") { screen = Screen.ReportPhoto }
                    Screen.ReportSuccess  -> BackTopBar("¡Listo!")           { screen = Screen.Home }
                    Screen.ReportMap -> HomeTopBar(title = "Mapa", onMenu = { scope.launch { drawerState.open() } })
                    Screen.ReportPhoto -> BackTopBar("Subir imágenes") { screen = Screen.ReportLocation }
                    Screen.MisReports -> HomeTopBar(title = "Mis Reportes", onMenu = { scope.launch { drawerState.open() } })
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
                            onMenu         = { scope.launch { drawerState.open() } },
                            onDrawerReport = { screen = Screen.ReportCategory }
                        )
                        Screen.ReportCategory -> ReportCategoryScreen(
                            onBack = { screen = Screen.Home },
                            onCategorySelected = { /* Aquí puedes navegar a la siguiente pantalla según la categoría */ }
                        )
                        Screen.MisReports -> MisReportesScreen()
                        // Aquí irán las demás pantallas
                        else -> {}
                    }
                }
            }
        )
    }
} 