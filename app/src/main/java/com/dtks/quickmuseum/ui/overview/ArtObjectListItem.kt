package com.dtks.quickmuseum.ui.overview

data class ArtObjectListItem(
    val id: String,
    val title: String,
    val creator: String,
    val imageUrl: String?= null
)