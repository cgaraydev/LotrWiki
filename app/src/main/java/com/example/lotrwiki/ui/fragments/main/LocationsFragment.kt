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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.adapters.CharacterListAdapter
import com.example.lotrwiki.adapters.LocationListAdapter
import com.example.lotrwiki.databinding.FragmentLocationsBinding
import com.example.lotrwiki.utils.recyclerview.MarginItemDecoration
import com.example.lotrwiki.utils.setUpCustomToolbar
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout


class LocationsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLocationsBinding
    private lateinit var adapter: LocationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(layoutInflater)
        initCustomToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExpandable(binding.tvLocationsCities, binding.expandableLayoutCities)
        initLocationsLoad()
        observeLocations()
    }

    private fun initLocationsLoad() {
        adapter = LocationListAdapter {
            val action =
                LocationsFragmentDirections.actionLocationsFragmentToLocationDetailsFragment(
                    locationId = it
                )
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeLocations() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLocations().collectLatest { pagingData ->
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
        setUpCustomToolbar(binding.customToolbar, "Ubicaciones", findNavController())
    }

    private fun toggleExpandable(expandableLayout: ExpandableLayout) {
        if (expandableLayout.isExpanded) {
            expandableLayout.collapse()
        } else {
            expandableLayout.expand()
        }

    }

    //CONSIDERAR
//        private fun toggleExpandable(expandableLayout: ExpandableLayout) {
//            if (expandableLayout.isExpanded) {
//                expandableLayout.collapse()
//                binding.ivArrowDown.setImageResource(R.drawable.ic_down) // Cambiar icono de flecha
//            } else {
//                expandableLayout.expand()
//                binding.ivArrowDown.setImageResource(R.drawable.ic_up) // Cambiar icono de flecha
//            }
//        }


//    private fun initCustomToolbar() {
//        setUpCustomToolbar(binding.locationsCustomToolbar, "Ubicaciones", findNavController())
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initLocationsLoad()
//    }

//    private fun initLocationsLoad() {
//        val adapter = LocationAdapter {
//            val action =
//                LocationsFragmentDirections.actionLocationsFragmentToLocationDetailsFragment(
//                    locationId = it
//                )
//            findNavController().navigate(action)
//        }
//        val recyclerView = binding.customRecyclerProgressLocations.getRecyclerView()
//        binding.customRecyclerProgressLocations.setTitle("")
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(MarginItemDecoration(10))
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getLocations().collectLatest {
//                    adapter.submitData(it)
//                }
//            }
//        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                binding.customRecyclerProgressLocations.observeLoadState(adapter)
//
//            }
//        }
//    }
}