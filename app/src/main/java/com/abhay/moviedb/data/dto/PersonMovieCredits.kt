package com.abhay.moviedb.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PersonMovieCredits(
    val cast: List<Cast>,
    val id: Int
)