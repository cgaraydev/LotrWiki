package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.ItemBookBinding
import com.example.lotrwiki.model.Language

class LanguageListAdapter(
    private val onItemClicked: (String) -> Unit
) : PagingDataAdapter<Language, LanguageListAdapter.LanguageFragmentVIewHolder>(DIFF_CALLBACK) {

    inner class LanguageFragmentVIewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(language: Language) {
            binding.tvItemBookName.text = language.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: LanguageFragmentVIewHolder, position: Int) {
        val language = getItem(position)
        if (language != null) {
            holder.bind(language)
            holder.itemView.setOnClickListener {
                onItemClicked(language.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageFragmentVIewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageFragmentVIewHolder(binding)
    }

}
