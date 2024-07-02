package com.example.lotrwiki.ui.fragments.trivia

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lotrwiki.R
import com.example.lotrwiki.ui.activities.MainActivity
import com.example.lotrwiki.databinding.FragmentTriviaIntroBinding

class TriviaIntroFragment : Fragment() {

    private lateinit var binding: FragmentTriviaIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriviaIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNav()
        binding.tvTriviaIntroExit.setOnClickListener {
            val intent = Intent (activity, MainActivity::class.java)
            activity?.finish()
            startActivity(intent)
        }
    }

    private fun initNav() {
        binding.btnStartNavTrivia.setOnClickListener {
            findNavController().navigate(R.id.action_triviaIntroFragment_to_triviaQuestionFragment)
        }
    }

}