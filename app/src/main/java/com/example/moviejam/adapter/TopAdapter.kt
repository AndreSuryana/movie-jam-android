package com.example.moviejam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.CardviewTopBinding
import com.example.moviejam.diffutil.DataDiffCallback

class TopAdapter : RecyclerView.Adapter<TopAdapter.TopViewHolder>() {

    private val listData = ArrayList<DataEntity>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setListTopMovies(listData: List<DataEntity>) {
        val diffCallback = DataDiffCallback(this.listData, listData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listData.clear()
        this.listData.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val cardViewTopBinding = CardviewTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopViewHolder(cardViewTopBinding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickListener?.onClick(listData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listData.size

    inner class TopViewHolder(private val binding: CardviewTopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.poster)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                    .error(R.drawable.placeholder)
                    .into(ivPoster)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(data: DataEntity)
    }
}
