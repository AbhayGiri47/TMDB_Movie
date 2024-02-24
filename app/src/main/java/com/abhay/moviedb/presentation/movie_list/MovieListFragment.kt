package com.abhay.moviedb.presentation.movie_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhay.moviedb.R
import com.abhay.moviedb.base.BaseFragment
import com.abhay.moviedb.data.paging.LoaderAdapter
import com.abhay.moviedb.databinding.FragmentMovieListBinding
import com.abhay.moviedb.util.toGone
import com.abhay.moviedb.util.toVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list) {

    private val movieListViewModel: MovieListViewModel by viewModels()

    var isPopular = MutableLiveData(false)
    var isTopRated = MutableLiveData(false)
    var isAllMovies = MutableLiveData(false)

    override fun observeViewModel() {

    }

    override fun onResume() {
        super.onResume()
        if (isAllMovies.value == true){
            initializeAdapter()

        }else if (isTopRated.value == true){
            initializeAdapter(isTopRated=true)

        }else if (isPopular.value == true){
            initializeAdapter(isPopular=true)
        }
    }

    override fun FragmentMovieListBinding.initialize() {
        initializeAdapter()

        implementBottomSheetClickListener()
    }

    private fun implementBottomSheetClickListener() {
        binding.clSortButton.setOnClickListener {
            initializeBottomSheet()
        }
    }

    private fun initializeBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(R.layout.item_movie_list_bottom_sheet, null)

        val tvPopular = view.findViewById<TextView>(R.id.tvPopular)
        val tvTopRated = view.findViewById<TextView>(R.id.tvTopRated)
        val tvAllMovies = view.findViewById<TextView>(R.id.tvAllMovies)
        val ivCheckPopular = view.findViewById<ImageView>(R.id.ivCheckPopular)
        val ivCheckTopRated = view.findViewById<ImageView>(R.id.ivCheckTopRated)
        val ivCheckAllMovies = view.findViewById<ImageView>(R.id.ivCheckAllMovies)

        if (isAllMovies.value == true){
            ivCheckPopular.toGone()
            ivCheckTopRated.toGone()
            ivCheckAllMovies.toVisible()

        }else if (isTopRated.value == true){
            ivCheckPopular.toGone()
            ivCheckTopRated.toVisible()
            ivCheckAllMovies.toGone()

        }else if (isPopular.value == true){
            ivCheckPopular.toVisible()
            ivCheckTopRated.toGone()
            ivCheckAllMovies.toGone()
        }

        tvPopular.setOnClickListener {

            initializeAdapter(isPopular = true)
            isPopular.value = true
            isTopRated.value=false
            isAllMovies.value =false
            dialog.dismiss()

        }

        tvTopRated.setOnClickListener {

            initializeAdapter(isTopRated = true)
            isTopRated.value=true
            isPopular.value = false
            isAllMovies.value =false

            dialog.dismiss()
        }
        tvAllMovies.setOnClickListener {

            initializeAdapter()
            isAllMovies.value =true
            isPopular.value = false
            isTopRated.value=false
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun initializeAdapter(isPopular: Boolean = false, isTopRated: Boolean = false) {

        val adapter = MoviePagingAdapter {

            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }

        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvMovieList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }

        adapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    if (loadState.source.refresh is LoadState.NotLoading) {
                        binding.rvMovieList.visibility = View.VISIBLE
                        if (loadState.append.endOfPaginationReached && adapter.itemCount < 1) {

                        } else {

                            binding.pbLoader.visibility = View.GONE
                        }
                    }
                }

                is LoadState.Loading -> {
                    binding.pbLoader.toVisible()
                    binding.rvMovieList.toGone()
                }

                is LoadState.Error -> {
                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (isPopular) {
            movieListViewModel.popularMoviesList.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        } else if (isTopRated) {
            movieListViewModel.topRatedMoviesList.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        } else {

            movieListViewModel.moviesList.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

    }

}