package com.dtks.quickmuseum.data.repository

import com.dtks.quickmuseum.data.RemoteDataSource
import com.dtks.quickmuseum.data.model.ArtDetailsRequest
import com.dtks.quickmuseum.di.DefaultDispatcher
import com.dtks.quickmuseum.ui.details.ArtDetails
import com.dtks.quickmuseum.ui.details.ArtDetailsImage
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class ArtDetailsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getArtDetails(artDetailsRequest: ArtDetailsRequest): ArtDetails {
        return withContext(dispatcher) {
            val artDetailsResponse = remoteDataSource.getArtDetails(artDetailsRequest)
            val artDetails = artDetailsResponse.artObject
            ArtDetails(
                id = artDetails.id,
                objectNumber = artDetails.objectNumber,
                title = artDetails.title,
                creators = artDetails.principalMakers.map {
                    it.name
                },
                materials = artDetails.materials,
                techniques = artDetails.techniques,
                dating = artDetails.dating?.presentingDate,
                image = artDetails.webImage?.let {
                    ArtDetailsImage(
                        url = it.url,
                        width = it.width,
                        height = it.height
                    )
                }
            )
        }
    }

    fun getArtDetailsFlow(artDetailsRequest: ArtDetailsRequest): Flow<ArtDetails> =
        flow {
            emit(getArtDetails(artDetailsRequest))
        }
}