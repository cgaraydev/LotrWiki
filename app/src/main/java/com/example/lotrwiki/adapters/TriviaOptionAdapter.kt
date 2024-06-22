package com.example.lotrwiki.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.databinding.TriviaOptionItemBinding
import com.example.lotrwiki.model.Question

class TriviaOptionAdapter(
    var options: List<String>
) : RecyclerView.Adapter<TriviaOptionAdapter.TriviaViewHolder>() {

    inner class TriviaViewHolder(val binding: TriviaOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: String) {
            binding.tvTriviaOption.text = option
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaViewHolder {
        val binding =
            TriviaOptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TriviaViewHolder(binding)
    }

    override fun getItemCount() = options.size

    override fun onBindViewHolder(holder: TriviaViewHolder, position: Int) {
        holder.bind(options[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateOptions(newOptions: List<String>) {
        Log.d("TriviaOptionAdapter", "Updating options: $newOptions")
        options = newOptions
        notifyDataSetChanged()
    }
}