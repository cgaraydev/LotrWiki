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
import com.example.lotrwiki.adapters.LocationListAdapter
import com.example.lotrwiki.adapters.OthersListAdapter
import com.example.lotrwiki.databinding.FragmentOthersBinding
import com.example.lotrwiki.utils.setUpCustomToolbar
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout

class OthersFragment : Fragment() {

    private lateinit var binding: FragmentOthersBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: OthersListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOthersBinding.inflate(layoutInflater)
        initCustomToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExpandable(binding.tvOthersAll, binding.expandableLayoutOthers)
        initOthersLoad()
        observeOthers()


    }

    private fun initOthersLoad() {
        adapter = OthersListAdapter {
            val action =
                OthersFragmentDirections.actionOthersFragmentToOtherDetailsFragment(
                    otherId = it
                )
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

//    private fun observeOthers() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getOthers().collectLatest { pagingData ->
//                    adapter.submitData(pagingData)
//                }
//            }
//        }
//    }

    private fun observeOthers() {
        lifecycleScope.launch {
            viewModel.others.collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun initExpandable(tv: TextView, expandableLayout: ExpandableLayout) {
        tv.setOnClickListener {
            toggleExpandable(expandableLayout)
        }
    }

    private fun initCustomToolbar() {
        setUpCustomToolbar(binding.othersCustomToolbar, "Otros", findNavController())
    }

    private fun toggleExpandable(expandableLayout: ExpandableLayout) {
        if (expandableLayout.isExpanded) {
            expandableLayout.collapse()
        } else {
            expandableLayout.expand()
        }
    }
}