package com.example.movietest.presentation.movielist

import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.usecase.GetPopularMoviesUseCase
import com.example.movietest.domain.util.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var viewModel: MovieListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPopularMoviesUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads movies successfully`() = runTest {
        // Given
        val movies = listOf(
            Movie("tt1234567", "Movie 1", "Overview", null, null, "2024", 7.5, 100, 50.0)
        )
        whenever(getPopularMoviesUseCase()).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Success(movies)
            )
        )

        // When
        viewModel = MovieListViewModel(getPopularMoviesUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(movies, state.movies)
        assertEquals("", state.error)
    }

    @Test
    fun `init handles error correctly`() = runTest {
        // Given
        val errorMessage = "Network error"
        whenever(getPopularMoviesUseCase()).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Error(errorMessage)
            )
        )

        // When
        viewModel = MovieListViewModel(getPopularMoviesUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertTrue(state.movies.isEmpty())
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `loadMovies shows loading state`() = runTest {
        // Given
        whenever(getPopularMoviesUseCase()).thenReturn(
            flowOf(Resource.Loading())
        )

        // When
        viewModel = MovieListViewModel(getPopularMoviesUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(true, state.isLoading)
    }
}
