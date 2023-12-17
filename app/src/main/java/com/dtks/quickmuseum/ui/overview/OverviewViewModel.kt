package com.dtks.quickmuseum.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.quickmuseum.R
import com.dtks.quickmuseum.data.model.CollectionRequest
import com.dtks.quickmuseum.data.repository.OverviewRepository
import com.dtks.quickmuseum.utils.AsyncResource
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    private val currentPage = MutableStateFlow(1)

    private val initialState = overviewRepository.getCollectionFlow(CollectionRequest())
        .map { AsyncResource.Success(it) }
        .catch<AsyncResource<List<ArtObjectListItem>>> {
            emit(AsyncResource.Error(R.string.loading_collection_error))
        }
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val _updateState = MutableStateFlow(listOf<ArtObjectListItem>())
    val combinedUiState =
        combine(
            initialState,
            _updateState,
            _isLoading,
            _userMessage
        ) { initialState, updateState, isLoading, message ->
            when (initialState) {
                is AsyncResource.Loading -> {
                    OverviewUiState(isLoading = true, isEmpty = true)
                }

                is AsyncResource.Error -> {
                    OverviewUiState(userMessage = initialState.errorMessage, isEmpty = true)
                }

                is AsyncResource.Success -> {
                    val items = initialState.data + updateState
                    OverviewUiState(
                        items = items,
                        isLoading = isLoading,
                        isEmpty = items.isEmpty(),
                        userMessage = message
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = OverviewUiState(isLoading = true, isEmpty = true)
        )

    fun snackbarMessageShown() {
        _userMessage.value = null
    }
    fun loadNextPage() {
        _isLoading.value = true

        viewModelScope.launch {
            overviewRepository.loadMoreItemsFlow(
                CollectionRequest(
                    page = currentPage.value + 1
                )
            ).catch {
                _userMessage.value = R.string.loading_collection_error
                _isLoading.value = false
            }
                .onEach { newState ->
                    currentPage.value += 1
                    val uniqueStates = (_updateState.value + newState).toSet().toList()
                    _updateState.value = uniqueStates
                    _isLoading.value = false
                }.collect()
        }
    }
}
