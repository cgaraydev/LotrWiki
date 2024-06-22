package com.example.lotrwiki.fragments.trivia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.activities.TriviaActivity
import com.example.lotrwiki.adapters.TriviaOptionAdapter
import com.example.lotrwiki.databinding.FragmentTriviaQuestionBinding
import com.example.lotrwiki.viewmodel.TriviaViewModel

class TriviaQuestionFragment : Fragment() {

    private lateinit var binding: FragmentTriviaQuestionBinding
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
        binding = FragmentTriviaQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initQuestions()
        initButtons()
        initQuestionNumber()
    }

    private fun initQuestionNumber() {
        viewModel.questionNumber.observe(viewLifecycleOwner) {
            val totalQuestions = viewModel.randomIndices.size
            val questionNumber = resources.getString(R.string.question_number, it + 1, totalQuestions)
            binding.tvQuestionNumber.text = questionNumber
        }
    }

    private fun initButtons() {
        with(binding) {
            btnNextQuestion.setOnClickListener { viewModel.nextQuestion() }
            btnExitQuestion.setOnClickListener { exitTrivia() }
        }
    }


    private fun initQuestions() {
        val adapter = TriviaOptionAdapter(emptyList())
        binding.rvTriviaOptions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTriviaOptions.adapter = adapter
        viewModel.currentQuestion.observe(viewLifecycleOwner) {
            it?.let {//aqui se deberia manejar error en caso de no carga de pregunta
                Log.d("TriviaQuestionFragment", "Question: ${it.text}")
                Log.d("TriviaQuestionFragment", "Options: ${it.options}")
                binding.tvQuestionText.text = it.text
                adapter.updateOptions(it.options)
            }
        }
    }

    private fun exitTrivia() {
        val intent = Intent(requireActivity(), TriviaActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
}