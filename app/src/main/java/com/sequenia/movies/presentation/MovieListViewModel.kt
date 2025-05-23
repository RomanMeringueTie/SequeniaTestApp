package com.sequenia.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.movies.data.model.Genre
import com.sequenia.movies.domain.GetMovieListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
                    _state.value =
                        state.value.copy(
                            state = State.Content(response),
                            pickedMovies = response.films
                        )
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
                (_state.value.state as? State.Content)?.data?.films?.filter {
                    it.genres.contains(_state.value.pickedGenre)
                }
            else (_state.value.state as State.Content).data.films


        _state.value = _state.value.copy(pickedMovies = pickedMovies ?: emptyList())
    }
}