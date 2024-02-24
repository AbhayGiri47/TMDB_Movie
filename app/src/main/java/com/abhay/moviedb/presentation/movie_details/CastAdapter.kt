package com.abhay.moviedb.presentation.movie_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhay.moviedb.data.dto.Cast
import com.abhay.moviedb.databinding.ItemMovieCastBinding
import com.abhay.moviedb.util.Constants
import com.abhay.moviedb.util.loadImagesWithGlideExt

class CastAdapter() :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    private var movieCast = mutableListOf<Cast>()

    private lateinit var binding: ItemMovieCastBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        binding = ItemMovieCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieCast.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val movieCast = movieCast[position]
        holder.bind(movieCast)
    }

    class CastViewHolder(private val binding: ItemMovieCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieCast: Cast) {

            binding.ivCast.loadImagesWithGlideExt(Constants.IMAGE_BASE_URL+movieCast.profile_path)
            binding.tvCastName.text = movieCast.name
            binding.tvCastRole.text = movieCast.character
        }
    }

    fun updateMovieCastList(movieCastList: List<Cast>) {
        this.movieCast.clear()
        this.movieCast = movieCastList.toMutableList()
        notifyDataSetChanged()
    }


}