package com.example.movietest.data.mapper

import com.example.movietest.data.remote.MovieDetailDto
import com.example.movietest.data.remote.MovieSearchDto
import com.example.movietest.domain.model.Movie
import com.example.movietest.util.Constants

fun MovieSearchDto.toDomain(): Movie {
    return Movie(
        id = imdbId,
        title = title,
        overview = "Année: $year",
        posterUrl = if (poster != Constants.POSTER_NOT_AVAILABLE) poster else null,
        backdropUrl = if (poster != Constants.POSTER_NOT_AVAILABLE) poster else null,
        releaseDate = year,
        voteAverage = Constants.DEFAULT_RATING,
        voteCount = Constants.DEFAULT_VOTES,
        popularity = Constants.DEFAULT_POPULARITY
    )
}

fun MovieDetailDto.toDomain(): Movie {
    val rating = try {
        imdbRating.toDoubleOrNull() ?: Constants.DEFAULT_RATING
    } catch (e: Exception) {
        Constants.DEFAULT_RATING
    }

    val votes = try {
        imdbVotes.replace(",", "").toIntOrNull() ?: Constants.DEFAULT_VOTES
    } catch (e: Exception) {
        Constants.DEFAULT_VOTES
    }

    return Movie(
        id = imdbId,
        title = title,
        overview = plot,
        posterUrl = if (poster != Constants.POSTER_NOT_AVAILABLE) poster else null,
        backdropUrl = if (poster != Constants.POSTER_NOT_AVAILABLE) poster else null,
        releaseDate = released,
        voteAverage = rating,
        voteCount = votes,
        popularity = rating * 10
    )
}