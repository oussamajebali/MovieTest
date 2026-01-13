package com.example.movietest.data.api

import com.example.movietest.data.remote.MovieDetailDto
import com.example.movietest.data.remote.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String = "popular",
        @Query("type") type: String = "movie",
        @Query("page") page: Int = 1
    ): SearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full"
    ): MovieDetailDto
}