package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AppDrawer(
    onClose: () -> Unit,
    onLogout: () -> Unit,
    onReport: () -> Unit,
    onMap: () -> Unit,
    onMisReports: () -> Unit,
    onHome: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = SurfaceLight
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            "Menú",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary
        )
        Divider(color = TextSecondary.copy(alpha = 0.12f))
        DrawerItem(
            icon = Icons.Default.Home,
            label = "Inicio",
            onClick = { onHome(); onClose() }
        )
        DrawerItem(
            icon = Icons.Default.Add,
            label = "Nuevo Reporte",
            onClick = { onReport(); onClose() }
        )
        DrawerItem(
            icon = Icons.Default.Map,
            label = "Mapa",
            onClick = { onMap(); onClose() }
        )
        DrawerItem(
            icon = Icons.Default.List,
            label = "Mis Reportes",
            onClick = { onMisReports(); onClose() }
        )
        Spacer(Modifier.weight(1f))
        Divider(color = TextSecondary.copy(alpha = 0.12f))
        DrawerItem(
            icon = Icons.Default.ExitToApp,
            label = "Cerrar Sesión",
            onClick = { onLogout(); onClose() }
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = TextPrimary
            )
            Text(
                label,
                color = TextPrimary
            )
        }
    }
} 