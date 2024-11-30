package com.example.lotrwiki.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Language
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class LanguagesPagingSource : PagingSource<Int, Language>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getRefreshKey(state: PagingState<Int, Language>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Language> {
        val currentPage = params.key ?: 0
        val pageSize = params.loadSize

        return try {

            val ref = firebaseDatabase.getReference("languages")
            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()

            val languages = snapshot.children.mapNotNull {
                val id = it.key
                val name = it.child("name").getValue(String::class.java)
                if (id != null && name != null) Language(id, name) else null
            }
            LoadResult.Page(
                data = languages,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (languages.size == pageSize) currentPage + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
