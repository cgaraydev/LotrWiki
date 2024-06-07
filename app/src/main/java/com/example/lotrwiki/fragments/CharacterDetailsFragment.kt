package com.example.lotrwiki.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.lotrwiki.databinding.FragmentCharacterDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel

class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetails()
        initBackButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearCharacterDetails()
    }

    private fun initDetails() {
        binding.pbDetailsImage.visibility = View.VISIBLE
        val characterId = args.characterId
        viewModel.getCharacterById(characterId)
        viewModel.characterDetails.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    pbDetailsImage.visibility = View.GONE
                    tvDetailsName.text = ""
                    tvDetailsName.text = it.name

                    Glide.with(ivDetailsPoster.context)
                        .load(it.poster)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivDetailsPoster)

                    if (it.otherNames.isNullOrEmpty()) {
                        tvDetailsOtherNames.visibility = View.GONE
                        tvDetailsOtherNamesValue.visibility = View.GONE
                    } else {
                        tvDetailsOtherNames.visibility = View.VISIBLE
                        tvDetailsOtherNamesValue.visibility = View.VISIBLE
                        tvDetailsOtherNamesValue.text = it.otherNames!!.lowercase()
                    }

                    if (it.titles.isNullOrEmpty()) {
                        tvDetailsTitles.visibility = View.GONE
                        tvDetailsTitlesValue.visibility = View.GONE
                    } else {
                        tvDetailsTitles.visibility = View.VISIBLE
                        tvDetailsTitlesValue.visibility = View.VISIBLE
                        tvDetailsTitlesValue.text = it.titles!!.lowercase()
                    }

                    tvDetailsBirthValue.text = it.birth?.lowercase() ?: ""
                    tvDetailsDeathValue.text = it.death?.lowercase() ?: ""
                    tvDetailsFamilyValue.text = it.family?.lowercase() ?: ""
                    tvDetailsRaceValue.text = it.race?.lowercase() ?: ""
                }
            }
        }
    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}