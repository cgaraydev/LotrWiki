package com.example.lotrwiki.utils.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.CustomRecyclerProgressBinding
import kotlinx.coroutines.flow.collectLatest

class CustomRecyclerProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding =
        CustomRecyclerProgressBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitle(title: String) {
        binding.tvCustomRecyclerProgressTitle.text = title
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.pbCustomRecyclerProgress.visibility = VISIBLE
        } else {
            binding.pbCustomRecyclerProgress.visibility = GONE
        }
    }

    fun getRecyclerView(): RecyclerView {
        return binding.rvCustomRecyclerProgress
    }

    suspend fun observeLoadState(adapter: RecyclerView.Adapter<*>) {
        if (adapter is PagingDataAdapter<*, *>) {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> showProgress(true)
                    else -> showProgress(false)
                }
            }
        }
    }
}