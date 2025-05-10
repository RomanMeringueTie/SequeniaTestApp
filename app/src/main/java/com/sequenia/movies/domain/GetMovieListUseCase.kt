package com.sequenia.movies.domain

import com.sequenia.movies.model.MovieList

interface GetMovieListUseCase {
    suspend operator fun invoke(): Result<MovieList>
}