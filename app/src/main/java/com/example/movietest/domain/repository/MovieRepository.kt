package com.example.movietest.domain.repository

import com.example.movietest.domain.model.Movie
import com.example.movietest.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getMovieDetails(movieId: String): Flow<Resource<Movie>>
}