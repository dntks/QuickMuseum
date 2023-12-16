package com.dtks.quickmuseum.data.model

data class ArtObjectDao(
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val webImage: WebImage?
)
