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
        val raceId = args.id
        viewModel.raceDetails.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    pbRaceDetails.visibility = View.GONE
                    Glide.with(requireContext()).load(it.image).into(ivRaceDetailsImage)
                    Glide.with(requireContext()).load(it.image3).into(ivRaceDetails1)
                    Glide.with(requireContext()).load(it.image2).into(ivRaceDetails2)
                    tvRaceDetailsTitle.text = it.name
                    tvRaceDetailsIntroduction.text = it.introduction
                    tvRaceDetailsBody1.text = it.body1
                    tvRaceDetailsBody2.text = it.body2
                }
            }
        }
        viewModel.getRaceDetailsById(raceId)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearRaceDetails()
    }
}
