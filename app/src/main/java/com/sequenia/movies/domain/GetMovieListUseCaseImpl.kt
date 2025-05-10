package com.sequenia.movies.domain

import com.sequenia.movies.model.MovieList
import com.sequenia.movies.network.RetrofitClient.MoviesApi

class GetMovieListUseCaseImpl(private val moviesApi: MoviesApi) : GetMovieListUseCase {
    override suspend operator fun invoke(): Result<MovieList> {
        try {
            val result = moviesApi.getAll()
            val films = result.films.sortedBy { it.localizedName }
            return Result.success(result.copy(films = films))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}