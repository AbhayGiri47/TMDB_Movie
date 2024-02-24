package com.abhay.moviedb.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int = -1,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)