package com.sequenia.movies.presentation

import com.sequenia.movies.model.Genre
import com.sequenia.movies.model.Movie

data class MovieListState(
    val state: State = State.Loading,
    val pickedGenre: Genre? = null,
    val pickedMovies: List<Movie> = emptyList()
)