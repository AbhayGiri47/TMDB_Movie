package com.abhay.moviedb.presentation.movie_details

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhay.moviedb.R
import com.abhay.moviedb.base.BaseFragment
import com.abhay.moviedb.base.Result
import com.abhay.moviedb.data.dto.MovieDetail
import com.abhay.moviedb.data.dto.PersonMovieCredits
import com.abhay.moviedb.databinding.FragmentMovieDetailsBinding
import com.abhay.moviedb.presentation.movie_list.ItemMarginDecorationHelper
import com.abhay.moviedb.util.Constants
import com.abhay.moviedb.util.loadImagesWithGlideExt
import com.abhay.moviedb.util.observe
import com.abhay.moviedb.util.toGone
import com.abhay.moviedb.util.toVisible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    val safeArgs by navArgs<MovieDetailsFragmentArgs>()


    override fun FragmentMovieDetailsBinding.initialize() {

        val movieId = safeArgs.movieID
        Log.d("movieId", "initialize: $movieId")

        movieDetailsViewModel.getMovieDetails(movieId)
        movieDetailsViewModel.getMovieCastDetails(movieId)
    }

    override fun observeViewModel() {

        observe(movieDetailsViewModel.movieDetail, ::handleMovieDetailsResponse)
        observe(movieDetailsViewModel.movieCastDetail, ::handleMovieCastDetailsResponse)

    }

    private fun handleMovieCastDetailsResponse(result: Result<PersonMovieCredits>) {
        when (result.status) {
            Result.Status.SUCCESS -> {
                movieDetailsViewModel.movieCastDetail.value?.data?.let {
                    // recycler view for cast
                    val itemMarginDecoration = ItemMarginDecorationHelper.HorizontalItemMarginDecoration(20)
                    binding.detailRvCastBody.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val castAdapter = CastAdapter ()
                    castAdapter.updateMovieCastList(it.cast)
                    binding.detailRvCastBody.addItemDecoration(itemMarginDecoration)
                    binding.detailRvCastBody.adapter = castAdapter

                }

                binding.pbLoader.toGone()
            }

            Result.Status.LOADING -> {
                binding.pbLoader.toVisible()
            }

            Result.Status.ERROR -> {
                binding.pbLoader.toGone()
                Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun handleMovieDetailsResponse(result: Result<MovieDetail>) {
        when (result.status) {
            Result.Status.SUCCESS -> {
                binding.movieDetailCard.toVisible()
                movieDetailsViewModel.movieDetail.value?.data?.let { movieDetail ->

                    Log.d("Movielist", "handleMovieListResponse: $movieDetail")
                    // backdrop
                    movieDetail.backdrop_path?.let {
                        binding.detailImageCover.loadImagesWithGlideExt(Constants.IMAGE_BASE_URL + it)

                    }
                    // movie title + release year (extracted year from release data)
                    val releaseDate = movieDetail.release_date
                    val titleText = movieDetail.title
                    val yearText = "(${releaseDate?.substring(0, 4)})"
                    "$titleText $yearText".also { binding.detailTvTitle.text = it }
                    // movie tag
                    binding.detailTvTagline.text =
                        getString(R.string.detail_tagline, movieDetail.tagline)
                    // overview body
                    binding.detailTvOverviewHeader.text = movieDetail.overview


                }

                binding.pbLoader.toGone()
            }

            Result.Status.LOADING -> {
                binding.pbLoader.toVisible()
                binding.movieDetailCard.toGone()
            }

            Result.Status.ERROR -> {
                binding.pbLoader.toGone()
                Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
            }
        }
    }


}