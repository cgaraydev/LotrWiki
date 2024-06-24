package com.example.lotrwiki.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.TriviaOptionItemBinding

class TriviaOptionAdapter(
    private var options: List<String>,
    private var correctOption: String,
    private val onOptionSelected: (String) -> Unit
) : RecyclerView.Adapter<TriviaOptionAdapter.TriviaViewHolder>() {

    private var selectedOption: String? = null
    private var isOptionSelected = false

    inner class TriviaViewHolder(val binding: TriviaOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: String) {
            binding.tvTriviaOption.text = option
            binding.tvTriviaOption.isEnabled = !isOptionSelected

            val backgroundColor = getBackgroundColor(binding.cvTriviaOption.context, option)
            val textColor = getTextColor(binding.tvTriviaOption.context, option)

            binding.root.setCardBackgroundColor(backgroundColor)
            binding.tvTriviaOption.setTextColor(textColor)

            binding.tvTriviaOption.setOnClickListener {
                if (!isOptionSelected) {
                    selectedOption = option
                    isOptionSelected = true
                    notifyDataSetChanged()
                    onOptionSelected(option)
                }
            }
            showAnswerIndicators(option)
        }

        private fun showAnswerIndicators(option: String) {
            binding.ivCorrect.visibility =
                if (selectedOption != null && option == correctOption) View.VISIBLE else View.GONE
            binding.ivWrong.visibility =
                if (selectedOption != null && option != correctOption && option == selectedOption) View.VISIBLE else View.GONE
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
    fun updateOptions(newOptions: List<String>, newCorrectOption: String) {
        options = newOptions
        correctOption = newCorrectOption
        selectedOption = null
        isOptionSelected = false
        notifyDataSetChanged()
    }

    private fun getBackgroundColor(context: Context, option: String): Int {
        return if (isOptionSelected) {
            if (option == correctOption) {
                context.getColor(R.color.card_background)
            } else {
                context.getColor(R.color.card_background_disabled)
            }
        } else {
            context.getColor(R.color.card_background)
        }
    }

    private fun getTextColor(context: Context, option: String): Int {
        return if (isOptionSelected) {
            if (option == correctOption) {
                context.getColor(R.color.text_color)
            } else {
                context.getColor(R.color.text_color_disabled)
            }
        } else {
            context.getColor(R.color.text_color)

        }
    }

}