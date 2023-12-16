package com.dtks.quickmuseum.data

import com.dtks.quickmuseum.data.api.RijksMuseumApi
import com.dtks.quickmuseum.data.model.CollectionRequest
import com.dtks.quickmuseum.data.model.CollectionResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val rijksMuseumApi: RijksMuseumApi
) {
    suspend fun getArticles(collectionRequest: CollectionRequest): CollectionResponse {
        return rijksMuseumApi.getCollection(
            searchType = collectionRequest.searchType,
            page = collectionRequest.page,
            pageSize = collectionRequest.pageSize,
            language = collectionRequest.language
        )
    }
}