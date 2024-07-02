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
import com.example.lotrwiki.databinding.FragmentMovieDetailBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMovieDetails()
        initBackButton()

    }

//    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.clearMovieDetails()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearMovieDetails()

    }

    private fun initMovieDetails() {
        val movieId = args.movieId
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                with(binding) {
                    Glide.with(this@MovieDetailsFragment)
                        .load(it.posterBackground)
                        .into(ivMovieDetailsPoster)
                    tvMovieName.text = it.title
                    tvMovieYearValue.text = it.year
                    tvMovieDirectorValue.text = it.director.lowercase()
                    tvMovieCastValue.text = it.cast.lowercase()
                    tvMovieDurationValue.text = it.duration.lowercase()
                }
            }
        }
        viewModel.getMovieById(movieId)

    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}