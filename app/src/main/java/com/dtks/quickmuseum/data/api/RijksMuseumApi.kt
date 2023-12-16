package com.dtks.quickmuseum.data.api

import com.dtks.quickmuseum.data.model.ArtDetailsResponse
import com.dtks.quickmuseum.data.model.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Path
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
//        @Path("culture")
//        language: String = "nl",
        @Query("key")
        apiKey: String = API_KEY
    ): CollectionResponse
    @GET("collection/{artObjectNumber}")
    suspend fun getArtDetails(
        @Path("artObjectNumber")
        artObjectNumber: String,
        @Query("key")
        apiKey: String = API_KEY
    ): ArtDetailsResponse

    companion object {
        private const val API_KEY = "0fiuZFh4"
    }
}