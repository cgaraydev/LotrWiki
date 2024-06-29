package com.example.lotrwiki.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.ItemMapBinding
import com.example.lotrwiki.model.Map

class MapAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<Map, MapAdapter.MapsViewHolder>(MapDiffCallBack()) {

    inner class MapsViewHolder(val binding: ItemMapBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(map: Map) {
            binding.tvMapItemName.text = map.name
            binding.tvMapItemName.setOnClickListener {
                onClick(map.id.toString())
                Log.d("MapAdapter", "Map clicked: ${map.name}")
            }
            Log.d("MapAdapter", "Binding map: ${map.name}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapAdapter.MapsViewHolder {
        val binding = ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val map = getItem(position)
        holder.bind(map)
        Log.d("MapAdapter", "Binding map: ${map.name}")
    }

    class MapDiffCallBack : DiffUtil.ItemCallback<Map>() {
        override fun areItemsTheSame(oldItem: Map, newItem: Map): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Map, newItem: Map): Boolean {
            return oldItem == newItem
        }
    }
}

//    private val diffUtil = object : DiffUtil.ItemCallback<Map>() {
//        override fun areItemsTheSame(oldItem: Map, newItem: Map): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Map, newItem: Map): Boolean {
//            return oldItem == newItem
//        }
//    }
//    private val differ = AsyncListDiffer(this, diffUtil)

//    inner class MapsViewHolder(val binding: MapItemBinding) :
//        RecyclerView.ViewHolder(binding.root)

//    override fun getItemCount() = differ.currentList.size

//    override fun onBindViewHolder(holder: MapAdapter.MapsViewHolder, position: Int) {
//        val map = differ.currentList[position]
//        holder.binding.tvMapItemName.text = map.name
//        holder.binding.tvMapItemName.setOnClickListener {
//            onClick(map.name.toString())
//        }
//
//    }