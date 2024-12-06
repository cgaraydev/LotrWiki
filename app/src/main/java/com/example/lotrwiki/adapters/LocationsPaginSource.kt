package com.example.lotrwiki.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lotrwiki.model.Location
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LocationsPagingSource : PagingSource<Int, Location>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {

        val currentPage = params.key ?: 0
        val pageSize = params.loadSize
        Log.d("PagingSource", "Load called - currentPage: $currentPage, pageSize: $pageSize")


        return try {

            val ref = firebaseDatabase.getReference("locations")
//            val snapshot = ref.orderByKey().limitToFirst(pageSize).get().await()
//            val locations = snapshot.children.mapNotNull { it.getValue(Location::class.java) }
            val snapshot = withContext(Dispatchers.IO){
                ref.orderByKey().limitToFirst(pageSize).get().await()
            }
            val locations = snapshot.children.mapNotNull {
                val id = it.key
                val name = it.child("name").getValue(String::class.java)
                if (id != null && name != null) Location(id, name) else null

            }

            Log.d("PagingSource", "Loaded ${locations.size} locations")
            locations.forEach { Log.d("PagingSource", "Location loaded: ${it.id}, ${it.name}") }


            LoadResult.Page(
                data = locations,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (locations.size == pageSize) currentPage + 1 else null
            )

        } catch (e: Exception) {
            Log.e("PagingSource", "Error loading data: ${e.message}", e)
            LoadResult.Error(e)
        }
    }
}