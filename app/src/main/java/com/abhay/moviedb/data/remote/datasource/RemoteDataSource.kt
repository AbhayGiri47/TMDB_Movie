package com.abhay.moviedb.data.remote.datasource

import com.abhay.moviedb.base.BaseDataSource
import com.abhay.moviedb.base.Result
import com.abhay.moviedb.data.remote.services.MovieApiService
import com.abhay.moviedb.util.NetworkUtils
import com.abhay.moviedb.data.dto.MovieList
import com.abhay.moviedb.data.dto.MovieDetail
import com.abhay.moviedb.data.dto.PersonMovieCredits
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
      networkUtils: NetworkUtils,
    private val movieApiService: MovieApiService,
) : BaseDataSource(networkUtils) {

    suspend fun getMovieList(): Result<MovieList> {
        val result = getResult {
            movieApiService.getMovieList(1)
        }
        return result
    }

    suspend fun getPopularMovies(): Result<MovieList> {
        val result = getResult {
            movieApiService.getPopularMovies(1)
        }
        return result
    }

    suspend fun getTopRatedMovies(): Result<MovieList> {
        val result = getResult {
            movieApiService.getTopRatedMovies(1)
        }
        return result
    }

    suspend fun getMovieDetails(movieId:Int): Result<MovieDetail> {
        val result = getResult {
            movieApiService.getMovieDetails(movieId)
        }
        return result
    }

    suspend fun getMovieCastDetails(movieId:Int): Result<PersonMovieCredits> {
        val result = getResult {
            movieApiService.getMovieCredits(movieId)
        }
        return result
    }
}