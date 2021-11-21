package com.example.moviejam.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.moviejam.data.source.local.entity.FavoriteEntity

class FavoriteDiffCallback(
    private val oldList: List<FavoriteEntity>,
    private val newList: List<FavoriteEntity>
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