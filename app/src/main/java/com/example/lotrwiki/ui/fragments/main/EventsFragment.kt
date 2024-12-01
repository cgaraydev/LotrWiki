package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.adapters.EventsListAdapter
import com.example.lotrwiki.databinding.FragmentEventsBinding
import com.example.lotrwiki.utils.setUpCustomToolbar
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout

class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private lateinit var adapter: EventsListAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(layoutInflater)
        initCustomToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExpandable(binding.tvEventsAll, binding.expandableLayoutEvents)
        initEventsLoad()
        observeEvents()
    }

    private fun initEventsLoad() {
        adapter = EventsListAdapter {
            val action = EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment(
                eventId = it
            )
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getEvents().collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun initExpandable(tv: TextView, expandableLayout: ExpandableLayout) {
        tv.setOnClickListener {
            toggleExpandable(expandableLayout)
        }
    }

    private fun initCustomToolbar() {
        setUpCustomToolbar(binding.eventsCustomToolbar, "Eventos/Batallas", findNavController())
    }

    private fun toggleExpandable(expandableLayout: ExpandableLayout) {
        if (expandableLayout.isExpanded) {
            expandableLayout.collapse()
        } else {
            expandableLayout.expand()
        }
    }
}