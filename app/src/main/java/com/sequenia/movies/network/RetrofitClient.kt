package com.sequenia.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sequenia.movies.model.MovieList
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


class RetrofitClient {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(logger)
        .build()

    fun getRetrofit(
        baseUrl: HttpUrl = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/".toHttpUrl()
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json".toMediaType()
                )
            ).build()
    }

    interface MoviesApi {

        @GET("films.json")
        suspend fun getAll(): MovieList

    }
}