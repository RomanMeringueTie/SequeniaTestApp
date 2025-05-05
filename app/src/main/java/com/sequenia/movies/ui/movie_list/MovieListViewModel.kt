package com.sequenia.movies.ui.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.movies.model.Genre
import com.sequenia.movies.model.Movie
import com.sequenia.movies.model.MovieList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface State {
    data object Loading : State
    data class Content(val data: MovieList) : State
    data class Error(val message: String) : State
}

data class MovieListState(
    val state: State = State.Loading,
    val pickedGenre: Genre? = null,
    val pickedMovies: List<Movie> = emptyList()
)

class MovieListViewModel(private val getMovieListUseCase: GetMovieListUseCase) : ViewModel() {

    private val _state = MutableStateFlow<MovieListState>(MovieListState())
    val state: StateFlow<MovieListState> = _state

    init {
        loadMovies()
    }

    fun retry() {
        _state.value =
            state.value.copy(state = State.Loading, pickedGenre = null, pickedMovies = emptyList())
        loadMovies()
    }

    private fun loadMovies() {
        if (_state.value.state !is State.Loading)
            return
        viewModelScope.launch {
            val result = getMovieListUseCase()
            result.fold(
                onSuccess = { response ->
                    val list = response.apply {
                        films.sortedBy { it.localizedName }
                    }
                    _state.value =
                        state.value.copy(state = State.Content(list), pickedMovies = list.films)
                },
                onFailure = { error ->
                    _state.value =
                        state.value.copy(
                            state = State.Error(
                                error.message ?: "Ошибка подключения сети"
                            )
                        )
                }
            )
        }
    }

    fun pickGenre(position: Int) {

        val pickedGenre =
            if (_state.value.pickedGenre == Genre.entries[position]) null
            else Genre.entries[position]

        _state.value = _state.value.copy(pickedGenre = pickedGenre)

        val pickedMovies =
            if (_state.value.pickedGenre != null)
                (_state.value.state as State.Content).data.films.filter {
                    it.genres.contains(_state.value.pickedGenre)
                }
            else (_state.value.state as State.Content).data.films


        _state.value = _state.value.copy(pickedMovies = pickedMovies)
    }
}