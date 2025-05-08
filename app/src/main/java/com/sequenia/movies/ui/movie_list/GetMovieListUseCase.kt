package com.sequenia.movies.ui.movie_list

import com.sequenia.movies.model.MovieList
import com.sequenia.movies.network.HttpClient

class GetMovieListUseCase(private val httpClient: HttpClient) {
    suspend operator fun invoke(): Result<MovieList> {
        try {
            val result = httpClient.getAll()
            val films = result.films.sortedBy { it.localizedName }
            return Result.success(result.copy(films = films))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}