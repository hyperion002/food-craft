package com.example.foodcraft.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcraft.databinding.RecipesItemBinding
import com.example.foodcraft.models.FoodRecipe
import com.example.foodcraft.models.Result
import com.example.foodcraft.util.GenericDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    private var recipes = emptyList<Result>()
    class RecipesViewHolder(private val binding: RecipesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesItemBinding.inflate(layoutInflater, parent, false)
                return RecipesViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData: FoodRecipe) {
        val genericDiffUtil = GenericDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(genericDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}