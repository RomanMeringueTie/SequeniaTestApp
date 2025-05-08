package com.sequenia.movies.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MovieListRoute : Route

    @Serializable
    data class MovieDetailsRoute(val movie: String) : Route
}
