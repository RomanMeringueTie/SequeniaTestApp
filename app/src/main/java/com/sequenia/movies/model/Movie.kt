package com.sequenia.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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