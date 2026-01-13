package com.example.movietest.presentation.moviedetail

import androidx.lifecycle.SavedStateHandle
import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.usecase.GetMovieDetailsUseCase
import com.example.movietest.domain.util.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: MovieDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    companion object {
        private const val TEST_MOVIE_ID = "tt0111161"
        private const val TEST_MOVIE_TITLE = "The Shawshank Redemption"
        private const val TEST_MOVIE_OVERVIEW = "Two imprisoned men bond over a number of years"
        private const val TEST_RELEASE_DATE = "14 Oct 1994"
        private const val TEST_VOTE_AVERAGE = 9.3
        private const val TEST_VOTE_COUNT = 2500000
        private const val TEST_POPULARITY = 93.0
        private const val ERROR_MESSAGE = "Movie not found"
        private const val UNKNOWN_ERROR_MESSAGE = "Une erreur inconnue est survenue"
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMovieDetailsUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load movie details successfully when movieId is provided`() = runTest {
        // Given
        val expectedMovie = createTestMovie()
        savedStateHandle = SavedStateHandle(mapOf("movieId" to TEST_MOVIE_ID))

        whenever(getMovieDetailsUseCase(TEST_MOVIE_ID)).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Success(expectedMovie)
            )
        )

        // When
        viewModel = MovieDetailViewModel(getMovieDetailsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Then
        verify(getMovieDetailsUseCase).invoke(TEST_MOVIE_ID)
        val state = viewModel.state.value

        assertEquals(false, state.isLoading)
        assertNotNull(state.movie)
        assertEquals(TEST_MOVIE_ID, state.movie?.id)
        assertEquals(TEST_MOVIE_TITLE, state.movie?.title)
        assertEquals(TEST_VOTE_AVERAGE, state.movie?.voteAverage!!, 0.01)
        assertEquals("", state.error)
    }

    @Test
    fun `init should handle error when loading movie details fails`() = runTest {
        // Given
        savedStateHandle = SavedStateHandle(mapOf("movieId" to TEST_MOVIE_ID))

        whenever(getMovieDetailsUseCase(TEST_MOVIE_ID)).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Error(ERROR_MESSAGE)
            )
        )

        // When
        viewModel = MovieDetailViewModel(getMovieDetailsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Then
        verify(getMovieDetailsUseCase).invoke(TEST_MOVIE_ID)
        val state = viewModel.state.value

        assertEquals(false, state.isLoading)
        assertNull(state.movie)
        assertEquals(ERROR_MESSAGE, state.error)
    }

    @Test
    fun `init should show loading state initially`() = runTest {
        // Given
        savedStateHandle = SavedStateHandle(mapOf("movieId" to TEST_MOVIE_ID))

        whenever(getMovieDetailsUseCase(TEST_MOVIE_ID)).thenReturn(
            flowOf(Resource.Loading())
        )

        // When
        viewModel = MovieDetailViewModel(getMovieDetailsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Then
        verify(getMovieDetailsUseCase).invoke(TEST_MOVIE_ID)
        val state = viewModel.state.value

        assertEquals(true, state.isLoading)
        assertNull(state.movie)
        assertEquals("", state.error)
    }

    @Test
    fun `init should not load movie when movieId is not provided`() = runTest {
        // Given
        savedStateHandle = SavedStateHandle()

        // When
        viewModel = MovieDetailViewModel(getMovieDetailsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value

        assertEquals(false, state.isLoading)
        assertNull(state.movie)
        assertEquals("", state.error)
    }

    @Test
    fun `state should preserve movie data structure correctly`() = runTest {
        // Given
        val movie = Movie(
            id = TEST_MOVIE_ID,
            title = TEST_MOVIE_TITLE,
            overview = TEST_MOVIE_OVERVIEW,
            posterUrl = "https://example.com/poster.jpg",
            backdropUrl = "https://example.com/backdrop.jpg",
            releaseDate = TEST_RELEASE_DATE,
            voteAverage = TEST_VOTE_AVERAGE,
            voteCount = TEST_VOTE_COUNT,
            popularity = TEST_POPULARITY
        )

        savedStateHandle = SavedStateHandle(mapOf("movieId" to TEST_MOVIE_ID))
        whenever(getMovieDetailsUseCase(TEST_MOVIE_ID)).thenReturn(
            flowOf(Resource.Success(movie))
        )

        // When
        viewModel = MovieDetailViewModel(getMovieDetailsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        val resultMovie = state.movie

        assertNotNull(resultMovie)
        assertEquals(movie.id, resultMovie?.id)
        assertEquals(movie.title, resultMovie?.title)
        assertEquals(movie.overview, resultMovie?.overview)
        assertEquals(movie.posterUrl, resultMovie?.posterUrl)
        assertEquals(movie.backdropUrl, resultMovie?.backdropUrl)
        assertEquals(movie.releaseDate, resultMovie?.releaseDate)
        assertEquals(movie.voteAverage, resultMovie?.voteAverage!!, 0.01)
        assertEquals(movie.voteCount, resultMovie?.voteCount)
        assertEquals(movie.popularity, resultMovie?.popularity!!, 0.01)
    }

    private fun createTestMovie() = Movie(
        id = TEST_MOVIE_ID,
        title = TEST_MOVIE_TITLE,
        overview = TEST_MOVIE_OVERVIEW,
        posterUrl = "https://example.com/poster.jpg",
        backdropUrl = "https://example.com/backdrop.jpg",
        releaseDate = TEST_RELEASE_DATE,
        voteAverage = TEST_VOTE_AVERAGE,
        voteCount = TEST_VOTE_COUNT,
        popularity = TEST_POPULARITY
    )
}