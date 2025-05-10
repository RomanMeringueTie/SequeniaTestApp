package com.sequenia.movies.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieList(
    @SerialName("films")
    val films: List<Movie>
)

