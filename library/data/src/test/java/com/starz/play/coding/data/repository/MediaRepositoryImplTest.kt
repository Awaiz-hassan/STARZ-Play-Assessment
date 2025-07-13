package com.starz.play.coding.data.repository

import com.starz.play.coding.data.contract.LocalCache
import com.starz.play.coding.data.dataSource.remote.apiService.ApiService
import com.starz.play.coding.data.dataSource.remote.dto.ResultDto
import com.starz.play.coding.data.dataSource.remote.dto.SearchResponseDto
import com.starz.play.coding.data.testUtilz.CoroutineMainDispatcherRule
import com.starz.play.coding.data.util.AppException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MediaRepositoryImplTest {

    @get:Rule
    val dispatcherRule = CoroutineMainDispatcherRule()

    @Mock private lateinit var apiService: ApiService
    @Mock private lateinit var localCache: LocalCache

    private lateinit var repository: MediaRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = MediaRepositoryImpl(apiService, localCache)
    }

    @Test
    fun `search returns SearchResult with Movies category`() = runTest {
        val dto = ResultDto(
            id = 1,
            name = "Iron Man",
            mediaType = "movie",
            overview = "A Marvel superhero movie.",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg"
        )
        val response = Response.success(SearchResponseDto(results = arrayListOf(dto)))

        whenever(apiService.searchMulti("avengers")).thenReturn(response)

        val result = repository.search("avengers")

        assertTrue(result.isSuccess)

        val searchResult = result.getOrThrow()
        val categories = searchResult.categories

        assertEquals(1, categories.size)
        assertEquals("Movies", categories[0].mediaType)

        val item = categories[0].items.first()
        assertEquals(1, item.id)
        assertEquals("Iron Man", item.title)
        assertEquals("movie", item.mediaType)
        assertEquals("A Marvel superhero movie.", item.description)
        assertEquals("https://image.tmdb.org/t/p/w500//poster.jpg", item.imageUrl)
        assertEquals("https://image.tmdb.org/t/p/w500//backdrop.jpg", item.backdropUrl)
    }

    @Test
    fun `search returns SearchResult with multiple categories`() = runTest {
        val results = listOf(
            ResultDto(id = 1, name = "Iron Man", mediaType = "movie"),
            ResultDto(id = 2, name = "Loki", mediaType = "tv"),
            ResultDto(id = 3, name = "Nature Doc", mediaType = "documentary")
        )
        val response = Response.success(SearchResponseDto(results = ArrayList(results)))
        whenever(apiService.searchMulti("mixed")).thenReturn(response)

        val result = repository.search("mixed")

        assertTrue(result.isSuccess)

        val categories = result.getOrThrow().categories
        assertEquals(3, categories.size)
        assertEquals(setOf("Movies", "TV Shows", "Others"), categories.map { it.mediaType }.toSet())
    }

    @Test
    fun `search returns failure on API error`() = runTest {
        val errorResponse = Response.error<SearchResponseDto>(
            500,
            "Server error".toResponseBody("application/json".toMediaType())
        )

        whenever(
            apiService.searchMulti(
                eq("query"),
                eq(false),
                eq("en-US"),
                eq(1)
            )
        ).thenReturn(errorResponse)

        val result = repository.search("query")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AppException)
    }

    @Test
    fun `getLastQuery returns cached query`() = runTest {
        whenever(localCache.getLastQuery()).thenReturn("batman")

        val query = repository.getLastQuery()

        assertEquals("batman", query)
    }

    @Test
    fun `saveLastQuery delegates to LocalCache`() = runTest {
        repository.saveLastQuery("superman")
        verify(localCache).saveLastQuery("superman")
    }
}

