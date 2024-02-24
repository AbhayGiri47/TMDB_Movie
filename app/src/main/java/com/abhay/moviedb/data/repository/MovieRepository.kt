package com.abhay.moviedb.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.abhay.moviedb.base.Result
import com.abhay.moviedb.data.dto.Movie
import com.abhay.moviedb.data.dto.MovieList
import com.abhay.moviedb.data.dto.MovieDetail
import com.abhay.moviedb.data.dto.PersonMovieCredits
import com.abhay.moviedb.data.paging.MoviePagingSource
import com.abhay.moviedb.data.remote.datasource.RemoteDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {


    suspend fun getMovieDetails(movieId:Int): Result<MovieDetail> {
        return remoteDataSource.getMovieDetails(movieId)
    }

    suspend fun getMovieCastDetails(movieId:Int): Result<PersonMovieCredits> {
        return remoteDataSource.getMovieCastDetails(movieId)
    }

    fun getMoviesList(): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource) }
    ).liveData

    fun getPopularMoviesList(): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource,isPopular = true) }
    ).liveData

    fun getTopRatedMoviesList(): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource, isTopRated = true) }
    ).liveData


}