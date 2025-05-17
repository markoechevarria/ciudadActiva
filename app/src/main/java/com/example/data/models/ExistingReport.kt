package com.example.data.models

data class ExistingReport(
    val id: Int,
    val title: String,
    val category: String,
    val location: String,
    val time: String,
    val description: String,
    val imageRes: Int
) 