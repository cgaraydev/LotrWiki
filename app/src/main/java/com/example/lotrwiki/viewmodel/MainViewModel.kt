package com.example.lotrwiki.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lotrwiki.adapters.CharactersPagingSource
import com.example.lotrwiki.model.Character
import com.example.lotrwiki.model.Map
import com.example.lotrwiki.model.Movie
import com.example.lotrwiki.model.Race
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
    val characterDetails: LiveData<Character?> = _characterDetails

    private val _mapList = MutableLiveData<List<Map>>()
    val mapList: LiveData<List<Map>> = _mapList

    private val _mapImage = MutableLiveData<String?>()
    val mapImage: LiveData<String?> = _mapImage

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: LiveData<Movie?> = _movieDetails

    private val _raceList = MutableLiveData<List<Race>>()
    val raceList: LiveData<List<Race>> = _raceList

    private val _raceDetails = MutableLiveData<Race?>()
    val raceDetails: LiveData<Race?> = _raceDetails

    //CHARACTER
    //Pagination
    val characters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CharactersPagingSource() }
    ).flow
        .cachedIn(viewModelScope)


//    fun loadAllCharacters() {
//        val queryRef = firebaseDatabase.getReference("articles")
//        val charactersFlow = Pager(
//            config = PagingConfig(
//                pageSize = 50,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { CharactersPagingSource(queryRef) }
//        ).flow
//            .cachedIn(viewModelScope)
//        viewModelScope.launch {
//            charactersFlow.collect {
//                _characters.postValue(it)
//            }
//        }
//    }

//    fun filterCharacters(filterField: String, filterValue: String) {
//        val queryRef = firebaseDatabase.getReference("articles").orderByChild(filterField)
//            .equalTo(filterValue)
//        val filteredCharactersFlow = Pager(
//            config = PagingConfig(
//                pageSize = 50,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { CharactersPagingSource(queryRef) }
//        ).flow
//            .cachedIn(viewModelScope)
//        viewModelScope.launch {
//            filteredCharactersFlow.collectLatest {
//                _characters.postValue(it)
//            }
//        }
//    }


//    val charactersFlow: Flow<PagingData<Character>> = Pager(
//        config = PagingConfig(
//            pageSize = 50,
//            enablePlaceholders = false
//        ),
//        pagingSourceFactory = { CharactersPagingSource() }
//    ).flow
//        .cachedIn(viewModelScope)
//
//    fun filterCharacters(filterField: String, filterValue: String) {
//        Log.d("MainViewModel", "Filtering characters by $filterField: $filterValue")
//        val queryRef = firebaseDatabase.getReference("articles").orderByChild(filterField)
//            .equalTo(filterValue)
//        val charactersFlow = Pager(
//            config = PagingConfig(
//                pageSize = 50,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { CharactersPagingSource(queryRef) }
//        ).flow
//            .cachedIn(viewModelScope)
//        viewModelScope.launch {
//            charactersFlow.collect {
//                Log.d("MainViewModel", "Characters loaded: $it")
//                _characters.postValue(it)
//            }
//        }
//    }

    fun getCharacterById(id: String) {
        val ref = firebaseDatabase.getReference("articles").child(id)
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

//    fun loadCharacterImages(id: String) {
//        val ref = firebaseDatabase.getReference("characters").child(id)
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })
//    }

    fun clearCharacterDetails() {
        _characterDetails.value = null
    }

    // QUOTE
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

    // MAPS
    fun getMapList() {
        val ref = firebaseDatabase.getReference("maps")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mapsList = mutableListOf<Map>()
                for (child in snapshot.children) {
                    val map = child.getValue(Map::class.java)
                    if (map != null) {
                        mapsList.add(map)
                    }
                }
                _mapList.value = mapsList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainViewModel", "Error loading maps: ${error.message}")
            }

        })
    }

    fun getMapById(id: String) {
        val ref = firebaseDatabase.getReference("maps").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val map = snapshot.getValue(Map::class.java)
                if (map != null) {
                    _mapImage.value = map.mapUrl.toString()
                } else {
                    Log.d("MainViewModel", "Map is null for ID: $id")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainViewModel", "Error loading map with ID: $id. Error: ${error.message}")
            }
        })
    }

    fun clearMapImage() {
        _mapImage.value = null
    }

    // MOVIE
    fun getMovieById(id: String) {
        val ref = firebaseDatabase.getReference("movies").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movie = snapshot.getValue(Movie::class.java)
                if (movie != null) {
                    val currentList = _movieList.value?.toMutableList() ?: mutableListOf()
                    currentList.add(movie)
                    _movieList.value = currentList
                    _movieDetails.value = movie
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun initMovieList() {
        val movieIds = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8")
        movieIds.forEach {
            getMovieById(it)
        }
    }

    fun clearMovieDetails() {
        _movieDetails.value = null
    }

    //RACES
    fun getRaceList() {
        val ref = firebaseDatabase.getReference("races")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val raceList = mutableListOf<Race>()
                for (child in snapshot.children) {
                    val race = child.getValue(Race::class.java)
                    if (race != null) {
                        raceList.add(race)
                    }
//                    val raceImage = child.child("image").getValue(String::class.java)
//                    val raceText = child.child("name").getValue(String::class.java)
//                    val raceId = child.child("id").getValue(String::class.java)
//                    val raceDescription = child.child("description").getValue(String::class.java)
//                    raceList.add(Race(raceImage, raceText, raceId, raceDescription))
                }
                _raceList.value = raceList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainViewModel", "Error loading raceList: ${error.message}")

            }

        })

    }

    fun getRaceDetailsById(id: String) {
        Log.d("RaceViewModel", "Fetching race details for ID: $id")
        val ref = firebaseDatabase.getReference("races").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val race = snapshot.getValue(Race::class.java)
                if (race != null) {
                    _raceDetails.value = race
                    Log.d("RaceViewModel", "Lista de razas antes de la navegación: $raceList")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun clearRaceDetails() {
        _raceDetails.value = null
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
