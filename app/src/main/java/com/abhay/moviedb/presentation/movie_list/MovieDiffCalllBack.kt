package com.abhay.moviedb.presentation.movie_list

import androidx.recyclerview.widget.DiffUtil
import com.abhay.moviedb.data.dto.Movie

class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
