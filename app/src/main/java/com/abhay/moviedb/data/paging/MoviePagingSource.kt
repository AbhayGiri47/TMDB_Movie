package com.abhay.moviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abhay.moviedb.base.Result
import com.abhay.moviedb.data.dto.Movie
import com.abhay.moviedb.data.dto.MovieList
import com.abhay.moviedb.data.remote.datasource.RemoteDataSource

class MoviePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val isPopular :Boolean = false,
    private val isTopRated : Boolean = false
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response : Result<MovieList>
            if (isPopular){
                response = remoteDataSource.getPopularMovies()
            }else if (isTopRated){
                response = remoteDataSource.getTopRatedMovies()
            }else{
                 response = remoteDataSource.getMovieList()
            }


            if (response.status == Result.Status.SUCCESS && response.data != null) {
                LoadResult.Page(
                    data = response.data.results,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (position == response.data.total_pages) null else (position + 1)
                )
            } else {
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {

            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}