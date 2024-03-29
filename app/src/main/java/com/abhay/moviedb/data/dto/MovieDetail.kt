package com.abhay.moviedb.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val adult: Boolean ?= false,
    val backdrop_path: String ?= null,
    val id: Int ?= -1,
    val overview: String ?= null,
    val popularity: Double ?= null,
    val poster_path: String ?= null,
    val release_date: String ?= null,
    val runtime: Int ?= null,
    val title: String ?= null,
    val vote_average: Double ?= null,
    val vote_count: Int ?= null,
    val tagline: String?=null
)