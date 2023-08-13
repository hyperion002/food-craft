package com.example.foodcraft.util

import androidx.recyclerview.widget.DiffUtil

class DiffUtilTemplate<T>(
    private val oldLIst: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldLIst.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldLIst[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldLIst[oldItemPosition] == newList[newItemPosition]
    }
}