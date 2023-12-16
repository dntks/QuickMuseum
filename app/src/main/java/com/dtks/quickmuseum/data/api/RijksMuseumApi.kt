package com.dtks.quickmuseum.data.api

import com.dtks.quickmuseum.data.model.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RijksMuseumApi {

    @GET("collection")
    suspend fun getCollection(
        @Query("s")
        searchType: String = "artist",
        @Query("p")
        page: Int = 0,
        @Query("ps")
        pageSize: Int = 10,
        @Query("culture")
        language: String = "en",
        @Query("key")
        apiKey: String = API_KEY
    ): CollectionResponse

    companion object {
        private const val API_KEY = "0fiuZFh4"
    }
}