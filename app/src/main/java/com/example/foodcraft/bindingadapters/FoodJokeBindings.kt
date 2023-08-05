package com.example.foodcraft.bindingadapters

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.foodcraft.data.database.entities.FoodJokeEntity
import com.example.foodcraft.models.FoodJoke
import com.example.foodcraft.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBindings {
    companion object {
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when (view) {
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }

                null -> {}
            }
        }
    }
}