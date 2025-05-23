package com.example.ciudadactiva.data.model

import android.net.Uri

data class ReportState(
    var category: String? = null,
    var iaFlow: Boolean = false,
    var address: String = "",
    var coords: Pair<Double, Double>? = null,
    var photoUri: Uri? = null,
    var title: String = "",
    var description: String = ""
)
