package com.example.moviejam.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.moviejam.data.source.remote.response.tvshow.TvShow

class TvShowDiffCallback(
    private val oldList: List<TvShow>,
    private val newList: List<TvShow>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]

        return oldData.id == newData.id
    }
}