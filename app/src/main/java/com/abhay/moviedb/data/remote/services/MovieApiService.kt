package com.abhay.moviedb.data.remote.services

import com.abhay.moviedb.util.Constants
import com.abhay.moviedb.data.dto.MovieList
import com.abhay.moviedb.data.dto.MovieDetail
import com.abhay.moviedb.data.dto.PersonMovieCredits
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("3/movie/now_playing")
    suspend fun getMovieList(
        @Query("page") page: Int = 1
    ): Response<MovieList>

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int = -1
    ): Response<MovieDetail>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): Response<MovieList>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): Response<MovieList>

    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") id : Int) : Response<PersonMovieCredits>


}