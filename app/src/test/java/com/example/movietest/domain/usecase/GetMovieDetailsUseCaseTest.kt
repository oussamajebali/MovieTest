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

class GetMovieDetailsUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `invoke should return success with movie details when repository succeeds`() = runTest {
        // Given
        val movieId = "tt0111161"
        val expectedMovie = Movie(
            id = movieId,
            title = "The Shawshank Redemption",
            overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            posterUrl = "https://example.com/poster.jpg",
            backdropUrl = "https://example.com/backdrop.jpg",
            releaseDate = "14 Oct 1994",
            voteAverage = 9.3,
            voteCount = 2500000,
            popularity = 93.0
        )

        whenever(repository.getMovieDetails(movieId)).thenReturn(
            flowOf(Resource.Success(expectedMovie))
        )

        // When
        val result = useCase(movieId).toList()

        // Then
        verify(repository).getMovieDetails(movieId)
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Success)

        val successResult = result[0] as Resource.Success
        assertNotNull(successResult.data)
        assertEquals(movieId, successResult.data?.id)
        assertEquals("The Shawshank Redemption", successResult.data?.title)
        assertEquals(9.3, successResult.data?.voteAverage!!, 0.01)
    }

    @Test
    fun `invoke should return error when repository fails`() = runTest {
        // Given
        val movieId = "tt9999999"
        val errorMessage = "Movie not found"

        whenever(repository.getMovieDetails(movieId)).thenReturn(
            flowOf(Resource.Error(errorMessage))
        )

        // When
        val result = useCase(movieId).toList()

        // Then
        verify(repository).getMovieDetails(movieId)
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals(errorMessage, (result[0] as Resource.Error).message)
    }

    @Test
    fun `invoke should emit loading state from repository`() = runTest {
        // Given
        val movieId = "tt0068646"
        whenever(repository.getMovieDetails(movieId)).thenReturn(
            flowOf(Resource.Loading())
        )

        // When
        val result = useCase(movieId).toList()

        // Then
        verify(repository).getMovieDetails(movieId)
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Loading)
    }

    @Test
    fun `invoke should handle different movie IDs correctly`() = runTest {
        // Given
        val movieId1 = "tt0111161"
        val movieId2 = "tt0068646"

        val movie1 = Movie(movieId1, "Movie 1", "Overview 1", null, null, "1994", 9.3, 1000, 93.0)
        val movie2 = Movie(movieId2, "Movie 2", "Overview 2", null, null, "1972", 9.2, 900, 92.0)

        whenever(repository.getMovieDetails(movieId1)).thenReturn(flowOf(Resource.Success(movie1)))
        whenever(repository.getMovieDetails(movieId2)).thenReturn(flowOf(Resource.Success(movie2)))

        // When
        val result1 = useCase(movieId1).toList()
        val result2 = useCase(movieId2).toList()

        // Then
        verify(repository).getMovieDetails(movieId1)
        verify(repository).getMovieDetails(movieId2)

        assertTrue(result1[0] is Resource.Success)
        assertTrue(result2[0] is Resource.Success)

        assertEquals(movieId1, (result1[0] as Resource.Success).data?.id)
        assertEquals(movieId2, (result2[0] as Resource.Success).data?.id)
    }

    @Test
    fun `invoke should emit multiple states in sequence`() = runTest {
        // Given
        val movieId = "tt1234567"
        val movie = Movie(
            id = movieId,
            title = "Test Movie",
            overview = "A test movie overview",
            posterUrl = null,
            backdropUrl = null,
            releaseDate = "01 Jan 2024",
            voteAverage = 8.5,
            voteCount = 5000,
            popularity = 85.0
        )

        whenever(repository.getMovieDetails(movieId)).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Success(movie)
            )
        )

        // When
        val result = useCase(movieId).toList()

        // Then
        verify(repository).getMovieDetails(movieId)
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(movie, (result[1] as Resource.Success).data)
    }

    @Test
    fun `invoke should handle error with partial data`() = runTest {
        // Given
        val movieId = "tt7777777"
        val errorMessage = "Incomplete data received"
        val partialMovie = Movie(movieId, "Partial", "", null, null, "", 0.0, 0, 0.0)

        whenever(repository.getMovieDetails(movieId)).thenReturn(
            flowOf(Resource.Error(errorMessage, partialMovie))
        )

        // When
        val result = useCase(movieId).toList()

        // Then
        verify(repository).getMovieDetails(movieId)
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)

        val errorResult = result[0] as Resource.Error
        assertEquals(errorMessage, errorResult.message)
        assertEquals(partialMovie, errorResult.data)
    }
}