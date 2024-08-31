package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentLocationDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: LocationDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_details, container, false)
    }

}