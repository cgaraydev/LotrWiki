package com.example.lotrwiki.fragments

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


class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: MovieDetailFragmentArgs by navArgs()


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

    private fun initMovieDetails() {
        val movieId = args.movieId
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                with(binding) {
                    Glide.with(this@MovieDetailFragment)
                        .load(it.posterBackground)
                        .into(ivMovieDetailsPoster)
                    tvMovieName.text = it.title
                    tvMovieYearValue.text = it.year
                    tvMovieDirectorValue.text = it.director
                    tvMovieCastValue.text = it.cast
                    tvMovieDurationValue.text = it.duration
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