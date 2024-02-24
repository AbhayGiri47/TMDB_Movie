package com.abhay.moviedb.presentation.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhay.moviedb.data.dto.Movie
import com.abhay.moviedb.databinding.ItemMovieListBinding
import com.abhay.moviedb.util.Constants
import com.abhay.moviedb.util.loadImagesWithGlideExt

class MoviePagingAdapter(private val onMovieClickListener: (movie: Movie) -> Unit) :
    PagingDataAdapter<Movie, MoviePagingAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onMovieClickListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?, onMovieClickListener: (movie: Movie) -> Unit ) {

            if (item!=null){

                item.poster_path?.let {
                    binding.ivMovie.loadImagesWithGlideExt(Constants.IMAGE_BASE_URL+it)
                }
                binding.tvTitle.text = item.title
                binding.ivMovie.setOnClickListener {
                    onMovieClickListener(item)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}