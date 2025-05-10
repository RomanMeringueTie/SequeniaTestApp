package com.sequenia.movies.di

import com.sequenia.movies.domain.GetMovieListUseCase
import com.sequenia.movies.domain.GetMovieListUseCaseImpl
import com.sequenia.movies.data.network.RetrofitClient
import com.sequenia.movies.data.network.RetrofitClient.MoviesApi
import com.sequenia.movies.presentation.MovieListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<Retrofit> { RetrofitClient().getRetrofit() }
    single<MoviesApi> {
        val retrofit: Retrofit = get()
        retrofit.create(MoviesApi::class.java)
    }
    single<GetMovieListUseCase> { GetMovieListUseCaseImpl(moviesApi = get()) }
    viewModel { MovieListViewModel(get()) }
}