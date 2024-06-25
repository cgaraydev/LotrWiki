package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotrwiki.databinding.ItemCharacterFragmentBinding
import com.example.lotrwiki.model.Character



class CharacterAdapter(
    private val onItemClick: (String) -> Unit
) :
    PagingDataAdapter<Character, CharacterAdapter.CharacterFragmentViewHolder>(DIFF_CALLBACK) {

    inner class CharacterFragmentViewHolder(val binding: ItemCharacterFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                tvCharacterNameFragment.text = character.name
                Glide.with(ivCharacterFragment.context)
                    .load(character.poster)
                    .into(ivCharacterFragment)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
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
            ItemCharacterFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterFragmentViewHolder(binding)
    }

}
