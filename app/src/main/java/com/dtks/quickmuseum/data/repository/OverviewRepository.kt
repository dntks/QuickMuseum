package com.dtks.quickmuseum.data.repository

import com.dtks.quickmuseum.data.RemoteDataSource
import com.dtks.quickmuseum.data.model.CollectionRequest
import com.dtks.quickmuseum.di.DefaultDispatcher
import com.dtks.quickmuseum.ui.overview.ArtObjectListItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class OverviewRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
){
    suspend fun getOverviewArtObjects(collectionRequest: CollectionRequest): List<ArtObjectListItem> {
        return withContext(dispatcher) {
            remoteDataSource.getCollection(collectionRequest).artObjects.map {
                ArtObjectListItem(
                    id = it.id,
                    title = it.title,
                    creator = it.principalOrFirstMaker,
                    imageUrl = it.webImage?.url,
                    objectNumber = it.objectNumber
                )
            }
        }
    }

    fun getCollectionFlow(collectionRequest: CollectionRequest): Flow<List<ArtObjectListItem>> = flow {
        emit(getOverviewArtObjects(collectionRequest))
    }
}