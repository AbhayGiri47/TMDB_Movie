package com.abhay.moviedb.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val character: String,
    val name: String,
    val profile_path: String
)