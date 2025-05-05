package com.sequenia.movies.di

import com.sequenia.movies.network.HttpClient
import com.sequenia.movies.network.retrofit
import com.sequenia.movies.ui.movie_list.GetMovieListUseCase
import com.sequenia.movies.ui.movie_list.MovieListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { retrofit.create(HttpClient::class.java) }
    singleOf(::GetMovieListUseCase)
    viewModelOf(::MovieListViewModel)
}