package com.example.foodcraft.ui.fragments.favouriterecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcraft.adapters.FavouriteRecipesAdapter
import com.example.foodcraft.databinding.FragmentFavouriteRecipesBinding
import com.example.foodcraft.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {
    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val favouriteRecipesAdapter: FavouriteRecipesAdapter by lazy {
        FavouriteRecipesAdapter(
            requireActivity(),
            mainViewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.favouriteRecipesAdapter = favouriteRecipesAdapter

        setUpRecyclerView(binding.recyclerViewFavouriteRecipes)

        return binding.root
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = favouriteRecipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}