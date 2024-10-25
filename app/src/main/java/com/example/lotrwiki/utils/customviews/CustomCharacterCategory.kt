package com.example.lotrwiki.utils.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.CustomCharactersExpandableBinding

class CustomCharacterCategory @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding =
        CustomCharactersExpandableBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        binding.expandableLayout.collapse()
        binding.headerLayout.setOnClickListener {
            toggleExpandableLayout()
        }
    }

    fun setTitle(title: String) {
        binding.tvCategoryTitle.text = title
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun toggleExpandableLayout() {
        if (binding.expandableLayout.isExpanded) {
            binding.expandableLayout.collapse()
        } else {
            binding.expandableLayout.expand()
        }
    }

    fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

}


//    fun setOnHeaderClickListener(onClick: () -> Unit) {
//        binding.headerLayout.setOnClickListener {
//            toggleExpandableLayout()
//            onClick()
//        }
//    }
//
//    fun isExpanded() = binding.expandableLayout.isExpanded
