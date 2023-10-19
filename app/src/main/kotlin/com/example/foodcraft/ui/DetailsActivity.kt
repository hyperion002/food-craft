package com.example.foodcraft.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodcraft.R
import com.example.foodcraft.adapters.PagerAdapter
import com.example.foodcraft.data.database.entities.FavouritesEntity
import com.example.foodcraft.databinding.ActivityDetailsBinding
import com.example.foodcraft.ui.fragments.recipes.ingredients.IngredientsFragment
import com.example.foodcraft.ui.fragments.recipes.instructions.InstructionsFragment
import com.example.foodcraft.ui.fragments.recipes.overview.OverviewFragment
import com.example.foodcraft.util.Constants.RECIPE_RESULT_BUNDLE
import com.example.foodcraft.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var menuItem: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.also {
            it.add(OverviewFragment())
            it.add(IngredientsFragment())
            it.add(InstructionsFragment())
        }

        val titles = ArrayList<String>()
        titles.also {
            it.add("Overview")
            it.add("Ingredients")
            it.add("Instructions")
        }

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_BUNDLE, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favourites && !recipeSaved) {
            saveToFavourites(item)
        } else if (item.itemId == R.id.save_to_favourites && recipeSaved) {
            removeFromFavourites()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_favourites)
        checkSavedRecipes(menuItem)
        return true
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(0, args.result)
        mainViewModel.insertFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved")
        recipeSaved = true
    }

    private fun removeFromFavourites() {
        val favouritesEntity = FavouritesEntity(savedRecipeId, args.result)
        mainViewModel.deleteFavouriteRecipe(favouritesEntity)
        showSnackBar("Recipe removed favourites")
        recipeSaved = false
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipes.observe(this) { listFavouriteEntities ->
            try {
                for (savedFavouriteRecipe in listFavouriteEntities) {
                    if (savedFavouriteRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedFavouriteRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }
}