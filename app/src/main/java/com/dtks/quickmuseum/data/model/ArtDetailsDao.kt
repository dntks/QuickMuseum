package com.dtks.quickmuseum.data.model

data class ArtDetailsDao(
    val id: String,
    val objectNumber: String,
    val title: String,
    val principalMakers: List<ArtCreator>,
    val materials: List<String>?,
    val techniques: List<String>?,
    val dating: Dating?,
    val webImage: WebImage?
)
