package com.example.moviejam.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.moviejam.data.model.DataEntity

class DataDiffCallback(
    private val oldList: List<DataEntity>,
    private val newList: List<DataEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[oldItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]

        return oldData.title == newData.title
    }
}