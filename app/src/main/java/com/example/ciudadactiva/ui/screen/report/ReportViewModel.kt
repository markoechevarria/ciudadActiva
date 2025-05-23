package com.example.ciudadactiva.ui.screen.report

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ciudadactiva.data.model.ReportState
import com.example.ciudadactiva.data.repository.ReportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(
    private val repository: ReportRepository
) : ViewModel() {

    // Estados de UI
    var category by mutableStateOf<String?>(null); private set
    var iaFlow by mutableStateOf(false); private set
    var photoUri by mutableStateOf<Uri?>(null); private set
    var iaResult by mutableStateOf<String?>(null); private set
    var address by mutableStateOf(""); private set
    var coords by mutableStateOf<Pair<Double, Double>?>(null); private set
    var title by mutableStateOf(""); private set
    var description by mutableStateOf(""); private set

    // Selección de categoría
    fun selectCategory(cat: String) {
        category = cat
        iaFlow = cat == "Detectar con IA"
    }

    // Flujo IA: detecta categoría desde foto
    fun detectWithIA(onDetected: () -> Unit) {
        photoUri?.let { uri ->
            LaIADetectaCategoria(uri) { result ->
                iaResult = result
                onDetected()
            }
        }
    }

    private fun LaIADetectaCategoria(uri: Uri, onResult: (String) -> Unit) {
        val result = "Aqui se simulara"
        onResult(result)
    }

    // Confirma resultado IA
    fun confirmIAResult() {
        iaResult?.let { category = it }
    }

    // Foto
    fun onPhotoPicked(uri: Uri) {
        photoUri = uri
    }

    // Ubicación
    fun onAddressChanged(v: String) {
        address = v
    }
    fun onCoordsSelected(pair: Pair<Double, Double>) {
        coords = pair
    }

    // Texto final
    fun onTitleChanged(v: String) {
        title = v
    }
    fun onDescriptionChanged(v: String) {
        description = v
    }

    // Envío
    fun submit(onSuccess: () -> Unit) {
        // Construye el estado
        val state = ReportState(
            category   = category,
            iaFlow     = iaFlow,
            photoUri   = photoUri,
            address    = address,
            coords     = coords,
            title      = title,
            description= description
        )
        CoroutineScope(Dispatchers.IO).launch {
            repository.addReport(state)
            onSuccess()
        }
    }
}
