package com.example.lotrwiki.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Character
import com.example.lotrwiki.utils.CharacterFilter
import com.google.firebase.database.FirebaseDatabase
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


