package com.example.foodcraft.bindingadapters

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcraft.adapters.FavouriteRecipesAdapter
import com.example.foodcraft.data.database.entities.FavouritesEntity

object FavouriteRecipesBindings {
    @BindingAdapter("setVisibility", "setData", requireAll = false)
    @JvmStatic
    fun setVisibility(
        view: View,
        favoritesEntity: List<FavouritesEntity>?,
        mAdapter: FavouriteRecipesAdapter?
    ) {
        when (view) {
            is RecyclerView -> {
                val dataCheck = favoritesEntity.isNullOrEmpty()
                view.isInvisible = dataCheck
                if (!dataCheck) {
                    favoritesEntity?.let { mAdapter?.setData(it) }
                }
            }

            else -> view.isVisible = favoritesEntity.isNullOrEmpty()
        }
    }
}