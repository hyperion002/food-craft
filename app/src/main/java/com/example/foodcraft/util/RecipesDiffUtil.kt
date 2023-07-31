package com.example.foodcraft.util

import androidx.recyclerview.widget.DiffUtil
import com.example.foodcraft.models.Result

class RecipesDiffUtil(
    private val oldLIst: List<Result>,
    private val newList: List<Result>
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