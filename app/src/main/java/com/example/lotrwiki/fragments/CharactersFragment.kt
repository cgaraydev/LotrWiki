package com.example.lotrwiki.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lotrwiki.NavGraphDirections
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.CharacterAdapter
import com.example.lotrwiki.databinding.FragmentCharactersBinding
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersLoad()
        initBackButton()
    }



    private fun initCharactersLoad() {
        val adapter = CharacterAdapter {
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
                    characterId = it
                )
            findNavController().navigate(action)
        }
        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharactersFragment.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        binding.pbCharacters.visibility = View.VISIBLE
                    }

                    is LoadState.NotLoading -> {
                        binding.pbCharacters.visibility = View.GONE
                    }

                    is LoadState.Error -> {
                        binding.pbCharacters.visibility = View.GONE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest {
                    adapter.submitData(it)
                }
            }

        }
    }

    private fun initBackButton() {
        binding.ivBtnBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    //    private fun initCharactersFiltered() {
//        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                    characterId = it
//                )
//            findNavController().navigate(action)
//        }
//        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.rvCharactersFragment.adapter = adapter
//        viewLifecycleOwner.lifecycleScope.launch {
//            adapter.loadStateFlow.collectLatest {
//                when (it.refresh) {
//                    is LoadState.Loading -> {
//                        binding.pbCharacters.visibility = View.VISIBLE
//                    }
//
//                    is LoadState.NotLoading -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//
//                    is LoadState.Error -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//                }
//            }
//
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.characters.observe(viewLifecycleOwner) {
//                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
//                }
//                viewModel.filterCharacters("faction", "evil")
//            }
//        }
//
//
//    }

//    private fun initCharactersLoad() {
//        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                    characterId = it
//                )
//            findNavController().navigate(action)
//        }
//        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(),2)
//        binding.rvCharactersFragment.adapter = adapter
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            adapter.loadStateFlow.collectLatest {
//                when(it.refresh){
//                    is LoadState.Loading -> {
//                        binding.pbCharacters.visibility = View.VISIBLE
//                    }
//                    is LoadState.NotLoading -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//                    is LoadState.Error -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//                }
//            }
//        }
//        if (showAll){
//            viewLifecycleOwner.lifecycleScope.launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED){
//                    viewModel.charactersFlow.collectLatest {
//                        adapter.submitData(it)
//                    }
//                }
//            }
//        } else {
//            viewModel.filterCharacters("faction", "evil")
//        }
//    }


    //        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(characterId = it)
//            findNavController().navigate(action)
//        }
//        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.rvCharactersFragment.adapter = adapter
//        viewModel.characters.observe(viewLifecycleOwner){
//            Log.d("CharactersFragment", "Characters observed: ${it.toString()}")
//            adapter.submitData(viewLifecycleOwner.lifecycle, it)
//        }

//        viewModel.filterCharacters("race", "Hobbits")


//    private fun updateCharacters(filteredData: List<Character>) {
//        val adapter = binding.rvCharactersFragment.adapter as CharacterAdapterFragment
//        adapter.submitData(PagingData.from(filteredData))
//    }

//    private fun showFilterDialog() {
//        val dialog = FilterDialogFragment()
//        dialog.setFilterListener {
//            currentQuery = query
//            val filteredCharacters = viewModel.filterCharacters(query)
//            updateCharacters(filteredCharacters)
//            dialog.dismiss()
//        }
//        dialog.show(parentFragmentManager, "filter_dialog")
//    }
//
//    private fun updateCharacters(filteredData: List<Character>) {
//        val adapter = binding.rvCharactersFragment.adapter as CharacterAdapterFragment
//        adapter.submitData(PagingData.from(filteredData))
//    }

//    private fun showFilterDialog() {
//        val dialog = FilterDialogFragment()
//        dialog.show(parentFragmentManager, "FilterDialogFragment")
//    }
}