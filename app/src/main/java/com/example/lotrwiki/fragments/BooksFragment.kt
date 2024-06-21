package com.example.lotrwiki.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.BookAdapter
import com.example.lotrwiki.databinding.FragmentBooksBinding
import com.example.lotrwiki.model.Book
import com.example.lotrwiki.viewmodel.MainViewModel
import net.cachapa.expandablelayout.ExpandableLayout

class BooksFragment : Fragment(), View.OnClickListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentBooksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBooksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(binding.rvBookNonPosthumous, viewModel.nonPosthumousBookList)
        initRecyclerView(binding.rvBookPosthumous, viewModel.posthumousBookList)
        initBookLists()
        initExpandableClick()
        initBackButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearBookDetails()
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        bookList: LiveData<List<Book>>
    ) {
        val adapter = BookAdapter {
            val action =
                BooksFragmentDirections.actionBooksFragmentToBookDetailsFragment(bookId = it)
            findNavController().navigate(action)
        }
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        bookList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initBookLists() {
        if (viewModel.posthumousBookList.value == null || viewModel.nonPosthumousBookList.value == null) {
            viewModel.getBookList()
        }
    }

    private fun initExpandableClick() {
        with(binding) {
            constrainPublished.setOnClickListener(this@BooksFragment)
            constrainPosthumous.setOnClickListener(this@BooksFragment)
            constrainOtherAuthors.setOnClickListener(this@BooksFragment)
        }
    }

    private fun initBackButton() {
        binding.ivBtnBackBooks.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.constrainPublished -> toggleExpandableLayouts(binding.elBookNonPosthumous)
            R.id.constrainPosthumous -> toggleExpandableLayouts(binding.elBookPosthumous)
        }
    }

    private fun toggleExpandableLayouts(expandableLayout: ExpandableLayout) {
        if (expandableLayout.isExpanded) {
            expandableLayout.collapse()
        } else {
            expandableLayout.expand()
        }
    }
}