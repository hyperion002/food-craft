package com.example.foodcraft.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcraft.data.database.entities.FavouritesEntity
import com.example.foodcraft.databinding.FavouriteRecipesItemBinding
import com.example.foodcraft.util.GenericDiffUtil

class FavouriteRecipesAdapter :
    RecyclerView.Adapter<FavouriteRecipesAdapter.FavouriteRecipesViewHolder>() {

    private var favouriteRecipes = emptyList<FavouritesEntity>()
    class FavouriteRecipesViewHolder(private val binding: FavouriteRecipesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouriteEntity = favouritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavouriteRecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return FavouriteRecipesViewHolder(FavouriteRecipesItemBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipesViewHolder {
        return FavouriteRecipesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    override fun onBindViewHolder(holder: FavouriteRecipesViewHolder, position: Int) {
        val selectedRecipes = favouriteRecipes[position]
        holder.bind(selectedRecipes)
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>) {
        val favouriteRecipesDiffUtil = GenericDiffUtil(favouriteRecipes, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favouriteRecipesDiffUtil)
        favouriteRecipes = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)

    }
}