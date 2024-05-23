package com.example.lotrwiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.lotrwiki.model.Character

class DetailsViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _characterDetails = MutableLiveData<Character>()
    val characterDetails: LiveData<Character> = _characterDetails

    fun loadCharacterDetails(id: String) {
        val ref = firebaseDatabase.getReference("characters").child(id)
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val character = snapshot.getValue(Character::class.java)
                _characterDetails.value = character
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}