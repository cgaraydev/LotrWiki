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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.CharacterAdapterFragment
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

    private fun initBackButton() {
        binding.ivBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initCharactersLoad() {
        val adapter = CharacterAdapterFragment()
        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharactersFragment.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}