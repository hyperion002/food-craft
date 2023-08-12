package com.example.foodcraft.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodcraft.R
import com.example.foodcraft.databinding.IngredientsItemBinding
import com.example.foodcraft.models.ExtendedIngredient
import com.example.foodcraft.util.Constants.Companion.BASE_INGREDIENT_IMAGE_URL
import com.example.foodcraft.util.GenericDiffUtil
import java.util.Locale

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder(val binding: IngredientsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            IngredientsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.binding.imageviewIngredient.load(BASE_INGREDIENT_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.textviewIngredientName.text =
            ingredientsList[position].name.replaceFirstChar { firstChar ->
                if (firstChar.isLowerCase())
                    firstChar.titlecase(Locale.ROOT)
                else
                    firstChar.toString()
            }
        holder.binding.textviewIngredientAmount.text = ingredientsList[position].amount.toString()
        holder.binding.textviewIngredientUnit.text = ingredientsList[position].unit
        holder.binding.textviewIngredientConsistency.text = ingredientsList[position].consistency
        holder.binding.textviewIngredientOriginal.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int = ingredientsList.size

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientDiffUtil = GenericDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}