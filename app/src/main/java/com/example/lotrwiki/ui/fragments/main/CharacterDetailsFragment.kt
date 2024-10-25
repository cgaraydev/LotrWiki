package com.example.lotrwiki.ui.fragments.main

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.ImagePagerAdapter
import com.example.lotrwiki.databinding.FragmentCharacterDetailsBinding
import com.example.lotrwiki.model.Character
import com.example.lotrwiki.utils.customviews.CustomDetailsText
import com.example.lotrwiki.utils.customviews.CustomLinkTextView
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun initDetails() {
        binding.pbCharacterDetails.visibility = View.VISIBLE
        val characterId = args.characterId
        viewModel.clearCharacterDetails()
        viewModel.getCharacterById(characterId)
        viewModel.characterDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    val imageUrls = it.images

                    if (!it.poster.isNullOrEmpty()) {
                        characterDetailsPosterContainer.visibility = View.VISIBLE
                        tvCharacterDetailsImagesTitle.visibility = View.VISIBLE
                        Glide.with(requireContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + it.poster)
                            .into(ivCharacterDetailsPoster)
                        adapter = ImagePagerAdapter(imageUrls)
                        viewPager2.adapter = adapter
                        dotIndicator.attachTo(viewPager2)
                    } else {
                        characterDetailsPosterContainer.visibility = View.GONE
                    }

                    tvCharacterDetailsBiographyTitle.visibility = View.VISIBLE
                    tvInformation.visibility = View.VISIBLE
                    pbCharacterDetails.visibility = View.GONE
                    tvCharacterDetailsName.text = it.name
                    tvCharacterDetailsRace.text = it.race
                    tvCharacterDetailsBiography.setHtmlText(it.biography)
                    tvCharacterDetailsOtherNames.setTextOrHide("Otro nombres", it.otherNames)
                    tvCharacterDetailsTitles.setTextOrHide("Títulos", it.titles)
                    tvCharacterDetailsBirth.setTextOrHide("Nacimiento", it.birth)
                    tvCharacterDetailsDeath.setTextOrHide("Muerte", it.death)
                    tvCharacterDetailsLove.setTextOrHide("Cónyuge/Amor", it.love)
                    tvCharacterDetailsFamily.setTextOrHide("Familia", it.family)
                    tvCharacterDetailsHouse.setTextOrHide("Casa", it.house)
                    tvCharacterDetailsEtymology.setTextOrHide("Etimología", it.etymology)

                }
            }
        }
    }
}


//    private fun initDetails() {
//        binding.pbCharacterDetails.visibility = View.VISIBLE
//        val characterId = args.characterId
//        viewModel.clearCharacterDetails()
//        viewModel.getCharacterById(characterId)
//        observeCharacterDetails()
//
//    }
//
//    private fun observeCharacterDetails() {
//        viewModel.characterDetails.observe(viewLifecycleOwner) {
//            if (it != null) updateCharacterDetails(it)
//        }
//    }
//
//    private fun updateCharacterDetails(character: Character) {
//        with(binding) {
//            setPoster(character)
//            setBasicInfo(character)
//            setAdditionalInfo(character)
//            showDetails()
//        }
//
//    }
//
//    private fun showDetails() {
//        binding.tvCharacterDetailsBiographyTitle.visibility = View.VISIBLE
//        binding.tvInformation.visibility = View.VISIBLE
//        binding.pbCharacterDetails.visibility = View.GONE
//    }
//
//    private fun setAdditionalInfo(character: Character) {
//        binding.tvCharacterDetailsOtherNames.setTextOrHide("Otros nombres", character.otherNames)
//        binding.tvCharacterDetailsTitles.setTextOrHide("Títulos", character.titles)
//        binding.tvCharacterDetailsBirth.setTextOrHide("Nacimiento", character.birth)
//        binding.tvCharacterDetailsDeath.setTextOrHide("Muerte", character.death)
//        binding.tvCharacterDetailsLove.setTextOrHide("Cónyuge/Amor", character.love)
//        binding.tvCharacterDetailsFamily.setTextOrHide("Familia", character.family)
//        binding.tvCharacterDetailsHouse.setTextOrHide("Casa", character.house)
//        binding.tvCharacterDetailsEtimology.setTextOrHide("Etimología", character.etimology)
//    }
//
//    private fun setBasicInfo(character: Character) {
//        binding.tvCharacterDetailsName.text = character.name
//        if (!character.race.isNullOrEmpty()) {
//            binding.tvCharacterDetailsRace.text = character.race
//        }
//        binding.tvCharacterDetailsBiography.text = character.biography
//        if (!character.genre.isNullOrEmpty()) {
//            binding.ivCharacterDetailsGender.visibility = View.VISIBLE
//            setGenderIcon(character)
//        }
//    }
//
//    private fun setGenderIcon(character: Character) {
//        val genderIconRes = when (character.genre) {
//            "masculino" -> R.drawable.ic_male
//            "femenino" -> R.drawable.ic_female
//            else -> 0
//        }
//        binding.ivCharacterDetailsGender.setImageResource(genderIconRes)
//    }
//
//    private fun setPoster(character: Character) {
//        val strokeColor = if (character.faction == "bien") {
//            ContextCompat.getColor(requireContext(), R.color.white)
//        } else {
//            ContextCompat.getColor(requireContext(), R.color.red)
//        }
//        if (!character.poster.isNullOrEmpty()) {
//            binding.characterDetailsPosterContainer.visibility = View.VISIBLE
//            binding.ivCharacterDetailsPoster.strokeColor = ColorStateList.valueOf(strokeColor)
//            binding.ivCharacterDetailsPoster.strokeWidth = 4f
//            Glide.with(requireContext())
//                .load(character.poster)
//                .into(binding.ivCharacterDetailsPoster)
//        } else {
//            binding.characterDetailsPosterContainer.visibility = View.GONE
//        }
//    }
//


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

