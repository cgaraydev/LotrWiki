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
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentOtherDetailsBinding
import com.example.lotrwiki.databinding.FragmentOthersBinding
import com.example.lotrwiki.viewmodel.MainViewModel

class OtherDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOtherDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: OtherDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetails()
        initBackButton()
    }

    private fun initDetails() {
        binding.pbOtherDetails.visibility = View.VISIBLE
        val otherId = args.otherId
        viewModel.clearOtherDetails()
        viewModel.getOthersById(otherId)
        viewModel.otherDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if(it != null) {
                    if (!it.poster.isNullOrEmpty()){
                        Glide.with(requireContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + it.poster)
                            .into(ivOtherDetailsPoster)
                    }
                    pbOtherDetails.visibility = View.GONE
                    tvOtherDetailsName.text = it.name
                    tvOtherDetailsHistory.setHtmlText(it.history)
                    tvOtherDetailsOtherNames.setTextOrHide("Otros nombres", it.otherNames)
                    tvOtherDetailsOtherLocations.setTextOrHide("Ubicaciones", it.location)
                    tvOtherDetailsOtherOwner.setTextOrHide("Propietario", it.owner)
                    tvOtherDetailsOtherCreator.setTextOrHide("Creador", it.creator)
                    tvOtherDetailsOtherLanguages.setTextOrHide("Idiomas", it.languages)
                    tvOtherDetailsOtherFounder.setTextOrHide("Fundador", it.founder)
                    tvOtherDetailsOtherFounded.setTextOrHide("Fundado", it.founded)
                    tvOtherDetailsOtherLeader.setTextOrHide("Lider", it.leader)
                    tvOtherDetailsOtherHeirlooms.setTextOrHide("Artefactos", it.heirlooms)
                }
            }
        }

    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearOtherDetails()
    }
}

