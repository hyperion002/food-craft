package com.example.foodcraft.ui.fragments.recipes.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodcraft.adapters.IngredientsAdapter
import com.example.foodcraft.databinding.FragmentIngredientsBinding
import com.example.foodcraft.models.Result
import com.example.foodcraft.util.Constants.RECIPE_RESULT_BUNDLE
import com.example.foodcraft.util.retrieveParcelable

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val recipeBundle: Result? = arguments?.retrieveParcelable(RECIPE_RESULT_BUNDLE)

        setUpRecyclerView()
        recipeBundle?.extendedIngredients?.let { ingredientsAdapter.setData(it) }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewIngredients.adapter = ingredientsAdapter
        binding.recyclerViewIngredients.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}