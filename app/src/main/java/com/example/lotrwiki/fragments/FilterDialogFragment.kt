package com.example.lotrwiki.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.lotrwiki.databinding.FragmentFilterDialogBinding


class FilterDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentFilterDialogBinding
    private var filterListener: FilterListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    fun setFilterListener(listener: FilterListener?) {
        filterListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val query = s.toString()
                    (activity as? FilterListener)?.onFilter(query)
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }
        }
    }
}

interface FilterListener {
    fun onFilter(query: String)
}