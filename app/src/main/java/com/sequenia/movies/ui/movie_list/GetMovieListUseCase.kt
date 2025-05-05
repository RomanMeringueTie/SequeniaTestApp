package com.sequenia.movies.ui.movie_list

import com.sequenia.movies.model.MovieList
import com.sequenia.movies.network.HttpClient
import kotlinx.coroutines.delay

class GetMovieListUseCase(private val httpClient: HttpClient) {
    suspend operator fun invoke(): Result<MovieList> {
        try {
            val result = httpClient.getAll()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}