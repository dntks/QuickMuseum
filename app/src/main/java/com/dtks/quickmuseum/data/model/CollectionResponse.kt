package com.dtks.quickmuseum.data.model

data class CollectionResponse(
    val count: Long,
    val artObjects: List<ArtObjectDao>,
)