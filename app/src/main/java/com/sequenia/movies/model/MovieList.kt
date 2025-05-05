package com.sequenia.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieList(
    @SerialName("films")
    val films: List<Movie>
)

@Serializable
data class Movie(
    @SerialName("id")
    val id: Int,
    @SerialName("localized_name")
    val localizedName: String,
    @SerialName("name")
    val name: String,
    @SerialName("year")
    val year: Int,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("genres")
    val genres: List<Genre>
)

@Serializable
enum class Genre(val string: String) {
    @SerialName("биография")
    BIOGRAPHY("биография"),

    @SerialName("боевик")
    ACTION("боевик"),

    @SerialName("детектив")
    DETECTIVE("детектив"),

    @SerialName("драма")
    DRAMA("драма"),

    @SerialName("комедия")
    COMEDY("комедия"),

    @SerialName("криминал")
    CRIMINAL("криминал"),

    @SerialName("мелодрама")
    ROMANCE("мелодрама"),

    @SerialName("мюзикл")
    MUSICALE("мюзикл"),

    @SerialName("приключения")
    ADVENTURE("приключения"),

    @SerialName("триллер")
    THRILLER("триллер"),

    @SerialName("ужасы")
    HORROR("ужасы"),

    @SerialName("фантастика")
    FANTASTIC("фантастика"),

    @SerialName("фэнтези")
    FANTASY("фэнтези");

    companion object {

        fun getCapitalized() = entries.map {
            it.string.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
        }

    }
}