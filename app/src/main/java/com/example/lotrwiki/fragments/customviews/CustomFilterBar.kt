package com.example.lotrwiki.fragments.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.lotrwiki.databinding.CustomFilterbarBinding

class CustomFilterBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = CustomFilterbarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setViewAllClickListener(listener: OnClickListener) {
        binding.tvViewAll.setOnClickListener(listener)
    }

    fun setFilterClickListener(listener: OnClickListener) {
        binding.ivFilterCharacters.setOnClickListener(listener)
    }

    fun setViewAllVisibility(visibility: Int) {
        binding.tvViewAll.visibility = visibility
    }
}