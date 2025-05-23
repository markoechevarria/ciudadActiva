package com.example.ciudadactiva.data.repository

import com.example.ciudadactiva.data.model.ExistingReport
import com.example.ciudadactiva.data.model.ReportState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
   Define operaciones de lectura/escritura de reportes.
   Permite intercambiar implementaciones (base de datos, red, etc.).
 */
interface ReportRepository {
    /** Flujo de todos los reportes guardados */
    fun observeReports(): Flow<List<ExistingReport>>

    /** Agrega un nuevo reporte (convertido desde ReportState) */
    suspend fun addReport(state: ReportState)

    /** Elimina un reporte existente por su ID */
    suspend fun deleteReport(reportId: Int)
}

/**
 * Implementación en memoria para desarrollo o testing.
 */
class InMemoryReportRepository : ReportRepository {
    private val _reports = MutableStateFlow<List<ExistingReport>>(emptyList())
    private var nextId = 1

    override fun observeReports(): Flow<List<ExistingReport>> = _reports

    override suspend fun addReport(state: ReportState) {
        val new = ExistingReport(
            id          = nextId++,
            title       = state.title,
            category    = state.category.orEmpty(),
            location    = state.address,
            time        = "Ahora",            // podrías formatear con Timestamp real
            description = state.description,
            imageRes    = state.photoUri?.let { /* si fuese recurso local */ 0 } ?: 0
        )
        _reports.value = _reports.value + new
    }

    override suspend fun deleteReport(reportId: Int) {
        _reports.value = _reports.value.filterNot { it.id == reportId }
    }
}
