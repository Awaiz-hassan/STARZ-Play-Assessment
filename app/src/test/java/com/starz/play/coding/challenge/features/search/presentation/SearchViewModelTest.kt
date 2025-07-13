package com.starz.play.coding.challenge.features.search.presentation

import app.cash.turbine.test
import com.starz.play.coding.challenge.testUtilz.CoroutineMainDispatcherRule
import com.starz.play.coding.domain.model.search.SearchResult
import com.starz.play.coding.domain.repository.MediaRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule()

    @Mock
    private lateinit var repository: MediaRepository

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() = runBlocking {
        MockitoAnnotations.openMocks(this@SearchViewModelTest)
        whenever(repository.getLastQuery()).thenReturn("")
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `handleIntent QueryChanged updates query state`() = runTest {
        val searchResult = SearchResult(emptyList())
        whenever(repository.search("batman")).thenReturn(Result.success(searchResult))
        viewModel.handleIntent(SearchIntent.QueryChanged("batman"))
        assertEquals("batman", viewModel.query.value)
    }

    @Test
    fun `handleIntent Clear resets query and sets Idle state`() = runTest {
        viewModel.handleIntent(SearchIntent.QueryChanged("thor"))
        viewModel.handleIntent(SearchIntent.Clear)
        assertEquals("", viewModel.query.value)
        assertEquals(SearchState.Idle, viewModel.searchState.value)
    }

    @Test
    fun `search emits Loading then Success on valid result`() = runTest {
        val searchResult = SearchResult(emptyList())
        whenever(repository.search("loki")).thenReturn(Result.success(searchResult))
        viewModel.handleIntent(SearchIntent.QueryChanged("loki"))
        advanceTimeBy(400)

        viewModel.searchState.test {
            assertEquals(SearchState.Idle, awaitItem())
            assertEquals(SearchState.Loading, awaitItem())
            assertEquals(SearchState.Success(searchResult), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search emits Loading then Error on failure`() = runTest {
        whenever(repository.search("fail")).thenReturn(Result.failure(Exception("Network error")))

        viewModel.handleIntent(SearchIntent.QueryChanged("fail"))
        advanceTimeBy(400)

        viewModel.searchState.test {
            assertEquals(SearchState.Idle, awaitItem())
            assertEquals(SearchState.Loading, awaitItem())
            val error = awaitItem()
            assertTrue(error is SearchState.Error)
            assertEquals("Network error", (error as SearchState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
