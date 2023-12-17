/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtks.quickmuseum.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtks.quickmuseum.R
import com.dtks.quickmuseum.data.model.ArtDetailsRequest
import com.dtks.quickmuseum.data.repository.ArtDetailsRepository
import com.dtks.quickmuseum.ui.QuickMuseumDestinationsArgs
import com.dtks.quickmuseum.utils.AsyncResource
import com.dtks.quickmuseum.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * UiState for the Details screen.
 */
data class ArtDetailsUiState(
    val artDetails: ArtDetails? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)

/**
 * ViewModel for the Details screen.
 */
@HiltViewModel
class ArtDetailViewModel @Inject constructor(
    artDetailsRepository: ArtDetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val artObjectNumber: String = savedStateHandle[QuickMuseumDestinationsArgs.ART_ID_ARG]!!
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _taskAsync = artDetailsRepository.getArtDetailsFlow(
        ArtDetailsRequest(
            artObjectNumber = artObjectNumber
        )
    )
        .map { handleArtDetails(it) }
        .catch { emit(AsyncResource.Error(R.string.loading_art_error)) }

    val uiState: StateFlow<ArtDetailsUiState> = combine(
        _userMessage, _isLoading, _taskAsync
    ) { userMessage, isLoading, taskAsync ->
        when (taskAsync) {
            AsyncResource.Loading -> {
                ArtDetailsUiState(isLoading = true)
            }

            is AsyncResource.Error -> {
                ArtDetailsUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }

            is AsyncResource.Success -> {
                ArtDetailsUiState(
                    artDetails = taskAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage,
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ArtDetailsUiState(isLoading = true)
        )

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    private fun handleArtDetails(artDetails: ArtDetails?): AsyncResource<ArtDetails?> {
        if (artDetails == null) {
            return AsyncResource.Error(R.string.art_details_not_found)
        }
        return AsyncResource.Success(artDetails)
    }
}
