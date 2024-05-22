package com.example.lotrwiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lotrwiki.model.Character
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _characters = MutableLiveData<MutableList<Character>>()
    val characters: LiveData<MutableList<Character>> = _characters

    fun loadCharacters() {
        val ref = firebaseDatabase.getReference("characters")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val charactersList = mutableListOf<Character>()
                for (child in snapshot.children) {
                    val character = child.getValue(Character::class.java)
                    if (character != null) {
                        charactersList.add(character)
                    }
                }
                _characters.value = charactersList.shuffled().toMutableList()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun fetchCharacters(charactersId: List<String>) {
        val ref = firebaseDatabase.getReference("characters")
        val charactersList = mutableListOf<Character>()
        val charactersCount = charactersId.size

        charactersId.forEach {
            ref.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java)
                        val imageUrl = snapshot.child("imageUrl").getValue(String::class.java)
                        val character = Character(it, name, imageUrl)
                        charactersList.add(character)

                        if (charactersList.size == charactersCount) {
                            _characters.value = charactersList
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun loadRandomCharacters() {
        val characterIds = (0..31).shuffled().take(10).map { it.toString() }
        fetchCharacters(characterIds)
    }

}