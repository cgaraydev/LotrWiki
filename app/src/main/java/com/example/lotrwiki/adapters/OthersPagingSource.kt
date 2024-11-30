package com.example.lotrwiki.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Other
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class OthersPagingSource : PagingSource<Int, Other>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun getRefreshKey(state: PagingState<Int, Other>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Other> {
        val currentPage = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val ref = firebaseDatabase.getReference("others")
            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()
//            val others = snapshot.children.mapNotNull { it.getValue(Other::class.java) }

            val others = snapshot.children.mapNotNull {
                val id = it.key
                val name = it.child("name").getValue(String::class.java)
                if (id != null && name != null) Other(id, name) else null
            }
            LoadResult.Page(
                data = others,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (others.size == pageSize) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}