package com.dtks.quickmuseum.ui.overview

import android.util.Size

data class ArtObjectListItem(
    val id: String,
    val objectNumber: String,
    val title: String,
    val creator: String,
    val imageUrl: String?= null,
    val imageSize: Size?= null,
)