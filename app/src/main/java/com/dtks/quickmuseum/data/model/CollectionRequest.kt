package com.dtks.quickmuseum.data.model

data class CollectionRequest(
    val searchType: String = "relevance",
    val page: Int = 1,
    val pageSize: Int = 50,
    val language: String = "en",
)