package com.news.data

import JvmUnitTestFakeAssetManager
import androidx.paging.PagingSource
import com.news.data.network.model.headlines.asDomainModel
import com.news.data.network.model.sources.asDomainModel
import com.news.data.network.service.fake.FakeHeadlinesService
import com.news.data.paging.HeadlinesPagingSource
import com.news.data.repository.HeadlinesRepositoryImp
import com.news.util.Resource
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class HeadlinesRepositoryImpTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: HeadlinesRepositoryImp

    private lateinit var fakeHeadlinesService: FakeHeadlinesService
    private lateinit var headlinesPagingSource: HeadlinesPagingSource


    @Before
    fun setup() {

        fakeHeadlinesService = FakeHeadlinesService(
            Json { ignoreUnknownKeys = true },
            JvmUnitTestFakeAssetManager
        )

        subject = HeadlinesRepositoryImp(
            ioDispatcher = UnconfinedTestDispatcher(),
            headlinesService = fakeHeadlinesService
        )

        headlinesPagingSource = HeadlinesPagingSource(
            headlineApi = fakeHeadlinesService, "bbc-news"
        )
    }

    @Test
    fun `getTopHeadlines() handles success`() = testScope.runTest {

        val expectedResult = PagingSource.LoadResult.Page(
            data = fakeHeadlinesService.getTopHeadlines(
                "bbc-news",
                pageSize = 6,
                page = 1
            ).contentHeadlineResponse.asDomainModel(),
            prevKey = -1,
            nextKey = 1
        )
        assertEquals(
            expectedResult, headlinesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )

    }

    @Test
    fun `getSources() returns correct resource`() = testScope.runTest {
        val expectedData =
            fakeHeadlinesService.getSources().sourceItemsResponse
                .map { it.asDomainModel() }

        val resultFlow = subject.getSources()

        val result = resultFlow.first()
        assertTrue(result is Resource.Loading)

        val emittedResult = resultFlow.drop(1).first() // Drop loading emission
        assertEquals(expectedData, (emittedResult as Resource.Success).data)
    }

    @Test
    fun `getSources() handles error`() = testScope.runTest {
        fakeHeadlinesService.setShouldFail(true)

        val resultFlow = subject.getSources().drop(1).first() // Drop loading emission

        assertTrue(resultFlow is Resource.Error)
    }

}

