package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotrwiki.databinding.ItemRaceBinding
import com.example.lotrwiki.model.Race

class RaceAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<Race, RaceAdapter.RaceViewHolder>(RaceDiffCallBack()) {

    inner class RaceViewHolder(val binding: ItemRaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(race: Race) {
            binding.tvRaceText.text = race.name
            binding.cvRace.setOnClickListener {
                onClick(race.id.toString())
            }
            Glide.with(binding.root.context)
                .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + race.poster)
                .into(binding.ivRaceImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val binding =
            ItemRaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        val race = getItem(position)
        holder.bind(race)
    }

    class RaceDiffCallBack : DiffUtil.ItemCallback<Race>() {
        override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
            return oldItem == newItem
        }

    }
}