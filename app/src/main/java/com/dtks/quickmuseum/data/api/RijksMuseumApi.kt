package com.dtks.quickmuseum.data.api

import com.dtks.quickmuseum.data.model.ArtDetailsResponse
import com.dtks.quickmuseum.data.model.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksMuseumApi {

    @GET("{culture}/collection")
    suspend fun getCollection(
        @Path("culture")
        language: String = "en",
        @Query("s")
        searchType: String = "relevance",
        @Query("p")
        page: Int = 0,
        @Query("ps")
        pageSize: Int = 10,
        @Query("key")
        apiKey: String = API_KEY
    ): CollectionResponse
    @GET("{culture}/collection/{artObjectNumber}")
    suspend fun getArtDetails(
        @Path("culture")
        language: String = "en",
        @Path("artObjectNumber")
        artObjectNumber: String,
        @Query("key")
        apiKey: String = API_KEY
    ): ArtDetailsResponse

    companion object {
        private const val API_KEY = "0fiuZFh4"
    }
}