package com.example.movietest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movietest.util.Constants
import com.example.movietest.presentation.moviedetail.MovieDetailScreen
import com.example.movietest.presentation.movielist.MovieListScreen


@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MovieList.route
    ) {
        composable(route = Screen.MovieList.route) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                }
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(
                navArgument(Constants.NAV_ARG_MOVIE_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}