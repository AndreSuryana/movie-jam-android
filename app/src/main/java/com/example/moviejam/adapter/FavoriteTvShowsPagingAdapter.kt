package com.example.moviejam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.constant.Constants
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.databinding.CardviewListBinding

class FavoriteTvShowsPagingAdapter :
    PagingDataAdapter<FavoriteEntity, FavoriteTvShowsPagingAdapter.FavoriteMoviesViewHolder>(
        MOVIES_COMPARATOR
    ) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val data = getItem(position)

        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val cardViewListBinding =
            CardviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMoviesViewHolder(cardViewListBinding)
    }

    inner class FavoriteMoviesViewHolder(private val binding: CardviewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteEntity) {
            with(binding) {
                // Set item content
                ivPoster.loadImage(data.posterPath)
                tvTitle.text = data.title
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
            }
        }
    }

    private fun ImageView.loadImage(path: String?) {
        val url = Constants.IMAGE_BASE_URL + path
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
            .error(R.drawable.placeholder)
            .into(this)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    companion object {
        private val MOVIES_COMPARATOR = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean =
                oldItem == newItem
        }
    }
}