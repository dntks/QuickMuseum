package com.dtks.quickmuseum.data.model

data class CollectionRequest(
    val searchType: String = "artist",
    val page: Int = 0,
    val pageSize: Int = 10,
    val language: String = "en",
)