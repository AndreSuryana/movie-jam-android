package com.example.moviejam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.databinding.CardviewListFavoriteBinding
import com.example.moviejam.utils.Extensions.loadImage

class FavoriteAdapter : PagedListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: OnItemClickListener? = null
    private var onBtnDeleteClickListener: OnBtnDeleteClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val cardViewListFavoriteBinding = CardviewListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(cardViewListFavoriteBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            data?.id?.let { id ->
                onItemClickListener?.onClick(id)
            }
        }
        holder.getBinding().btnDelete.setOnClickListener {
            data?.id?.let { id ->
                onBtnDeleteClickListener?.onClick(id)
            }
        }
    }

    inner class FavoriteViewHolder(private val binding: CardviewListFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteEntity) {
            with(binding) {
                // Set item content
                ivPoster.loadImage(data.posterPath)
                tvTitle.text = data.title
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
            }
        }

        fun getBinding() = binding
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnBtnDeleteClickListener(onBtnDeleteClickListener: OnBtnDeleteClickListener) {
        this.onBtnDeleteClickListener = onBtnDeleteClickListener
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    interface OnBtnDeleteClickListener {
        fun onClick(id: Int)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEntity>() {
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
