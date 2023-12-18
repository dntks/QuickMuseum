package com.dtks.quickmuseum.ui.details

import androidx.lifecycle.SavedStateHandle
import com.dtks.quickmuseum.data.model.ArtDetailsRequest
import com.dtks.quickmuseum.data.repository.ArtDetailsRepository
import com.dtks.quickmuseum.data.repository.defaultArtDetails
import com.dtks.quickmuseum.ui.MainCoroutineRule
import com.dtks.quickmuseum.ui.navigation.QuickMuseumDestinationsArgs
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtDetailViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var artDetailViewModel: ArtDetailViewModel
    val repository: ArtDetailsRepository = mockk()

    @Before
    fun setup() {
        every { repository.getArtDetailsFlow(any()) } returns flow {
            emit(defaultArtDetails)
        }
        artDetailViewModel = ArtDetailViewModel(
            repository,
            SavedStateHandle(mapOf(QuickMuseumDestinationsArgs.ART_ID_ARG to "artId2"))
        )
    }

    @Test
    fun testInitialStateIsLoaded() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val uiState = artDetailViewModel.uiState
        TestCase.assertTrue(uiState.first().isLoading)
        advanceUntilIdle()
        TestCase.assertFalse(uiState.first().isLoading)
        TestCase.assertEquals(uiState.first().artDetails, defaultArtDetails)
        coVerify {
            repository.getArtDetailsFlow(
                ArtDetailsRequest(
                    artObjectNumber = "artId2"
                )
            )
        }
    }

}