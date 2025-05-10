package com.sequenia.movies.network

import com.sequenia.movies.data.network.RetrofitClient
import com.sequenia.movies.data.network.RetrofitClient.MoviesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class RetrofitClientTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var moviesApi: MoviesApi

    @Before
    fun setUp() {
        mockWebServer =
            MockWebServer()

        mockWebServer.start()

        mockWebServer.url("/")

        val retrofit =
            RetrofitClient().getRetrofit(mockWebServer.url("/"))

        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAll should parse response correctly`() = runTest {
        val responseJson = """
        {
            "films": [
                {
                "id": 326,
                "localized_name": "Побег из Шоушенка",
                "name": "The Shawshank Redemption",
                "year": 1994,
                "rating": 9.196,
                "image_url": "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                "description": "Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.",
                "genres": ["драма"]
                }
            ]
        }
        """.trimIndent()

        val response = MockResponse().setBody(responseJson).setResponseCode(200)

        mockWebServer.enqueue(response)

        val expected = "Побег из Шоушенка"
        val actual = moviesApi.getAll().films.first().localizedName

        assertEquals(expected, actual)
    }

    @Test
    fun `getAll should throw an exception due to timeout`() = runTest {


        val responseJson = """
        {
            "films": [
                {
                "id": 326,
                "localized_name": "Побег из Шоушенка",
                "name": "The Shawshank Redemption",
                "year": 1994,
                "rating": 9.196,
                "image_url": "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                "description": "Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.",
                "genres": ["драма"]
                }
            ]
        }
        """.trimIndent()

        val response = MockResponse().setBody(responseJson).setResponseCode(200)
            .throttleBody(1024, 21, TimeUnit.SECONDS)

        mockWebServer.enqueue(response)

        assertThrows(SocketTimeoutException::class.java)
        { runBlocking { moviesApi.getAll() } }.message
    }
}