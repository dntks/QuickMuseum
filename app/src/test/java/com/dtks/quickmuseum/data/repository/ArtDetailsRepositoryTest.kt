package com.dtks.quickmuseum.data.repository

import com.dtks.quickmuseum.data.RemoteDataSource
import com.dtks.quickmuseum.data.model.ArtDetailsRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtDetailsRepositoryTest{

    val remoteDataSourceMock = mockk<RemoteDataSource>()
    private lateinit var repository: ArtDetailsRepository
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)
    @Before
    fun setup() {
        repository = ArtDetailsRepository(
            remoteDataSource = remoteDataSourceMock,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun getArtDetailsMapsResponseCorrectly()= testScope.runTest{
        val expectedArtDetails = defaultArtDetails
        coEvery {  remoteDataSourceMock.getArtDetails(any())} returns defaultArtDetailsResponse
        val request = ArtDetailsRequest(artObjectNumber = "a")
        val artDetails = repository.getArtDetails(
            request
        )

        coVerify { remoteDataSourceMock.getArtDetails(request) }
        Assert.assertEquals(expectedArtDetails, artDetails)
    }
}