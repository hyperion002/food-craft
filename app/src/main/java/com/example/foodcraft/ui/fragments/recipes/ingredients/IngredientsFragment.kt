package com.example.foodcraft.ui.fragments.recipes.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodcraft.adapters.IngredientsAdapter
import com.example.foodcraft.databinding.IngredientsItemBinding

class IngredientsFragment : Fragment() {

    private var _binding: IngredientsItemBinding? = null
    private val binding get() = _binding!!

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IngredientsItemBinding.inflate(inflater, container, false)

        return binding.root
    }

}