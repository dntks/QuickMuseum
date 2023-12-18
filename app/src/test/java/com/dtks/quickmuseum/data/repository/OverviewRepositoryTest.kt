package com.dtks.quickmuseum.data.repository

import com.dtks.quickmuseum.data.RemoteDataSource
import com.dtks.quickmuseum.data.model.CollectionRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class OverviewRepositoryTest{

    val remoteDataSourceMock = mockk<RemoteDataSource>()
    private lateinit var repository: OverviewRepository
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)
    @Before
    fun setup() {
        repository = OverviewRepository(
            remoteDataSource = remoteDataSourceMock,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun getOverviewArtObjectsMapsResponseCorrectly()= testScope.runTest{
        val expectedArtObjectListItems = defaultArtObjectListItems
        coEvery {  remoteDataSourceMock.getCollection(any())} returns defaultCollectionResponse
        val artObjects = repository.getOverviewArtObjects(
            CollectionRequest()
        )

        Assert.assertEquals(expectedArtObjectListItems, artObjects)
    }
}