package com.sequenia.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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