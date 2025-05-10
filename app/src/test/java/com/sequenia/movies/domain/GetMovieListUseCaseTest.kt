package com.sequenia.movies.domain

import com.sequenia.movies.model.Movie
import com.sequenia.movies.model.MovieList
import com.sequenia.movies.network.RetrofitClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieListUseCaseImplTest {

    private lateinit var moviesApi: RetrofitClient.MoviesApi
    private lateinit var useCase: GetMovieListUseCase

    @Before
    fun setUp() {
        moviesApi = mockk()
        useCase = GetMovieListUseCaseImpl(moviesApi)
    }

    @Test
    fun `invoke should return sorted movie list on success`() = runTest {
        val unsortedMovies = listOf(
            Movie(
                id = 1,
                localizedName = "АБВ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = emptyList()
            ),
            Movie(
                id = 1,
                localizedName = "ЭЮЯ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = emptyList()
            ),
            Movie(
                id = 1,
                localizedName = "ГДЕ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = emptyList()
            )
        )
        val movieList = MovieList(films = unsortedMovies)

        coEvery { moviesApi.getAll() } returns movieList

        val result = useCase()

        assertTrue(result.isSuccess)
        val expected = listOf("АБВ", "ГДЕ", "ЭЮЯ")
        val actual = result.getOrNull()?.films?.map { it.localizedName }
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke should return failure on exception`() = runTest {
        coEvery { moviesApi.getAll() } throws RuntimeException("Network error")

        val result = useCase()

        assertTrue(result.isFailure)
        val expected = "Network error"
        val actual = result.exceptionOrNull()?.message
        assertEquals(expected, actual)
    }
}