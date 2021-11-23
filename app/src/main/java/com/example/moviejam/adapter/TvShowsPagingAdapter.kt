package com.example.moviejam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.databinding.CardviewListBinding
import com.example.moviejam.utils.Extensions.loadImage

class TvShowsPagingAdapter :
    PagingDataAdapter<TvShow, TvShowsPagingAdapter.TvShowsPagingViewHolder>(
        TV_SHOWS_COMPARATOR
    ) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: TvShowsPagingViewHolder, position: Int) {
        val data = getItem(position)

        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener { onItemClickListener?.onClick(data.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsPagingViewHolder {
        val cardViewListBinding =
            CardviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsPagingViewHolder(cardViewListBinding)
    }

    inner class TvShowsPagingViewHolder(private val binding: CardviewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TvShow) {
            with(binding) {
                // Set item content
                ivPoster.loadImage(data.posterPath)
                tvTitle.text = data.name
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.firstAirDate
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
        private val TV_SHOWS_COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(
                oldItem: TvShow,
                newItem: TvShow
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TvShow,
                newItem: TvShow
            ): Boolean =
                oldItem == newItem
        }
    }
}