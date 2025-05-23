package com.example.ciudadactiva.data.model

data class ExistingReport(
    val id: Int,
    val title: String,
    val category: String,
    val location: String,
    val time: String,
    val description: String,
    val imageRes: Int
)
