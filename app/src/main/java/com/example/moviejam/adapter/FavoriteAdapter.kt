package com.example.moviejam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.constant.Constants
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.databinding.CardviewListFavoriteBinding
import com.example.moviejam.diffutil.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MoviesViewHolder>() {

    private val listData = ArrayList<FavoriteEntity>()
    private var onItemClickListener: OnItemClickListener? = null
    private var onBtnDeleteClickListener: OnBtnDeleteClickListener? = null

    fun setList(listData: List<FavoriteEntity>) {
        val diffCallback = FavoriteDiffCallback(this.listData, listData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listData.clear()
        this.listData.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val cardViewListFavoriteBinding = CardviewListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(cardViewListFavoriteBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickListener?.onClick(listData[holder.absoluteAdapterPosition].id) }
        holder.getBinding().btnDelete.setOnClickListener { onBtnDeleteClickListener?.onClick(listData[holder.absoluteAdapterPosition].id) }
    }

    override fun getItemCount(): Int = listData.size

    inner class MoviesViewHolder(private val binding: CardviewListFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
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

    fun setOnBtnDeleteClickListener(onBtnDeleteClickListener: OnBtnDeleteClickListener) {
        this.onBtnDeleteClickListener = onBtnDeleteClickListener
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    interface OnBtnDeleteClickListener {
        fun onClick(id: Int)
    }
}
