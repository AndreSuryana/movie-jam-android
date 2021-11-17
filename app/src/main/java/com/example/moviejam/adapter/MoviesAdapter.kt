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
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.databinding.CardviewListBinding
import com.example.moviejam.diffutil.MovieDiffCallback

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val listData = ArrayList<Movie>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setList(listData: List<Movie>) {
        val diffCallback = MovieDiffCallback(this.listData, listData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listData.clear()
        this.listData.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val cardViewListBinding = CardviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(cardViewListBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickListener?.onClick(listData[holder.adapterPosition].id) }
    }

    override fun getItemCount(): Int = listData.size

    inner class MoviesViewHolder(private val binding: CardviewListBinding) : RecyclerView.ViewHolder(binding.root) {
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