package com.dtks.quickmuseum.ui.overview

data class OverviewUiState(
    val userMessage: Int? = null,
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val items: List<ArtObjectListItem> = emptyList(),
)