package com.example.lotrwiki.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Character
import com.example.lotrwiki.utils.CharacterFilter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await



class CharactersPagingSource(
    private val filter: CharacterFilter
) : PagingSource<Int, Character>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        val currentPage = params.key ?: 0
        val pageSize = params.loadSize

        return try {

            val ref = firebaseDatabase.getReference("characters")
            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()
            val characters = snapshot.children.mapNotNull { it.getValue(Character::class.java) }
                .filter { character -> filter.filter(character) }
//            val charactersShuffled = characters.shuffled()

            LoadResult.Page(
                data = characters,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (characters.size == pageSize) currentPage + 1 else null
            )
        } catch (e: Exception) {
            Log.e("CharactersPagingSource", "Error loading characters", e)
            LoadResult.Error(e)
        }
    }
}

//            val sortedCharacters = characters.sortedBy { it.id }


//            val query = ref.orderByChild("id").limitToFirst(pageSize)
//                data = characters,


//            val query = if (isFiltered){
//                ref.orderByChild("featured").equalTo(true)
//            } else {
//                ref.orderByKey()
//            }
//            val snapshot = query.limitToFirst(pageSize).get().await()


//            val ref = firebaseDatabase.getReference("articles")
//            val query = ref.orderByChild(category).limitToFirst(pageSize)
//            val query = ref.orderByChild("category").equalTo(category).limitToFirst(pageSize)
//            val snapshot = query.get().await()

//            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()
//            val characters = snapshot.children.mapNotNull { it.getValue(Character::class.java) }

//class CharactersPagingSource(
//    private val filter: CharacterFilter.ByTag
//) : PagingSource<String, Character>() {
//
//    private val firestore = FirebaseFirestore.getInstance()
//    override fun getRefreshKey(state: PagingState<String, Character>): String? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<String>): LoadResult<String, Character> {
//
//        val currentPageKey = params.key ?: ""
//        val pageSize = params.loadSize
//
//        return try {
//            val query = firestore.collection("characters")
////                .apply {
////                    when (filter) {
////                        is CharacterFilter.ByTag -> whereArrayContains("tags", filter.tag)
////                        is CharacterFilter.ByFaction -> whereEqualTo("faction", filter.faction)
////                        is CharacterFilter.All -> orderBy("name")
////                        else -> {}
////                    }
////                }
//                .whereArrayContains("tags", filter.tag)
//                .orderBy("name")
//                .limit(pageSize.toLong())
//                .apply {
//                    if (currentPageKey.isNotEmpty()) {
////                        startAfter(currentPageKey.takeIf { it.isNotEmpty() })
//                        startAfter(currentPageKey)
//                    }
//                }
//
//            val snapshot = query.get().await()
//            val characters = snapshot.toObjects(Character::class.java)
//            val lastVisible = snapshot.documents.lastOrNull()?.id
//
//            LoadResult.Page(
//                data = characters,
//                prevKey = null,
//                nextKey = lastVisible
//            )
//        } catch (e: Exception) {
//            Log.e("CharactersPagingSource", "Error loading characters", e)
//            LoadResult.Error(e)
//        }
//    }
//
//    override val keyReuseSupported: Boolean = true
//}
