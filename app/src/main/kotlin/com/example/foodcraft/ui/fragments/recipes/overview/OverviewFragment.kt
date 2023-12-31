package com.example.foodcraft.ui.fragments.recipes.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.foodcraft.R
import com.example.foodcraft.bindingadapters.RecipesItemBindings
import com.example.foodcraft.databinding.FragmentOverviewBinding
import com.example.foodcraft.models.Result
import com.example.foodcraft.util.Constants.RECIPE_RESULT_BUNDLE
import com.example.foodcraft.util.retrieveParcelable

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val recipeBundle: Result? = arguments?.retrieveParcelable(RECIPE_RESULT_BUNDLE)

        if (recipeBundle != null) {
            binding.imageviewMain.load(recipeBundle.image)
            binding.imageviewMain.contentDescription = recipeBundle.title
            binding.textviewRecipeTitle.text = recipeBundle.title
            binding.textviewLikes.text = recipeBundle.aggregateLikes.toString()
            binding.textviewTime.text = recipeBundle.readyInMinutes.toString()
            RecipesItemBindings.parseHtml(binding.textviewSummary, recipeBundle.summary)

            setCheckColor(
                recipeBundle.vegetarian,
                binding.imageviewVegetarianCheck,
                binding.textviewVegetarianCheck
            )
            setCheckColor(
                recipeBundle.vegan,
                binding.imageviewVeganCheck,
                binding.textviewVeganCheck
            )
            setCheckColor(recipeBundle.cheap, binding.imageviewCheap, binding.textviewCheap)
            setCheckColor(
                recipeBundle.dairyFree,
                binding.imageviewDairyFree,
                binding.textviewDairyFree
            )
            setCheckColor(
                recipeBundle.glutenFree,
                binding.imageviewGlutenFree,
                binding.textviewGlutenFree
            )
            setCheckColor(
                recipeBundle.veryHealthy,
                binding.imageviewHealthy,
                binding.textviewHealthy
            )
        }

        return binding.root
    }

    private fun setCheckColor(
        attribute: Boolean?,
        checkImageView: ImageView,
        checkTextView: TextView
    ) {
        if (attribute == true) {
            checkImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            checkTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}