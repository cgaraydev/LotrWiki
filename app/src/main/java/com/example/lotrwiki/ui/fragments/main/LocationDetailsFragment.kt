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
import com.example.lotrwiki.databinding.FragmentLocationDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: LocationDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetails()
        initBackButton()
    }

    private fun initDetails() {
        binding.pbLocationDetails.visibility = View.VISIBLE
        val locationId = args.locationId
        viewModel.getLocationsById(locationId)
        viewModel.locationDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    if (!it.poster.isNullOrEmpty()) {
                        locationDetailsPosterContainer.visibility = View.VISIBLE
                        Glide.with(requireContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + it.poster)
                            .into(ivLocationDetailsPoster)
                    } else {
                        locationDetailsPosterContainer.visibility = View.GONE
                    }
                    tvLocationDetailsBiographyTitle.visibility = View.VISIBLE
                    tvInformation.visibility = View.VISIBLE
                    pbLocationDetails.visibility = View.GONE
                    tvLocationDetailsName.text = it.name
                    tvLocationDetailsBiography.setHtmlText(it.history)
                    tvLocationDetailsFounded.setTextOrHide("Fundado", it.founded)
                    tvLocationDetailsDestroyed.setTextOrHide("Destruido", it.destroyed)
                    tvLocationDetailsLocation.setTextOrHide("Ubicación", it.location)
                    tvLocationDetailsEvents.setTextOrHide("Eventos", it.events)
                    tvLocationDetailsLanguages.setTextOrHide("Idiomas", it.languages)
                    tvLocationDetailsEtymology.setTextOrHide("Etimologia", it.etymology)
                    tvLocationDetailsInhabitants.setTextOrHide("Inhabitantes", it.inhabitants)
                    tvLocationDetailsOtherNames.setTextOrHide("Otros nombres", it.otherNames)
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