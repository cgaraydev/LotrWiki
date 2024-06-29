package com.example.lotrwiki.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.CharacterAdapter
import com.example.lotrwiki.databinding.FragmentAllCharactersBinding
import com.example.lotrwiki.utils.CharacterFilter
import com.example.lotrwiki.utils.customviews.CustomRecyclerProgress
import com.example.lotrwiki.utils.recyclerview.MarginItemDecoration
import com.example.lotrwiki.utils.setUpCustomToolbar
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllCharactersFragment : Fragment() {

    private lateinit var binding: FragmentAllCharactersBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        initCustomToolbar()
        return binding.root
    }

    private fun initCustomToolbar() {
        setUpCustomToolbar(binding.customToolbarAllCharacters, "Personajes", findNavController())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersLoad()
    }

    private fun initCharactersLoad() {
        val adapter = CharacterAdapter {
            val action =
                AllCharactersFragmentDirections.actionAllCharactersFragmentToDetailsFragment(
                    characterId = it
                )
            findNavController().navigate(action)
        }
        val recyclerView = binding.customRecyclerProgressAllCharacters.getRecyclerView()
        binding.customRecyclerProgressAllCharacters.setTitle("todos")
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(MarginItemDecoration(10))
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCharacters(CharacterFilter.All).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.customRecyclerProgressAllCharacters.observeLoadState(adapter)
            }
        }
    }
}