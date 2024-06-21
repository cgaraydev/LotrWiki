package com.example.lotrwiki.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.lotrwiki.databinding.FragmentZoomImageBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class MapDetailsFragment : Fragment() {

    private lateinit var binding: FragmentZoomImageBinding
    private val args: MapDetailsFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentZoomImageBinding.inflate(layoutInflater)
        Log.d("ZoomImageFragment", args.mapId + " oncreateview")
        initBackButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImage()
    }

    private fun initImage() {
        binding.progressBarZoomImage.visibility = View.VISIBLE
        val mapId = args.mapId
        viewModel.mapImage.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.progressBarZoomImage.visibility = View.GONE
                Glide.with(binding.ivZoomImageMap.context).load(it).into(binding.ivZoomImageMap)
            }
//            binding.tvMapName.text =

        }
        viewModel.getMapById(mapId)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearMapImage()
    }

    private fun initBackButton() {
        binding.ivBtnBackZoomImage.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}