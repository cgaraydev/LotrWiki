package com.example.lotrwiki.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Character
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class CharactersPagingSource : PagingSource<Int, Character>() {

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
//            val ref = firebaseDatabase.getReference("articles")
//            val query = if (onlyFeatured) {
//                ref.orderByChild("featured").equalTo(true).limitToFirst(pageSize)
//            } else {
//                ref.orderByKey().limitToFirst(pageSize)
//            }
//            val snapshot = query.get().await()
//            val characters = snapshot.children.mapNotNull { it.getValue(Character::class.java) }

            val ref = firebaseDatabase.getReference("articles")
            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()
            val characters = snapshot.children.mapNotNull { it.getValue(Character::class.java) }

//            val snapshot = queryRef!!.limitToFirst(pageSize).get().await()
//            val characters = snapshot.children.mapNotNull { it.getValue(Character::class.java) }


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