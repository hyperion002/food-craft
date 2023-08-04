package com.example.foodcraft.adapters

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcraft.R
import com.example.foodcraft.data.database.entities.FavouritesEntity
import com.example.foodcraft.databinding.FavouriteRecipesItemBinding
import com.example.foodcraft.ui.fragments.favouriterecipes.FavouriteRecipesFragmentDirections
import com.example.foodcraft.util.GenericDiffUtil
import com.example.foodcraft.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavouriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavouriteRecipesAdapter.FavouriteRecipesViewHolder>(),
    ActionMode.Callback {

    private var favouriteRecipes = emptyList<FavouritesEntity>()

    private var multiSelection = false

    private lateinit var actionModeGlobal: ActionMode

    private lateinit var rootView: View

    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var favouriteRecipesViewHolders = arrayListOf<FavouriteRecipesViewHolder>()

    class FavouriteRecipesViewHolder(val binding: FavouriteRecipesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouriteEntity = favouritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavouriteRecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return FavouriteRecipesViewHolder(
                    FavouriteRecipesItemBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
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

        rootView = holder.itemView.rootView

        favouriteRecipesViewHolders.add(holder)

        val currentRecipe = favouriteRecipes[position]
        holder.bind(currentRecipe)

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
                false
            }
        }
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>) {
        val favouriteRecipesDiffUtil = GenericDiffUtil(favouriteRecipes, newFavouriteRecipes)
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
            changeRecipeStyle(holder, R.color.white, R.color.stroke_color)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.colorPrimary10, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(
        holder: FavouriteRecipesViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.binding.materialcardviewFavouriteRecipes.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.binding.materialcardviewFavouriteRecipes.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                actionModeGlobal.finish()
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

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favourite_recipe) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavouriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed!")

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        multiSelection = false
        selectedRecipes.clear()
        favouriteRecipesViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.white, R.color.stroke_color)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::actionModeGlobal.isInitialized) {
            actionModeGlobal.finish()
        }
    }
}