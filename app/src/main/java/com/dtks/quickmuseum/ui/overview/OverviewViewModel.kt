package com.dtks.quickmuseum.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.quickmuseum.R
import com.dtks.quickmuseum.data.model.CollectionRequest
import com.dtks.quickmuseum.data.repository.OverviewRepository
import com.dtks.quickmuseum.utils.AsyncResource
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class OverviewUiState(
    val userMessage: Int? = null,
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val items: List<ArtObjectListItem> = emptyList(),
)
@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val overviewRepository: OverviewRepository
) : ViewModel() {

    val uiState: StateFlow<OverviewUiState> =
        overviewRepository.getCollectionFlow(CollectionRequest(pageSize = 100))
            .map { AsyncResource.Success(it) }
            .catch<AsyncResource<List<ArtObjectListItem>>> {
                emit(AsyncResource.Error(R.string.loading_collection_error))
            }
            .map { taskAsync -> produceStatisticsUiState(taskAsync) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = OverviewUiState(isLoading = true)
            )
    private fun produceStatisticsUiState(itemsResource: AsyncResource<List<ArtObjectListItem>>) =
        when (itemsResource) {
            is AsyncResource.Loading -> {
                OverviewUiState(isLoading = true, isEmpty = true)
            }
            is AsyncResource.Error -> {
                OverviewUiState(isEmpty = true, isLoading = false, userMessage = itemsResource.errorMessage)
            }
            is AsyncResource.Success -> {
                OverviewUiState(
                    isEmpty = itemsResource.data.isEmpty(),
                    isLoading = false,
                    items = itemsResource.data,
                )
            }
        }
}
