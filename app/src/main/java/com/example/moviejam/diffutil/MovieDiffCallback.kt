package com.example.moviejam.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.moviejam.data.source.remote.response.movie.Movie

class MovieDiffCallback(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]

        return oldData.id == newData.id
    }
}