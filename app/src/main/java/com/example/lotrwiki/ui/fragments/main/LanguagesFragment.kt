package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.LanguageListAdapter
import com.example.lotrwiki.databinding.FragmentLanguagesBinding
import com.example.lotrwiki.utils.setUpCustomToolbar
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout

class LanguagesFragment : Fragment() {

    private lateinit var binding: FragmentLanguagesBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: LanguageListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguagesBinding.inflate(layoutInflater)
        initCustomToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExpandable(binding.tvLanguages, binding.expandableLayoutLanguages)
        initLanguagesLoad()
        observeLanguages()
    }

    private fun initLanguagesLoad() {
        adapter = LanguageListAdapter {
            val action =
                LanguagesFragmentDirections.actionLanguagesFragmentToLanguageDetailsFragment(
                    languageId = it
                )
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initExpandable(tv: TextView, expandableLayout: ExpandableLayout) {
        tv.setOnClickListener {
            toggleExpandable(expandableLayout)
        }
    }

//    private fun observeLanguages() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getLanguages().collectLatest { pagingData ->
//                    adapter.submitData(pagingData)
//                }
//            }
//        }
//    }

    private fun observeLanguages() {
        lifecycleScope.launch {
            viewModel.languages.collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun initCustomToolbar() {
        setUpCustomToolbar(
            binding.customToolbarLanguages,
            "Lenguajes y Escritura",
            findNavController()
        )
    }

    private fun toggleExpandable(expandableLayout: ExpandableLayout) {
        if (expandableLayout.isExpanded) {
            expandableLayout.collapse()
        } else {
            expandableLayout.expand()
        }
    }

}