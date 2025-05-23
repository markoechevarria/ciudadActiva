package com.example.ciudadactiva.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ciudadactiva.data.model.ExistingReport
import com.example.ciudadactiva.data.repository.InMemoryReportRepository
import com.example.ciudadactiva.data.repository.ReportRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: ReportRepository = InMemoryReportRepository() // usar DI real en producción
) : ViewModel() {

    val reports: StateFlow<List<ExistingReport>> = repository
        .observeReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun deleteReport(id: Int) {
        repository.deleteReport(id)
    }

    // Para agregar directamente un reporte desde algún test o acción rápida
    suspend fun addDummyReport() {
        repository.addReport(
            com.example.ciudadactiva.data.model.ReportState(
                title = "Fuga de agua",
                category = "Servicios",
                address = "Av. Central 123",
                description = "Fuga de agua en la vereda",
                iaFlow = false
            )
        )
    }
}
