package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.lotrwiki.adapters.ImagePagerAdapter
import com.example.lotrwiki.databinding.FragmentCharacterDetailsBinding
import com.example.lotrwiki.utils.customviews.CustomDetailsText
import com.example.lotrwiki.utils.customviews.CustomLinkTextView
import com.example.lotrwiki.viewmodel.MainViewModel

class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var adapter: ImagePagerAdapter

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
        val characterId = args.characterId
        viewModel.getCharacterById(characterId)
        viewModel.characterDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    tvCharacterDetailsName.text = it.name
                    if (!it.race.isNullOrEmpty()) {
                        tvCharacterDetailsRace.text = it.race
                    }
                    tvCharacterDetailsBiography.setHtmlText(it.biography)
                    tvCharacterDetailsOtherNames.setTextOrHide("Otro nombres", it.otherNames)
                    tvCharacterDetailsTitles.setTextOrHide("Títulos", it.titles)
                    tvCharacterDetailsBirth.setTextOrHide("Nacimiento", it.birth)
                    tvCharacterDetailsDeath.setTextOrHide("Muerte", it.death)
                    tvCharacterDetailsLove.setTextOrHide("Cónyuge/Amor", it.love)
                    tvCharacterDetailsFamily.setTextOrHide("Familia", it.family)
                    tvCharacterDetailsHouse.setTextOrHide("Casa", it.house)
                    tvCharacterDetailsEtimology.setTextOrHide("Etimología", it.etimology)
//                    tvCharacterDetailsOtherNames.configureView(
//                        "Otros nombres",
//                        it.otherNames.toString()
//                    )
//                    tvCharacterDetailsTitles.configureView(
//                        "Título(s)",
//                        it.titles.toString()
//                    )
//                    tvCharacterDetailsBirth.configureView(
//                        "Nacimiento",
//                        it.birth.toString()
//                    )
//                    tvCharacterDetailsDeath.configureView(
//                        "Muerte",
//                        it.death.toString()
//                    )
//                    tvCharacterDetailsLove.configureView(
//                        "Cónyuge/Amor",
//                        it.love.toString()
//                    )
//                    tvCharacterDetailsFamily.configureView(
//                        "Familia",
//                        it.family.toString()
//                    )
//                    tvCharacterDetailsHouse.configureView(
//                        "Casa",
//                        it.house.toString()
//                    )
//                    tvCharacterDetailsEtimology.configureView(
//                        "Etimología",
//                        it.etimology.toString()
//                    )
                }
            }
        }
    }

//    private fun CustomDetailsText.configureView(title: String, description: String) {
//        if (description.isNotEmpty()) {
//            this.visibility = View.VISIBLE
//            this.setTitle(title)
//            this.setDescription(description)
//        } else {
//            this.visibility = View.GONE
//        }
//    }


//    private fun initDetails() {
//        binding.pbDetailsImage.visibility = View.VISIBLE
//        val characterId = args.characterId
//        viewModel.getCharacterById(characterId)
//        viewModel.characterDetails.observe(viewLifecycleOwner) {
//            binding.apply {
//                if (it != null) {
////                    val imageUrls = it.images
//                    val characterImages = it.images
//                    pbDetailsImage.visibility = View.GONE
//                    tvDetailsName.text = it.name
////                    adapter = ImagePagerAdapter(imageUrls)
//                    adapter = ImagePagerAdapter(characterImages)
//                    viewPager2.adapter = adapter
//                    dotIndicator.attachTo(viewPager2)
//
//                    if (it.otherNames.isNullOrEmpty()) {
//                        tvDetailsOtherNames.visibility = View.GONE
//                        tvDetailsOtherNamesValue.visibility = View.GONE
//                    } else {
//                        tvDetailsOtherNames.visibility = View.VISIBLE
//                        tvDetailsOtherNamesValue.visibility = View.VISIBLE
//                        tvDetailsOtherNamesValue.text = it.otherNames!!.lowercase()
//                    }
//
//                    if (it.titles.isNullOrEmpty()) {
//                        tvDetailsTitles.visibility = View.GONE
//                        tvDetailsTitlesValue.visibility = View.GONE
//                    } else {
//                        tvDetailsTitles.visibility = View.VISIBLE
//                        tvDetailsTitlesValue.visibility = View.VISIBLE
//                        tvDetailsTitlesValue.text = it.titles!!.lowercase()
//                    }
//
//                    tvDetailsBirthValue.text = it.birth?.lowercase() ?: ""
//                    tvDetailsDeathValue.text = it.death?.lowercase() ?: ""
//                    tvDetailsFamilyValue.text = it.family?.lowercase() ?: ""
//                    tvDetailsRaceValue.text = it.race?.lowercase() ?: ""
//                    tvCharacterBiography.setHtmlText(it.biography!!)
//
//                }
//            }
//        }
//    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}