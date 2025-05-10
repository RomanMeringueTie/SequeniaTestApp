package com.sequenia.movies.model

import org.junit.Test
import kotlin.test.assertEquals

class GenreTest {
    @Test
    fun `getCapitalized should return capitalized genres`() {
        val expected = listOf(
            "Биография",
            "Боевик",
            "Детектив",
            "Драма",
            "Комедия",
            "Криминал",
            "Мелодрама",
            "Мюзикл",
            "Приключения",
            "Триллер",
            "Ужасы",
            "Фантастика",
            "Фэнтези"
        )
        val actual = Genre.getCapitalized()
        assertEquals(expected, actual)
    }
}