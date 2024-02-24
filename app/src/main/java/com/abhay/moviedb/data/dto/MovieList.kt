package com.abhay.moviedb.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieList(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)