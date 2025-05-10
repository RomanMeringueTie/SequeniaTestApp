package com.sequenia.movies.presentation

import com.sequenia.movies.domain.GetMovieListUseCase
import com.sequenia.movies.model.Genre
import com.sequenia.movies.model.Movie
import com.sequenia.movies.model.MovieList
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getMovieListUseCase: GetMovieListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMovieListUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMovies should emit Content state on success`() = runTest {
        val movies = listOf(
            Movie(
                id = 0,
                localizedName = "ГДЕ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = listOf(Genre.CRIMINAL)
            ),
            Movie(
                id = 1,
                localizedName = "ЭЮЯ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = listOf(Genre.MUSICALE, Genre.THRILLER, Genre.CRIMINAL)
            ),
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
        )
        val movieList = MovieList(films = movies)
        coEvery { getMovieListUseCase() } returns Result.success(movieList)

        viewModel = MovieListViewModel(getMovieListUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.state is State.Content)
        assertEquals(movies, state.pickedMovies)
    }

    @Test
    fun `loadMovies should emit Error state on failure`() = runTest {
        coEvery { getMovieListUseCase() } returns Result.failure(Exception("Network error"))

        viewModel = MovieListViewModel(getMovieListUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.state is State.Error)
        assertEquals("Network error", (state.state as State.Error).message)
    }

    @Test
    fun `pickGenre should filter movies by selected genre`() = runTest {
        val movies = listOf(
            Movie(
                id = 0,
                localizedName = "ГДЕ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = listOf(Genre.CRIMINAL)
            ),
            Movie(
                id = 1,
                localizedName = "ЭЮЯ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = listOf(Genre.MUSICALE, Genre.THRILLER, Genre.CRIMINAL)
            ),
            Movie(
                id = 1,
                localizedName = "АБВ",
                name = "",
                year = 0,
                rating = 0.0,
                imageUrl = null,
                description = "",
                genres = emptyList()
            )
        )
        val movieList = MovieList(films = movies)
        coEvery { getMovieListUseCase() } returns Result.success(movieList)

        viewModel = MovieListViewModel(getMovieListUseCase)

        advanceUntilIdle()

        viewModel.pickGenre(Genre.CRIMINAL.ordinal)

        val picked = viewModel.state.value.pickedMovies
        assertEquals(2, picked.size)
        assertTrue(picked.all { it.genres.contains(Genre.CRIMINAL) })
    }
}