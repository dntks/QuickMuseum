package com.dtks.quickmuseum.data.model

data class ArtDetailsRequest(
    val artObjectNumber: String,
    val language: String = "en",
)
