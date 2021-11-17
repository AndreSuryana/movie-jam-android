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
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.databinding.CardviewListBinding
import com.example.moviejam.diffutil.TvShowDiffCallback


class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    private val listData = ArrayList<TvShow>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setList(listData: List<TvShow>) {
        val diffCallback = TvShowDiffCallback(this.listData, listData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listData.clear()
        this.listData.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowsViewHolder {
        val cardViewListBinding = CardviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(cardViewListBinding)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickListener?.onClick(listData[holder.adapterPosition].id) }
    }

    override fun getItemCount(): Int = listData.size

    inner class TvShowsViewHolder(private val binding: CardviewListBinding) : RecyclerView.ViewHolder(binding.root) {
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
}