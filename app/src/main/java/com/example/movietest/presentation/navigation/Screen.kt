package com.example.movietest.presentation.navigation

import com.example.movietest.util.Constants

sealed class Screen(val route: String) {
    data object MovieList : Screen(Constants.ROUTE_MOVIE_LIST)
    data object MovieDetail : Screen(Constants.ROUTE_MOVIE_DETAIL) {
        fun createRoute(movieId: String) = "movie_detail/$movieId"
    }
}