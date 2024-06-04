package com.news.launchpad

import com.news.domain.headlines.FakeHeadlinesRepository
import com.news.domain.headlines.mockSources
import com.news.util.test.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class LaunchpadViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LaunchpadViewModel

    private lateinit var fakeHeadlinesRepository: FakeHeadlinesRepository


    @Before
    fun setup() {
        fakeHeadlinesRepository = FakeHeadlinesRepository()
        viewModel = LaunchpadViewModel(
            headlinesRepository = fakeHeadlinesRepository
        )
    }

    @Test
    fun `sources emits empty list when fail`() = runTest {
        fakeHeadlinesRepository.setShouldFail(true)

        // Act: Initialize the ViewModel, triggering the repository call indirectly
        viewModel = LaunchpadViewModel(
            headlinesRepository = fakeHeadlinesRepository
        )

        val resultState = viewModel.sources.value
        assertEquals(resultState, emptyList())
    }


    @Test
    fun `sources emits list when success`() = runTest {
        val resultState = viewModel.sources.value
        assertEquals(resultState, mockSources)
    }

}
