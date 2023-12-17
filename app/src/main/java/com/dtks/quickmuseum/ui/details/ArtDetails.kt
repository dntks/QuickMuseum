package com.dtks.quickmuseum.ui.details

import android.util.Size

data class ArtDetails(
    val id: String,
    val objectNumber: String,
    val title: String,
    val creators: List<String>,
    val materials: List<String>?,
    val techniques: List<String>?,
    val dating: String?,
    val imageUrl: String? = null,
    val imageSize: Size?
)