package com.example.lotrwiki.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotrwiki.activities.DetailsActivity
import com.example.lotrwiki.databinding.MainRvItemBinding
import com.example.lotrwiki.model.Character

class CharacterAdapter(
    val items: MutableList<Character>
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {


    inner class CharacterViewHolder(val binding: MainRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterAdapter.CharacterViewHolder {
        val binding = MainRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterAdapter.CharacterViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvMainItemName.text = item.name

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.binding.ivMainRvItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("character_id", item.id)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount() = items.size

}