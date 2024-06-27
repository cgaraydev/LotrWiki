package com.example.lotrwiki.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lotrwiki.model.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class TriviaViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _currentQuestion = MutableLiveData<Question?>()
    val currentQuestion: LiveData<Question?> = _currentQuestion

    private val _questionNumber = MutableLiveData<Int>()
    val questionNumber: LiveData<Int> = _questionNumber

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigateToResult = MutableLiveData<Boolean>()
    val navigateToResult: LiveData<Boolean> = _navigateToResult

    private val _currentIndex = MutableLiveData<Int>()
    val currentIndex: LiveData<Int> = _currentIndex

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score


    var randomIndices = generateUniqueRandomIndices()
    private var correctAnswers = 0
//    private var incorrectAnswers = 0

    init {
        _currentIndex.value = 0
        loadQuestionByIndex(randomIndices[_currentIndex.value ?: 0])
    }

    private fun loadQuestionByIndex(index: Int) {
        _isLoading.value = true
        val ref = firebaseDatabase.getReference("questions").child(index.toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val question = snapshot.getValue(Question::class.java)
                if (question != null) {
                    _currentQuestion.value = question
                    _questionNumber.value = _currentIndex.value ?: 1
                }
                _isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TriviaViewModel", "Error loading question: ${error.message}")
                _isLoading.value = false
            }
        })
    }

    private fun generateUniqueRandomIndices(): List<Int> {
        val indices = mutableSetOf<Int>()
        val random = Random
        while (indices.size < 5) {
            val newIndex = random.nextInt(20)
            if (newIndex !in indices) {
                indices.add(newIndex)
            }
        }
        Log.d("TriviaViewModel", "Random indices: $indices")


        return indices.toList()
    }

    fun nextQuestion() {
        _currentIndex.value = (_currentIndex.value ?: 0) + 1
        if ((_currentIndex.value ?: 0) < randomIndices.size) {
            loadQuestionByIndex(randomIndices[_currentIndex.value ?: 0])
        } else {
            navigateToResult()
        }
    }

    fun navigateToResult() {
        _navigateToResult.value = true
    }

    fun onNavigateToResultComplete() {
        _navigateToResult.value = false
    }

    fun checkAnswer(selectedOption: String) {
        val currentQuestionValue = _currentQuestion.value
        if (currentQuestionValue != null) {
            if (selectedOption == currentQuestionValue.correct) correctAnswers++
            _score.value = correctAnswers
        }
    }
}

//fun nextQuestion() {
//        Log.d("TriviaViewModel", "Is last question before: $isLastQuestion")
//        currentIndex++
//        _questionNumber.value = currentIndex
//        Log.d("TriviaViewModel", "Next question. Current Index: $currentIndex, Total Questions: ${randomIndices.size}")
//    _currentIndex.value = (_currentIndex.value ?: 0) + 1
//    if (_currentIndex.value ?: 0 < randomIndices.size) {
//        loadQuestionByIndex(randomIndices[_currentIndex.value ?: 0])
//    } else {
//        navigateToResult()
//    }
//        if (currentIndex == randomIndices.size) {
//            _isLastQuestion.value = true
//            isLastQuestion = true
//        }
//        if (currentIndex < randomIndices.size) {
//            _questionNumber.value = currentIndex
//            loadQuestionByIndex(randomIndices[currentIndex])
//        } else {
//            navigateToResult()
//        }
//        Log.d("TriviaViewModel", "Is last question after: $isLastQuestion")
//}

//    fun getCorrectAnswersCount(): Int = correctAnswers
//    fun getIncorrectAnswersCount(): Int = incorrectAnswers

//    private val _isLastQuestion = MutableLiveData<Boolean>()
//    val isLastQuestion: LiveData<Boolean> = _isLastQuestion

//    private var currentIndex = 0

//        _questionNumber.value = currentIndex
//        loadQuestionByIndex(randomIndices[currentIndex])

//    var isLastQuestion = false

