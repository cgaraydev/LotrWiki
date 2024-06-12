package com.example.lotrwiki.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotrwiki.databinding.RaceItemBinding
import com.example.lotrwiki.model.Race

class RaceAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<Race, RaceAdapter.RaceViewHolder>(RaceDiffCallBack()) {

    inner class RaceViewHolder(val binding: RaceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(race: Race) {
            binding.tvRaceText.text = race.name
            binding.cvRace.setOnClickListener {
                onClick(race.id.toString())
            }
            Glide.with(binding.root.context).load(race.image).into(binding.ivRaceImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val binding =
            RaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        val race = getItem(position)
        holder.bind(race)
//        val race = races[position]
//        holder.bind(race)
//        holder.itemView.setOnClickListener {
//            onItemClick(race.id.toString())
//        }
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

//class RaceAdapter (
//): RecyclerView.Adapter<RaceAdapter.RaceViewHolder>() {
//    private val onItemClick: (String) -> Unit
//
//    private val diffUtil = object : DiffUtil.ItemCallback<Race>() {
//        override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, diffUtil)
//
//    inner class RaceViewHolder(val binding: RaceItemBinding) : RecyclerView.ViewHolder(binding.root)
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceAdapter.RaceViewHolder {
//        val binding = RaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RaceViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RaceAdapter.RaceViewHolder, position: Int) {
//        val race = differ.currentList[position]
//        holder.binding.tvRaceText.text = race.name
////        Glide.with(holder.itemView.context).load(race.image).into(holder.binding.ivRaceImage)
//        holder.binding.root.setOnClickListener {
//            Log.d("RaceAdapter", "Item clicked, race id: ${race.id}")
//            onItemClick(race.id.toString())
//        }
//    }
//
//    override fun getItemCount() = differ.currentList.size
//}