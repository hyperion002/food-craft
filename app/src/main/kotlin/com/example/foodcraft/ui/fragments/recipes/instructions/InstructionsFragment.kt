package com.example.foodcraft.ui.fragments.recipes.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.foodcraft.databinding.FragmentInstructionsBinding
import com.example.foodcraft.models.Result
import com.example.foodcraft.util.Constants
import com.example.foodcraft.util.retrieveParcelable

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val recipeBundle: Result? = arguments?.retrieveParcelable(Constants.RECIPE_RESULT_BUNDLE)

        if (recipeBundle != null) {
            binding.webviewInstructions.webViewClient = object : WebViewClient() {}
            binding.webviewInstructions.loadUrl(recipeBundle.sourceUrl)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}