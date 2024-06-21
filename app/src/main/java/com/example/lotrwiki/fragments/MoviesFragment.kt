package com.example.lotrwiki.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentMoviesBinding
import com.example.lotrwiki.model.Movie
import com.example.lotrwiki.viewmodel.MainViewModel

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMovieList()
        initBackButton()

    }

    private fun initMovieList() {
        viewModel.movieList.observe(viewLifecycleOwner) { movies ->
            movies.forEach {
                when (it.id) {
                    "0" -> loadImageIntoView(it, binding.ivFellowship)
                    "1" -> loadImageIntoView(it, binding.ivTwoTowers)
                    "2" -> loadImageIntoView(it, binding.ivReturnKing)
                    "3" -> loadImageIntoView(it, binding.ivUnexpectedJourney)
                    "4" -> loadImageIntoView(it, binding.ivDesolationSmaug)
                    "5" -> loadImageIntoView(it, binding.ivFiveArmies)
                    "6" -> loadImageIntoView(it, binding.ivHobbit77)
                    "7" -> loadImageIntoView(it, binding.ivLotr78)
                    "8" -> loadImageIntoView(it, binding.ivLotr80)
                    else -> {}
                }
            }
        }
        if (viewModel.movieList.value.isNullOrEmpty()) {
            viewModel.getMovieList()
        }

    }

    private fun loadImageIntoView(movie: Movie, imageView: ImageView) {
        Glide.with(this)
            .load(movie.poster)
            .into(imageView)
        imageView.setOnClickListener {
            navigateToMovieDetails(movie.id)
        }
    }

    private fun navigateToMovieDetails(movieId: String) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }


    private fun initBackButton() {
        binding.ivBtnBackMovies.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearMovieDetails()
    }
}