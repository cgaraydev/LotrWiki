package com.example.lotrwiki.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.MapAdapter
import com.example.lotrwiki.databinding.FragmentMapsBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class MapsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMapsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        initCustomToolbar()
        return binding.root
    }

    private fun initCustomToolbar() {
        binding.MapsCustomToolbar.setTitle("Mapas")
        binding.MapsCustomToolbar.setBackButtonClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMaps()
    }

    private fun initMaps() {
        val adapter = MapAdapter {
            val action = MapsFragmentDirections.actionMapsFragmentToZoomImageFragment(mapId = it)
            findNavController().navigate(action)
        }
        binding.rvMapsFragment.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMapsFragment.adapter = adapter
        Log.d("MapsFragment", "RecyclerView initialized")
        viewModel.mapList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.getMapList()
    }
}