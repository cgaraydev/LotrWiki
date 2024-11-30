package com.example.lotrwiki.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Event
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class EventsPagingSource : PagingSource<Int, Event>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        val currentPage = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val ref = firebaseDatabase.getReference("events")
            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()

            val events = snapshot.children.mapNotNull {
                val id = it.key
                val name = it.child("name").getValue(String::class.java)
                if (id != null && name != null) Event(id, name) else null
            }
            LoadResult.Page(
                data = events,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (events.size == pageSize) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
