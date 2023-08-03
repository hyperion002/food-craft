package com.example.foodcraft.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodcraft.adapters.PagerAdapter
import com.example.foodcraft.databinding.ActivityDetailsBinding
import com.example.foodcraft.ui.fragments.ingredients.IngredientsFragment
import com.example.foodcraft.ui.fragments.instructions.InstructionsFragment
import com.example.foodcraft.ui.fragments.overview.OverviewFragment

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}