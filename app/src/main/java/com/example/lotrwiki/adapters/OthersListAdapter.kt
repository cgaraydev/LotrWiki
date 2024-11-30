package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.ItemBookBinding
import com.example.lotrwiki.model.Other

class OthersListAdapter(
    private val onItemClicked: (String) -> Unit
) : PagingDataAdapter<Other, OthersListAdapter.OthersFragmentViewHolder>(DIFF_CALLBACK) {

    inner class OthersFragmentViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(other: Other) {
            binding.tvItemBookName.text = other.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Other>() {
            override fun areItemsTheSame(oldItem: Other, newItem: Other): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Other, newItem: Other): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: OthersFragmentViewHolder, position: Int) {
        val other = getItem(position)
        if (other != null) {
            holder.bind(other)
            holder.itemView.setOnClickListener {
                onItemClicked(other.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OthersFragmentViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OthersFragmentViewHolder(binding)
    }
}