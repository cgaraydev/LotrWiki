package com.example.lotrwiki.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentRaceBinding
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

    }

    private fun initRaceDetails() {
        val raceId = args.id
        viewModel.raceDetails.observe(viewLifecycleOwner){
            binding.apply {
                if (it != null) {
                    tvRaceDetailsTitle.text = it.name
                    tvRaceDetailsDescription1.text = it.description
                }
            }

        }
        viewModel.getRaceDetailsById(raceId)
    }
}
