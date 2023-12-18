package com.dtks.quickmuseum.ui.details

data class ArtDetailsUiState(
    val artDetails: ArtDetails? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)