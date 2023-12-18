package com.dtks.quickmuseum.ui.overview

import com.dtks.quickmuseum.data.model.CollectionRequest
import com.dtks.quickmuseum.data.repository.OverviewRepository
import com.dtks.quickmuseum.data.repository.defaultArtObjectListItems
import com.dtks.quickmuseum.data.repository.secondaryArtObjectListItems
import com.dtks.quickmuseum.ui.MainCoroutineRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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
class OverviewViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var overviewViewModel: OverviewViewModel
    val repository: OverviewRepository = mockk()

    @Before
    fun setup() {
        every { repository.getCollectionFlow(any()) } returns flow {
            emit(defaultArtObjectListItems)
        } andThen flow {
            emit(secondaryArtObjectListItems)
        }
        overviewViewModel = OverviewViewModel(
            repository
        )
    }

    @Test
    fun testInitialStateIsLoaded() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val uiState = overviewViewModel.combinedUiState
        assertTrue(uiState.first().isLoading)
        advanceUntilIdle()
        assertFalse(uiState.first().isLoading)
        assertEquals(uiState.first().items, defaultArtObjectListItems)
    }

    @Test
    fun testLoadNextPage() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val uiState = overviewViewModel.combinedUiState

        advanceUntilIdle()
        overviewViewModel.loadNextPage()

        assertTrue(uiState.first().isLoading)
        advanceUntilIdle()
        assertFalse(uiState.first().isLoading)
        assertEquals(uiState.first().items, defaultArtObjectListItems+secondaryArtObjectListItems)
        coVerify {
            repository.getCollectionFlow(
                CollectionRequest(
                    page = 2
                )
            )
        }
    }
}