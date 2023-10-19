package com.example.foodcraft.adapters

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcraft.R
import com.example.foodcraft.data.database.entities.FavouritesEntity
import com.example.foodcraft.databinding.FavouriteRecipesItemBinding
import com.example.foodcraft.ui.fragments.favouriterecipes.FavouriteRecipesFragmentDirections
import com.example.foodcraft.util.DiffUtilTemplate
import com.example.foodcraft.viewmodels.MainViewModel

class FavouriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavouriteRecipesAdapter.FavouriteRecipesViewHolder>(),
    ActionMode.Callback {

    private var favouriteRecipes = emptyList<FavouritesEntity>()
    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var favouriteRecipesViewHolders = arrayListOf<FavouriteRecipesViewHolder>()

    private var multiSelection = false

    private lateinit var actionModeGlobal: ActionMode

    class FavouriteRecipesViewHolder(val binding: FavouriteRecipesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouriteEntity = favouritesEntity
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipesViewHolder {
        return FavouriteRecipesViewHolder(
            FavouriteRecipesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    override fun onBindViewHolder(holder: FavouriteRecipesViewHolder, position: Int) {

        favouriteRecipesViewHolders.add(holder)

        val currentRecipe = favouriteRecipes[position]
        holder.bind(currentRecipe)

        saveItemStateOnScroll(currentRecipe, holder)

        holder.binding.materialcardviewFavouriteRecipes.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.binding.materialcardviewFavouriteRecipes.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                applySelection(holder, currentRecipe)
                true
            }
        }
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>) {
        val favouriteRecipesDiffUtil = DiffUtilTemplate(favouriteRecipes, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favouriteRecipesDiffUtil)
        favouriteRecipes = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)

    }

    private fun applySelection(
        holder: FavouriteRecipesViewHolder,
        currentRecipe: FavouritesEntity
    ) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            holder.binding.materialcardviewFavouriteRecipes.isChecked = false
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            holder.binding.materialcardviewFavouriteRecipes.isChecked = true
            applyActionModeTitle()
        }
    }

    private fun saveItemStateOnScroll(
        currentRecipe: FavouritesEntity,
        holder: FavouriteRecipesViewHolder
    ) {
        holder.binding.materialcardviewFavouriteRecipes.isChecked =
            selectedRecipes.contains(currentRecipe)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                actionModeGlobal.finish()
                multiSelection = false
            }

            1 -> {
                actionModeGlobal.title = "${selectedRecipes.size} item selected"
            }

            else -> {
                actionModeGlobal.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        actionModeGlobal = actionMode!!
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?) = true

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favourite_recipe) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavouriteRecipe(it)
            }

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        favouriteRecipesViewHolders.forEach { holder ->
            holder.binding.materialcardviewFavouriteRecipes.isChecked = false
        }
        multiSelection = false
        selectedRecipes.clear()
    }

    fun clearContextualActionMode() {
        if (this::actionModeGlobal.isInitialized) {
            actionModeGlobal.finish()
        }
    }
}