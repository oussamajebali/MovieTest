package com.example.movietest.domain.usecase

import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.repository.MovieRepository
import com.example.movietest.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return repository.getPopularMovies()
    }
}