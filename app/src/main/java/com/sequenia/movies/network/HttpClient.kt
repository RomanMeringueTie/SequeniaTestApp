package com.sequenia.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sequenia.movies.model.MovieList
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


val okHttpClient = OkHttpClient.Builder()
    .readTimeout(3, TimeUnit.SECONDS)
    .connectTimeout(3, TimeUnit.SECONDS)
    .build()


val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
    .client(okHttpClient)
    .addConverterFactory(
        Json.asConverterFactory(
            "application/json".toMediaType()
        )
    )
    .build()

interface HttpClient {

    @GET("films.json")
    suspend fun getAll(): MovieList

}