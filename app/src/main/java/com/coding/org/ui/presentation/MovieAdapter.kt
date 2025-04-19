package com.coding.org.ui.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coding.`fun`.databinding.MovieListItemBinding
import com.coding.org.domain.Movie
import javax.inject.Inject

class MovieAdapter(): PagingDataAdapter<Movie,MovieAdapter.ViewHolder>(MovieDiffCallback) {

    @Inject
    lateinit var glide: Glide

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.movieName.text = getItem(position)?.title
        Glide.with(holder.binding.movieLogo.context)
            .load("https://image.tmdb.org/t/p/w500" + getItem(position)?.posterUrl)
            .into(holder.binding.movieLogo)
    }

    inner class ViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val MovieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }
}