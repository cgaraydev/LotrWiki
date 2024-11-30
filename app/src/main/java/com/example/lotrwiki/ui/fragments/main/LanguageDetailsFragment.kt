package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentLanguageDetailsBinding


class LanguageDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLanguageDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

}