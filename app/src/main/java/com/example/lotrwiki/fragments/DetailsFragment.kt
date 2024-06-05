package com.example.lotrwiki.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetails()

    }

    private fun initDetails() {
        binding.pbDetailsImage.visibility = View.VISIBLE
        val characterId = args.characterId
        viewModel.getCharacterById(characterId)
        viewModel.characterDetails.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    Log.d("detailsfragment", it.otherNames.toString())
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
                        tvDetailsOtherNamesValue.text = it.otherNames
                    }
                    if (it.titles.isNullOrEmpty()) {
                        tvDetailsTitles.visibility = View.GONE
                        tvDetailsTitlesValue.visibility = View.GONE
                    } else {
                        tvDetailsTitles.visibility = View.VISIBLE
                        tvDetailsTitlesValue.visibility = View.VISIBLE
                        tvDetailsTitlesValue.text = it.titles
                    }

                    if (it.birth.isNullOrEmpty()){
                        tvDetailsBirth.visibility = View.GONE
                        tvDetailsBirthValue.visibility = View.GONE
                        viewDetailsSeparator.visibility = View.GONE
                    } else {
                        tvDetailsBirth.visibility = View.VISIBLE
                        tvDetailsBirthValue.visibility = View.VISIBLE
                        viewDetailsSeparator.visibility = View.VISIBLE
                        tvDetailsBirthValue.text = it.birth
                    }

                    if (it.death.isNullOrEmpty()){
                        tvDetailsDeath.visibility = View.GONE
                        tvDetailsDeathValue.visibility = View.GONE
                        viewDetailsSeparator.visibility = View.GONE
                    } else {
                        tvDetailsDeath.visibility = View.VISIBLE
                        tvDetailsDeathValue.visibility = View.VISIBLE
                        viewDetailsSeparator.visibility = View.VISIBLE
                        tvDetailsDeathValue.text = it.birth
                    }


                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearCharacterDetails()
    }

}