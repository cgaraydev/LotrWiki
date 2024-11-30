package com.example.lotrwiki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.ItemBookBinding
import com.example.lotrwiki.model.Event

class EventsListAdapter(
    private val onItemClicked: (String) -> Unit
) : PagingDataAdapter<Event, EventsListAdapter.EventsFragmentViewHolder>(DIFF_CALLBACK) {

    inner class EventsFragmentViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tvItemBookName.text = event.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: EventsFragmentViewHolder, position: Int) {
        val event = getItem(position)
        if (event != null) {
            holder.bind(event)
            holder.itemView.setOnClickListener {
                onItemClicked(event.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsFragmentViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsFragmentViewHolder(binding)
    }

}