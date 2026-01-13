package com.example.movietest.presentation.moviedetail

import com.example.movietest.domain.model.Movie

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = ""
)
