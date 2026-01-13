package com.example.movietest.data.remote

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search")
    val search: List<MovieSearchDto>?,
    @SerializedName("totalResults")
    val totalResults: String?,
    @SerializedName("Response")
    val response: String
)

data class MovieSearchDto(
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Poster")
    val poster: String
)

data class MovieDetailDto(
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Runtime")
    val runtime: String?,
    @SerializedName("Genre")
    val genre: String?,
    @SerializedName("Director")
    val director: String?,
    @SerializedName("Actors")
    val actors: String?,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @SerializedName("Response")
    val response: String
)