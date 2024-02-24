package com.abhay.moviedb.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.moviedb.base.Result
import com.abhay.moviedb.data.dto.MovieDetail
import com.abhay.moviedb.data.dto.PersonMovieCredits
import com.abhay.moviedb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetail>>()
    val movieDetail: LiveData<Result<MovieDetail>>
        get() = _movieDetail

    private val _movieCastDetail = MutableLiveData<Result<PersonMovieCredits>>()
    val movieCastDetail: LiveData<Result<PersonMovieCredits>>
        get() = _movieCastDetail

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.postValue(movieRepository.getMovieDetails(movieId))
        }
    }

    fun getMovieCastDetails(movieId: Int) {
        viewModelScope.launch {
            _movieCastDetail.postValue(movieRepository.getMovieCastDetails(movieId))
        }
    }
}