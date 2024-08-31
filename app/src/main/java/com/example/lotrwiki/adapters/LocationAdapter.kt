package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.ItemLocationBinding
import com.example.lotrwiki.model.Location

class LocationAdapter(
    private val onItemClicked: (String) -> Unit
) :
    PagingDataAdapter<Location, LocationAdapter.LocationViewHolder>(DIFF_CALLBACK) {

    inner class LocationViewHolder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.apply {
                tvLocationNameFragment.text = location.name
                Glide.with(ivLocationFragment.context)
                    .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + location.poster)
                    .placeholder(R.drawable.tolkien_logo)
                    .into(ivLocationFragment)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        if (location != null) {
            holder.bind(location)
            holder.itemView.setOnClickListener {
                onItemClicked(location.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }
}