package com.example.movietest.util

object Constants {
    val POPULAR_SEARCH_TERMS = listOf("action", "avengers", "batman", "star", "love")

    const val MAX_MOVIES_TO_DISPLAY = 20

    const val NETWORK_TIMEOUT_CONNECT = 30L
    const val NETWORK_TIMEOUT_READ = 30L
    const val NETWORK_TIMEOUT_WRITE = 30L

    const val NAV_ARG_MOVIE_ID = "movieId"

    const val ROUTE_MOVIE_LIST = "movie_list"
    const val ROUTE_MOVIE_DETAIL = "movie_detail/{$NAV_ARG_MOVIE_ID}"

    const val POSTER_NOT_AVAILABLE = "N/A"

    const val DEFAULT_RATING = 0.0
    const val DEFAULT_VOTES = 0
    const val DEFAULT_POPULARITY = 0.0
}