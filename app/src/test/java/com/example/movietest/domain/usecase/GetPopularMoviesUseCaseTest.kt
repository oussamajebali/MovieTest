package com.example.movietest.domain.usecase

import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.repository.MovieRepository
import com.example.movietest.domain.util.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetPopularMoviesUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `invoke should return success with list of movies when repository succeeds`() = runTest {
        // Given
        val expectedMovies = listOf(
            Movie(
                id = "tt0111161",
                title = "The Shawshank Redemption",
                overview = "Two imprisoned men bond over a number of years",
                posterUrl = "https://example.com/poster1.jpg",
                backdropUrl = "https://example.com/backdrop1.jpg",
                releaseDate = "1994",
                voteAverage = 9.3,
                voteCount = 2500000,
                popularity = 93.0
            ),
            Movie(
                id = "tt0068646",
                title = "The Godfather",
                overview = "The aging patriarch of an organized crime dynasty",
                posterUrl = "https://example.com/poster2.jpg",
                backdropUrl = "https://example.com/backdrop2.jpg",
                releaseDate = "1972",
                voteAverage = 9.2,
                voteCount = 1800000,
                popularity = 92.0
            )
        )

        whenever(repository.getPopularMovies()).thenReturn(
            flowOf(Resource.Success(expectedMovies))
        )

        // When
        val result = useCase().toList()

        // Then
        verify(repository).getPopularMovies()
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Success)

        val successResult = result[0] as Resource.Success
        assertNotNull(successResult.data)
        assertEquals(2, successResult.data?.size)
        assertEquals("The Shawshank Redemption", successResult.data?.get(0)?.title)
        assertEquals("tt0111161", successResult.data?.get(0)?.id)
    }

    @Test
    fun `invoke should return error when repository fails`() = runTest {
        // Given
        val errorMessage = "Network connection failed"
        whenever(repository.getPopularMovies()).thenReturn(
            flowOf(Resource.Error(errorMessage))
        )

        // When
        val result = useCase().toList()

        // Then
        verify(repository).getPopularMovies()
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals(errorMessage, (result[0] as Resource.Error).message)
    }

    @Test
    fun `invoke should emit loading state from repository`() = runTest {
        // Given
        whenever(repository.getPopularMovies()).thenReturn(
            flowOf(Resource.Loading())
        )

        // When
        val result = useCase().toList()

        // Then
        verify(repository).getPopularMovies()
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Loading)
    }

    @Test
    fun `invoke should return empty list when repository returns success with empty data`() = runTest {
        // Given
        val emptyMoviesList = emptyList<Movie>()
        whenever(repository.getPopularMovies()).thenReturn(
            flowOf(Resource.Success(emptyMoviesList))
        )

        // When
        val result = useCase().toList()

        // Then
        verify(repository).getPopularMovies()
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Success)

        val successResult = result[0] as Resource.Success
        assertNotNull(successResult.data)
        assertTrue(successResult.data!!.isEmpty())
    }

    @Test
    fun `invoke should emit multiple states in sequence`() = runTest {
        // Given
        val movies = listOf(
            Movie("tt1234567", "Test Movie", "Overview", null, null, "2024", 7.5, 1000, 75.0)
        )

        whenever(repository.getPopularMovies()).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Success(movies)
            )
        )

        // When
        val result = useCase().toList()

        // Then
        verify(repository).getPopularMovies()
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(movies, (result[1] as Resource.Success).data)
    }
}