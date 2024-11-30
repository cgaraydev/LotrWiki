package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentLanguageDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class LanguageDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLanguageDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: LanguageDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDetails()
        initBackButton()
    }

    private fun initDetails() {
        binding.pbLanguageDetails.visibility = View.VISIBLE
        val languageId = args.languageId
        viewModel.clearLanguageDetails()
        viewModel.getLanguagesById(languageId)
        viewModel.languageDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    pbLanguageDetails.visibility = View.GONE
                    tvLanguageDetailsName.text = it.name
                    tvLanguageDetailsHistory.setHtmlText(it.history)
                    tvLanguageDetailsOtherNames.setTextOrHide("Otros nombres", it.otherNames)
                }
            }
        }
    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearLanguageDetails()
    }
}