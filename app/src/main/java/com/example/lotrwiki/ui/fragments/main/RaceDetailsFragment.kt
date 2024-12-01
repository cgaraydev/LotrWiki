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
import com.example.lotrwiki.databinding.FragmentRaceDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class RaceDetailsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentRaceDetailsBinding
    private val args: RaceDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRaceDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRaceDetails()
        initBackButton()

    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRaceDetails() {
        binding.pbRaceDetails.visibility = View.VISIBLE
        val raceId = args.raceId
        viewModel.clearRaceDetails()
        viewModel.getRaceDetailsById(raceId)
        viewModel.raceDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    if (!it.poster.isNullOrEmpty()){
                        Glide.with(requireContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + it.poster)
                            .into(ivRaceDetailsImage)
                    }
                    pbRaceDetails.visibility = View.GONE
                    tvRaceDetailsTitle.text = it.name
                    tvRaceDetailsHistory.setHtmlText(it.history)
                    tvRaceOtherNames.setTextOrHide("Otros nombres", it.otherNames)
                    tvRaceOrigins.setTextOrHide("Origenes", it.origins)
                    tvRaceLocations.setTextOrHide("Ubicaciones", it.location)
                    tvRaceLanguages.setTextOrHide("Idiomas", it.languages)
                    tvRacePeople.setTextOrHide("Pueblos", it.people)
                    tvRaceMembers.setTextOrHide("Miembros destacados", it.members)
                    tvRaceLifespan.setTextOrHide("Vida", it.lifespan)
                    tvRaceCharacteristics.setTextOrHide("Caracteristicas", it.characteristics)
                    tvRaceEtymology.setTextOrHide("Etimologia", it.etymology)


                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearRaceDetails()
    }
}
