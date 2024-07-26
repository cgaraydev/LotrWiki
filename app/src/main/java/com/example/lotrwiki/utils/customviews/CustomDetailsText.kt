package com.example.lotrwiki.utils.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.lotrwiki.databinding.CustomDetailsTextBinding

class CustomDetailsText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = CustomDetailsTextBinding.inflate(LayoutInflater.from(context), this, true)

    private fun setTitle(title: String) {
        binding.tvCustomDetailsTitle.text = title
    }

    private fun setDescription(description: String) {
        binding.tvCustomDetailsDescription.setHtmlText(description)
    }

    fun setTextOrHide(title: String, description: String?) {
        if (!description.isNullOrEmpty()) {
            this.visibility = View.VISIBLE
            this.setTitle(title)
            this.setDescription(description)
        } else {
            this.visibility = View.GONE
        }
    }
}