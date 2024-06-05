package com.example.lotrwiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.lotrwiki.adapters.CharactersPagingSource
import com.example.lotrwiki.model.Character
import com.example.lotrwiki.model.Location
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _quotes = MutableLiveData<String>()
    val quotes: LiveData<String> = _quotes

    private val _characterDetails = MutableLiveData<Character?>()
    val characterDetails: MutableLiveData<Character?> = _characterDetails


    val characters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CharactersPagingSource() }
    ).flow
        .cachedIn(viewModelScope)

    fun getRandomQuote() {
        val ref = firebaseDatabase.getReference("quotes")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val quoteList = mutableListOf<String>()
                for (data in snapshot.children) {
                    val quote = data.getValue<String>()
                    quote?.let {
                        quoteList.add(it)
                    }
                }
                val randomQuote = quoteList.randomOrNull()
                randomQuote?.let {
                    _quotes.value = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _quotes.value = "Error al cargar la cita"
            }
        })
    }

    fun getCharacterById(id:String){
        val ref = firebaseDatabase.getReference("characters").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val character = snapshot.getValue(Character::class.java)
                if (character != null) {
                    _characterDetails.value = character
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun clearCharacterDetails() {
        _characterDetails.value = null
    }

//    private val originalCharacters = mutableListOf<Character>() // Store original characters
//
//    fun filterCharacters(query: String): List<Character> {
//        if (query.isEmpty()) {
//            return originalCharacters.toList() // Return original characters if no filter
//        } else {
//            val filteredCharacters = originalCharacters.filter { character ->
//                character.name?.lowercase()?.contains(query.lowercase()) ?: false
//            }
//            return filteredCharacters
//        }
//    }


//    fun loadCharacters() {
//        val ref = firebaseDatabase.getReference("characters")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val charactersList = mutableListOf<Character>()
//                for (child in snapshot.children) {
//                    val character = child.getValue(Character::class.java)
//                    if (character != null) {
//                        charactersList.add(character)
//                    }
//                }
//                _characters.value = charactersList.shuffled().toMutableList()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
//    }

//    private fun fetchCharacters(charactersId: List<String>) {
//        val ref = firebaseDatabase.getReference("characters")
//        val charactersList = mutableListOf<Character>()
//        val charactersCount = charactersId.size
//
//        charactersId.forEach {
//            ref.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        val name = snapshot.child("name").getValue(String::class.java)
//                        val imageUrl = snapshot.child("imageUrl").getValue(String::class.java)
//                        val character = Character(it, name, imageUrl)
//                        charactersList.add(character)
//
//                        if (charactersList.size == charactersCount) {
//                            _characters.value = charactersList
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })
//        }
//    }
//
//    fun loadRandomCharacters() {
//        val characterIds = (0..111).shuffled().take(10).map { it.toString() }
//        fetchCharacters(characterIds)
//    }
//
//    fun loadLocations() {
//        val ref = firebaseDatabase.getReference("locations")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val locationsList = mutableListOf<Location>()
//                for (child in snapshot.children) {
//                    val item = child.getValue(Location::class.java)
//                    if (item != null) {
//                        locationsList.add(item)
//                    }
//                }
//                _locations.value = locationsList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })
//    }
}
