package com.example.lotrwiki.fragments.trivia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.activities.TriviaActivity
import com.example.lotrwiki.adapters.TriviaOptionAdapter
import com.example.lotrwiki.databinding.FragmentTriviaQuestionBinding
import com.example.lotrwiki.viewmodel.TriviaViewModel
import kotlinx.coroutines.launch

class TriviaQuestionFragment : Fragment() {

    private lateinit var binding: FragmentTriviaQuestionBinding
    private val viewModel: TriviaViewModel by activityViewModels()
//    private var countDownTimer: CountDownTimer? = null

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
        initNavToResult()
//        startTimer()
    }

    private fun initQuestions() {
        val adapter = TriviaOptionAdapter(emptyList(), "") { selectedOption ->
            viewModel.checkAnswer(selectedOption)
            binding.btnNextQuestion.isEnabled = true
            binding.btnNextQuestion.alpha = 1.0f
//            countDownTimer?.cancel()
        }
        binding.rvTriviaOptions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTriviaOptions.adapter = adapter
        viewModel.currentQuestion.observe(viewLifecycleOwner) {
            it?.let { question ->
                binding.tvQuestionText.text = question.text.lowercase()
                adapter.updateOptions(question.options, question.correct)
//                startTimer()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbTriviaOptions.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

private fun initButtons() {
    with(binding) {
        btnNextQuestion.isEnabled = false
        btnNextQuestion.alpha = 0.5f

        viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
            if (index != null) {
                val isLastQuestion = index == viewModel.randomIndices.size - 1
                btnNextQuestion.text = if (isLastQuestion) {
                    getString(R.string.finish)
                } else {
                    getString(R.string.next_question)
                }

                btnNextQuestion.setOnClickListener {
                    if (isLastQuestion) {
                        viewModel.navigateToResult()
                    } else {
                        viewModel.nextQuestion()
                        resetAdapter()
                        btnNextQuestion.isEnabled = false
                        btnNextQuestion.alpha = 0.5f
                    }
                }
            }
        }

        btnExitQuestion.setOnClickListener { exitTrivia() }
    }
}

    @SuppressLint("NotifyDataSetChanged")
    private fun resetAdapter() {
        (binding.rvTriviaOptions.adapter as? TriviaOptionAdapter)?.apply {
            notifyDataSetChanged()
        }
    }

    private fun initQuestionNumber() {
        viewModel.questionNumber.observe(viewLifecycleOwner) {
            val totalQuestions = viewModel.randomIndices.size
            val questionNumber =
                resources.getString(R.string.question_number, it + 1, totalQuestions)
            binding.tvQuestionNumber.text = questionNumber
        }
    }

    private fun exitTrivia() {
        val intent = Intent(requireActivity(), TriviaActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun initNavToResult() {
        viewModel.navigateToResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_triviaQuestionFragment_to_triviaResultFragment)
                viewModel.onNavigateToResultComplete()
            }
        }
    }

//    private fun startTimer() {
//        val totalTime = 10000L // 10 seconds
//        val countDownTimer = object : CountDownTimer(totalTime, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                binding.tvTimer.text = "${millisUntilFinished / 1000}s"
//            }
//
//            override fun onFinish() {
//                binding.tvTimer.text = "Time's up!"
//                // Desactivar el botón "Siguiente" y mostrar un mensaje de que el tiempo se ha agotado.
//                binding.btnNextQuestion.isEnabled = false
//                binding.btnNextQuestion.alpha = 0.5f
//            }
//        }
//        countDownTimer.start()
//    }
}

//private fun initButtons() {
//    with(binding) {
//        viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
//            if (index != null) {
//                if (index == viewModel.randomIndices.size - 1) {
//                    btnNextQuestion.text = getString(com.example.lotrwiki.R.string.finish)
//                } else {
//                    btnNextQuestion.text = getString(com.example.lotrwiki.R.string.next_question)
//                }
//            }
//        }
//
//            if (viewModel.isLastQuestion) {
//                btnNextQuestion.text = getString(R.string.finish)
//            } else {
//                btnNextQuestion.text = getString(R.string.next_question)
//            }
//        btnNextQuestion.setOnClickListener {
//            viewModel.nextQuestion()
//            resetAdapter()
//            root.invalidate()
//        }
//        btnExitQuestion.setOnClickListener { exitTrivia() }
//    }
//}


//    private fun initButtons(){
//        with(binding) {
//            viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
//                if (index != null) {
//                    if (index == viewModel.randomIndices.size - 1 && btnNextQuestion.text != getString(R.string.finish)) {
//                        btnNextQuestion.text = getString(R.string.finish)
//                    } else if (index != viewModel.randomIndices.size - 1 && btnNextQuestion.text != getString(R.string.next_question)) {
//                        btnNextQuestion.text = getString(R.string.next_question)
//                    }
//                }
//            }
//            btnNextQuestion.setOnClickListener {
//                viewModel.nextQuestion()
//                resetAdapter()
//                root.invalidate()
//            }
//            btnExitQuestion.setOnClickListener { exitTrivia() }
//        }
//    }


//    private fun initButtons() {
//        with(binding) {
//            viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
//                if (index != null) {
//                    if (index == viewModel.randomIndices.size - 1) {
//                        btnNextQuestion.text = getString(R.string.finish)
//                    } else {
//                        btnNextQuestion.text = getString(R.string.next_question)
//                    }
//                }
//            }
//            btnNextQuestion.setOnClickListener {
//                viewModel.nextQuestion()
//                resetAdapter()
//                root.invalidate()
//            }
//            btnExitQuestion.setOnClickListener { exitTrivia() }
//        }
//    }



//    private fun initQuestions() {
//        val adapter = TriviaOptionAdapter(emptyList(), "") {
//            viewModel.checkAnswer(it)
//        }
//        binding.rvTriviaOptions.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvTriviaOptions.adapter = adapter
//        viewModel.currentQuestion.observe(viewLifecycleOwner) {
//            it?.let {//aqui se deberia manejar error en caso de no carga de pregunta
//                binding.tvQuestionText.text = it.text
//                adapter.updateOptions(it.options, it.correct)
//            }
//        }
//        viewModel.isLoading.observe(viewLifecycleOwner) {
//            binding.pbTriviaOptions.visibility = if (it) View.VISIBLE else View.GONE
//        }
//    }

//    private fun initButtons() {
//        with(binding) {
//            viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
//                if (index != null) {
//                    val isLastQuestion = index == viewModel.randomIndices.size - 1
//                    btnNextQuestion.text = if (isLastQuestion) {
//                        getString(R.string.finish)
//                    } else {
//                        getString(R.string.next_question)
//                    }
//
//                    btnNextQuestion.setOnClickListener {
//                        if (isLastQuestion) {
//                            viewModel.navigateToResult()
//                        } else {
//                            viewModel.nextQuestion()
//                            resetAdapter()
//                        }
//                    }
//                }
//            }
//
//            btnExitQuestion.setOnClickListener { exitTrivia() }
//        }
//    }