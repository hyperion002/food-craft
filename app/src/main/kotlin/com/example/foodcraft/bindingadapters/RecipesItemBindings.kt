package com.example.foodcraft.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodcraft.R
import com.example.foodcraft.models.Result
import com.example.foodcraft.ui.fragments.recipes.RecipesFragmentDirections
import com.google.android.material.card.MaterialCardView
import org.jsoup.Jsoup

object RecipesItemBindings {
    @BindingAdapter("onRecipeClickListener")
    @JvmStatic
    fun onRecipeClickListener(recipesItem: MaterialCardView, result: Result) {
        recipesItem.setOnClickListener {
            try {
                val action =
                    RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                recipesItem.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.d("onRecipeClickListener", e.toString())
            }
        }
    }

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
    }

    @BindingAdapter("applyVeganColor")
    @JvmStatic
    fun applyVeganColor(view: View, vegan: Boolean) {
        if (vegan) {
            when (view) {
                is TextView -> {
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                }

                is ImageView -> {
                    view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                }
            }
        }
    }

    @BindingAdapter("parseHtml")
    @JvmStatic
    fun parseHtml(textView: TextView, description: String?) {
        if (description != null) {
            val parsedDescription = Jsoup.parse(description).text()
            textView.text = parsedDescription
        }
    }
}