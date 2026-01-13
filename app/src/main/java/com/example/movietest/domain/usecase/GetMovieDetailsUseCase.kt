package com.example.movietest.domain.usecase

import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.repository.MovieRepository
import com.example.movietest.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: String): Flow<Resource<Movie>> {
        return repository.getMovieDetails(movieId)
    }
}