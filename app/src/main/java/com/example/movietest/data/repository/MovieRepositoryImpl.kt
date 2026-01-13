package com.example.movietest.data.repository

import com.example.movietest.BuildConfig
import com.example.movietest.data.api.OmdbApi
import com.example.movietest.data.mapper.toDomain
import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.repository.MovieRepository
import com.example.movietest.util.Constants
import com.example.movietest.util.ErrorMessages
import com.example.movietest.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: OmdbApi
) : MovieRepository {

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())

            val allMovies = mutableSetOf<Movie>()

            Constants.POPULAR_SEARCH_TERMS.forEach { term ->
                try {
                    val response = api.searchMovies(
                        apiKey = BuildConfig.OMDB_API_KEY,
                        searchQuery = term
                    )

                    response.search?.let { movies ->
                        allMovies.addAll(movies.map { it.toDomain() })
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage ?: ErrorMessages.UNKNOWN_ERROR))
                }
            }

            if (allMovies.isEmpty()) {
                emit(Resource.Error(ErrorMessages.NO_MOVIES_FOUND))
            } else {
                emit(Resource.Success(allMovies.take(Constants.MAX_MOVIES_TO_DISPLAY).toList()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ErrorMessages.UNKNOWN_ERROR))
        }
    }

    override fun getMovieDetails(movieId: String): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val movie = api.getMovieDetails(
                apiKey = BuildConfig.OMDB_API_KEY,
                imdbId = movieId
            ).toDomain()
            emit(Resource.Success(movie))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ErrorMessages.UNKNOWN_ERROR))
        }
    }
}