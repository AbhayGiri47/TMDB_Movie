package com.abhay.moviedb.presentation.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abhay.moviedb.base.Result
import com.abhay.moviedb.data.dto.Movie
import com.abhay.moviedb.data.dto.MovieList
import com.abhay.moviedb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val moviesList: LiveData<PagingData<Movie>> = movieRepository.getMoviesList()
                .cachedIn(viewModelScope)

    val popularMoviesList: LiveData<PagingData<Movie>> = movieRepository.getPopularMoviesList()
        .cachedIn(viewModelScope)

    val topRatedMoviesList: LiveData<PagingData<Movie>> = movieRepository.getTopRatedMoviesList()
        .cachedIn(viewModelScope)



}