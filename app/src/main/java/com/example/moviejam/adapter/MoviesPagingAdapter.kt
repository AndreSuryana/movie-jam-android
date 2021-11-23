package com.example.moviejam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.databinding.CardviewListBinding
import com.example.moviejam.utils.Extensions.loadImage

class MoviesPagingAdapter :
    PagingDataAdapter<Movie, MoviesPagingAdapter.MoviesPagingViewHolder>(
        MOVIES_COMPARATOR
    ) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: MoviesPagingViewHolder, position: Int) {
        val data = getItem(position)

        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener { onItemClickListener?.onClick(data.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesPagingViewHolder {
        val cardViewListBinding =
            CardviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesPagingViewHolder(cardViewListBinding)
    }

    inner class MoviesPagingViewHolder(private val binding: CardviewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                // Set item content
                ivPoster.loadImage(data.posterPath)
                tvTitle.text = data.title
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    companion object {
        private val MOVIES_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean =
                oldItem == newItem
        }
    }
}