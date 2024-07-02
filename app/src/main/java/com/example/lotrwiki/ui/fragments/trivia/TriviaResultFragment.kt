package com.example.lotrwiki.ui.fragments.trivia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.lotrwiki.R
import com.example.lotrwiki.ui.activities.MainActivity
import com.example.lotrwiki.ui.activities.TriviaActivity
import com.example.lotrwiki.databinding.FragmentTriviaResultBinding
import com.example.lotrwiki.viewmodel.TriviaViewModel

class TriviaResultFragment : Fragment() {

    private lateinit var binding: FragmentTriviaResultBinding
    private val viewModel: TriviaViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            exitTrivia()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriviaResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResultDetails()
        initButtons()
    }



    private fun initResultDetails() {
        viewModel.score.observe(viewLifecycleOwner) {
            val scoreText = getString(R.string.score, it, viewModel.randomIndices.size)
            binding.tvTriviaScore.text = scoreText
            updateRank(it)
            updateImageRank(it)
        }
    }

    private fun updateRank(score: Int) {
        val ranking = when(score) {
            1 -> getString(R.string.rank1)
            2 -> getString(R.string.rank2)
            3 -> getString(R.string.rank3)
            4 -> getString(R.string.rank4)
            5 -> getString(R.string.rank5)
            else -> getString(R.string.rank0)
        }
        binding.tvTriviaRank.text = ranking

    }

    private fun updateImageRank(score: Int) {
        val rankingImage = when(score) {
            1 -> R.drawable.rank1
            2 -> R.drawable.rank2
            3 -> R.drawable.rank3
            4 -> R.drawable.rank4
            5 -> R.drawable.rank5
            else -> R.drawable.rank1
        }
        binding.ivTriviaRank.setImageResource(rankingImage)
    }

    private fun initButtons() {
        with(binding) {
            tvTriviaReintentar.setOnClickListener { exitTrivia() }
            tvTriviaResultToHome.setOnClickListener { exitToHome() }
        }
    }

    private fun exitTrivia() {
        val intent = Intent(requireActivity(), TriviaActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun exitToHome() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }

}