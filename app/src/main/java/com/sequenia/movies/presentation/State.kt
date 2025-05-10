package com.sequenia.movies.presentation

import com.sequenia.movies.model.MovieList

sealed interface State {
    data object Loading : State
    data class Content(val data: MovieList) : State
    data class Error(val message: String) : State
}