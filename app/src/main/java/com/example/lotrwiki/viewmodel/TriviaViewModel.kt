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

    private var currentIndex = 0
    var randomIndices = generateUniqueRandomIndices(5)

    init {
        _questionNumber.value = currentIndex
        loadQuestionByIndex(randomIndices[currentIndex])
    }

    private fun loadQuestionByIndex(index: Int) {
        val ref = firebaseDatabase.getReference("questions").child(index.toString())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val question = snapshot.getValue(Question::class.java)
                if (question != null) {
                    _currentQuestion.value = question
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TriviaViewModel", "Error loading question: ${error.message}")
            }
        })
    }

    private fun generateUniqueRandomIndices(numIndices: Int): List<Int> {
        val indices = mutableSetOf<Int>()
        val random = Random
        while (indices.size < numIndices) {
            val newIndex = random.nextInt(10)
            if (newIndex !in indices) {
                indices.add(newIndex)
            }
        }
        Log.d("TriviaViewModel", "Random indices: $indices")
        return indices.toList()
    }

    fun nextQuestion() {
        currentIndex++
        if (currentIndex < randomIndices.size) {
            _questionNumber.value = currentIndex
            loadQuestionByIndex(randomIndices[currentIndex])
        }
    }

}