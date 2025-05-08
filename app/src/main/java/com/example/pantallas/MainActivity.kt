package com.example.pantallas

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.vector.ImageVector


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { CiudadActivaApp() }
    }
}

enum class Screen { Login, Home, ReportCategory, ReportLocation, ReportPhoto, ReportSubmit,
    ReportSuccess, ReportMap, ReportIAInput, ReportIAProcess, ReportIAResult}

data class ReportState(
    var category: String? = null,
    var address: String = "",
    var coords: Pair<Double, Double>? = null,
    var photoUri: Uri? = null,
    var title: String = "",
    var description: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadActivaApp() {
    var screen by remember { mutableStateOf(Screen.Login) }
    var drawerOpen by remember { mutableStateOf(false) }
    val reportState = remember { ReportState() }

    Scaffold(
        topBar = {
            when(screen) {
                Screen.Home         -> HomeTopBar(onMenu = { drawerOpen = true })
                Screen.ReportCategory -> BackTopBar("Selecciona categoría") { screen = Screen.Home }
                Screen.ReportIAInput  -> BackTopBar("IA reconoce")       { screen = Screen.ReportCategory }
                Screen.ReportIAProcess-> BackTopBar("Procesando IA")     { screen = Screen.ReportIAInput }
                Screen.ReportIAResult -> BackTopBar("Resultado IA")      { screen = Screen.ReportIAProcess }
                Screen.ReportLocation -> BackTopBar("Selecciona ubicación") { screen = Screen.ReportIAResult }
                Screen.ReportSubmit   -> BackTopBar("Enviar reporte")    { screen = Screen.ReportLocation }
                Screen.ReportSuccess  -> BackTopBar("¡Listo!")           { screen = Screen.Home }
                Screen.ReportMap      -> BackTopBar("Mapa de reportes")  { screen = Screen.Home; drawerOpen = false }
                else -> {}
            }
        },
        content = { padding ->
            Box(Modifier.padding(padding)) {
                when(screen) {
                    Screen.Login -> LoginScreen { screen = Screen.Home }

                    Screen.Home -> HomeScreen(
                        onNewReport    = { screen = Screen.ReportCategory },
                        onMenu         = { drawerOpen = true },
                        onDrawerReport = { screen = Screen.ReportCategory }
                    )

                    Screen.ReportCategory -> CategoryScreen(
                        onSelect = { cat ->
                            reportState.category = cat
                            if (cat == "Detectar con IA") {
                                screen = Screen.ReportIAInput
                            } else {
                                screen = Screen.ReportLocation
                            }
                        }
                    )

                    Screen.ReportIAProcess -> {
                        LaIADetectaCategoria(reportState.photoUri!!) { cat ->
                            reportState.category = cat
                            screen = Screen.ReportIAResult
                        }
                    }

                    Screen.ReportIAInput -> PhotoScreen { uri ->
                        reportState.photoUri = uri
                        screen = Screen.ReportIAProcess
                    }

                    Screen.ReportIAResult -> IAResultScreen(
                        category = reportState.category!!,
                        onNext   = { screen = Screen.ReportLocation }
                    )

                    Screen.ReportLocation -> LocationScreen(
                        address = reportState.address,
                        coords  = reportState.coords
                    ) { addr, crd ->
                        reportState.address = addr
                        reportState.coords = crd
                        screen = Screen.ReportSubmit
                    }

                    Screen.ReportSubmit -> SubmitScreen(state = reportState) {
                        screen = Screen.ReportSuccess
                    }

                    Screen.ReportSuccess -> SuccessScreen(
                        onDone = { screen = Screen.Home }
                    )

                    Screen.ReportMap -> MapScreen()

                    Screen.ReportPhoto -> TODO()
                }
            }
        }
    )

    if (drawerOpen) {
        AppDrawer(
            onClose  = { drawerOpen = false },
            onLogout = { screen = Screen.Login; drawerOpen = false },
            onReport = { screen = Screen.ReportCategory; drawerOpen = false },
            onMap    = { screen = Screen.ReportMap; drawerOpen = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onMenu: () -> Unit) {
    TopAppBar(
        title = { Text("¡Bienvenido a Ciudad Activa!") },
        navigationIcon = {
            IconButton(onClick = onMenu) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
        },
        colors = smallTopAppBarColors(containerColor = Color.White)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
        },
        colors = smallTopAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

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
                Text("¡Bienvenido a Ciudad Activa!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    isError = showError && email.isBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    isError = showError && password.isBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (showError && (email.isBlank() || password.isBlank())) {
                    Text("¡Completa todos los campos!", color = Color.Red, modifier = Modifier.padding(8.dp))
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { if (email.isNotBlank() && password.isNotBlank()) onLogin() else showError = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))
                ) {
                    Text("Iniciar sesión", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun ReportCard(
    title: String,
    category: String,
    location: String,
    time: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = category, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = location, style = MaterialTheme.typography.labelSmall)
                }
                Text(text = time, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNewReport: () -> Unit, onMenu: () -> Unit, onDrawerReport: () -> Unit) {
    Column {
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                Button(
                    onClick = onNewReport,
                    Modifier.fillMaxWidth().padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))
                ) { Text("Nuevo reporte", color = Color.Black) }
                AsyncImage(
                    model = R.drawable.corazon,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }
            item {
                ReportCard("Bache profundo en avenida", "Bache / Daño en vía", "Av. Arequipa 1234, Lince", "Hace 15 minutos")
                ReportCard("Basura acumulada en parque", "Residuos / Limpieza pública", "Parque Kennedy, Miraflores", "Hace 2 horas")
            }
        }
    }
}


@Composable
fun CategoryScreen(onSelect: (String) -> Unit) {

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selecciona la categoría del reporte", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onSelect("Detectar con IA") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))
        ) {
            Icon(Icons.Default.Memory, contentDescription = "IA")
            Spacer(Modifier.width(8.dp))
            Text("Detectar con IA")
        }

        val cats = listOf(
            "Daño en vía" to R.drawable.basurero,
            "Accidente vehicular" to R.drawable.basurero,
            "Poste dañado" to R.drawable.basurero,
            "Incendio" to R.drawable.basurero,
            "Residuos" to R.drawable.basurero,
            "Otros" to R.drawable.basurero
        )
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "Selecciona la categoría del reporte",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(16.dp))
            for (row in cats.chunked(2)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { (cat, iconRes) ->
                        Card(
                            Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { onSelect(cat) },
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
}

@Composable
fun LocationScreen(
    address: String,
    coords: Pair<Double, Double>?,
    onLocate: (String, Pair<Double, Double>) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.mapa_google),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(300.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = {},
            label = { Text("Buscar aquí") },
            leadingIcon = { Icon(Icons.Default.Map, contentDescription = null) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text("Detalles:", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(16.dp))
        Text("Dirección: $address", modifier = Modifier.padding(horizontal = 16.dp))
        Text("Coordenadas: ${coords?.first}, ${coords?.second}", modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { onLocate(address.ifEmpty { "Av. Ejemplo" }, coords ?: 0.0 to 0.0) },
            Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))
        ) { Text("Continuar") }
    }
}

@Composable
fun PhotoScreen(onPhotoSelected: (Uri) -> Unit) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri -> uri?.let(onPhotoSelected) }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lea con atención los requisitos", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("1. Iluminación adecuada.")
        Text("2. Calidad nítida para reconocer situación.")
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Text("Fotos")
            }
            Button(onClick = { /* aqui podnremos el codigo de la camara */ }) {
                Text("Cámara")
            }
        }
    }
}

@Composable
fun SubmitScreen(state: ReportState, onSubmit: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Nuevo Reporte", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        state.photoUri?.let { uri ->
            AsyncImage(model = uri, contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
        }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(state.title, { state.title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(state.description, { state.description = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Text("Categoría: ${state.category}", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onSubmit, Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7EBD9))) {
            Text("Enviar")
        }
    }
}



@Composable
fun AppDrawer(onClose: () -> Unit, onLogout: () -> Unit, onReport: () -> Unit, onMap: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Color(0x88000000)).clickable { onClose() }) {
        Column(Modifier.width(280.dp).fillMaxHeight().background(Color.White)) {
            // cabecera perfil
            Box(Modifier.fillMaxWidth().background(Color(0xFF8A4F2A), RoundedCornerShape(bottomEnd = 40.dp)).padding(16.dp)) {
                Column {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                    Text("Carlos Augusto M.", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Vecino", color = Color.White)
                }
            }
            Spacer(Modifier.height(24.dp))
            // opciones
            DrawerItem("Reportar incidencia", Icons.Default.ChatBubble) { onReport() }
            DrawerItem("Mis reportes", Icons.Default.List) { /* aqui falta los reportes */ }
            DrawerItem("Ver mapa de reportes", Icons.Default.Map) { onMap() }
            Spacer(Modifier.weight(1f))
            DrawerItem("Cerrar sesión", Icons.Default.ExitToApp) { onLogout() }
        }
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(16.dp))
        Text(text, fontSize = 16.sp)
    }
}

fun LaIADetectaCategoria(uri: Uri, onResult: (String) -> Unit) {
    onResult("Poste dañado")
}

@Composable
fun IAResultScreen(
    category: String,
    onNext: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("La IA reconoció la imagen como:", fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        Text("\"$category\"", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onNext) {
            Text("Siguiente")
        }
    }
}

@Composable
fun MapScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Aquí iría el mapa de reportes", fontSize = 18.sp)
    }
}

@Composable
fun SuccessScreen(onDone: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Su reporte fue enviado con éxito!", fontSize = 20.sp)
        Spacer(Modifier.height(16.dp))
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(100.dp)
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onDone, shape = RoundedCornerShape(24.dp)) {
            Text("Volver al inicio")
        }
    }
}