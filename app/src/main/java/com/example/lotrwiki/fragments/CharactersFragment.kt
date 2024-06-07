package com.example.lotrwiki.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.CharacterAdapterFragment
import com.example.lotrwiki.databinding.FragmentCharactersBinding
import com.example.lotrwiki.model.Character
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCharactersBinding
//    private var currentQuery: String = ""

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
//        initFabSearch()
    }

    private fun initBackButton() {
        binding.ivBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initCharactersLoad() {
        val adapter = CharacterAdapterFragment {
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(characterId = it)
            findNavController().navigate(action)
        }
        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharactersFragment.adapter = adapter

//        binding.rvCharactersFragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy > 0){
//                    for (i in 0 until recyclerView.childCount){
//                        val view = recyclerView.getChildAt(i)
//                        view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.my_slide_up))
//                    }
//                } else if (dy < 0){
//                    for (i in 0 until recyclerView.childCount){
//                        val view = recyclerView.getChildAt(i)
//                        view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.my_slide_down))
//                    }
//                }
//            }
//        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

//    override fun onFilter(query: String) {
//
//    }

//    override fun onFilter(query: String) {
//        viewModel.filterCharacters(query)
//    }

    //    private fun initFabSearch() {
//        binding.fabCharacterFragment.setOnClickListener {
//            showFilterDialog()
//        }
//    }

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