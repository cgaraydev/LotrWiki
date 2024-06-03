package com.example.lotrwiki.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotrwiki.activities.DetailsActivity
import com.example.lotrwiki.databinding.CharacterItemBinding
import com.example.lotrwiki.model.Character

class CharacterAdapter
//    val items: MutableList<Character>
 : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    inner class CharacterViewHolder(val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterAdapter.CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.binding.tvMainItemName.text = item.name

        Glide.with(holder.itemView.context)
            .load(item.poster)
            .into(holder.binding.ivMainRvItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("character_id", item.id)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount() = differ.currentList.size
    fun submitList(list: List<Character>) {
        differ.submitList(list)
    }

}