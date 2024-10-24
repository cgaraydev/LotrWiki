package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.ItemBookBinding

class CharacterListAdapter (
    private val onItemClick: (String) -> Unit
    ) : PagingDataAdapter<com.example.lotrwiki.model.Character, CharacterListAdapter.CharacterFragmentViewHolder>(DIFF_CALLBACK) {

        inner class CharacterFragmentViewHolder(val binding: ItemBookBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(character: com.example.lotrwiki.model.Character) {
                binding.apply {
                    tvItemBookName.text = character.name
                }
            }
        }

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<com.example.lotrwiki.model.Character>() {
                override fun areItemsTheSame(oldItem: com.example.lotrwiki.model.Character, newItem: com.example.lotrwiki.model.Character): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: com.example.lotrwiki.model.Character, newItem: com.example.lotrwiki.model.Character): Boolean {
                    return oldItem == newItem
                }
            }
        }

        override fun onBindViewHolder(holder: CharacterFragmentViewHolder, position: Int) {
            val character = getItem(position)
            if (character != null) {
                holder.bind(character)
                holder.itemView.setOnClickListener {
                    onItemClick(character.id.toString())
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterFragmentViewHolder {
            val binding =
                ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CharacterFragmentViewHolder(binding)
        }
}