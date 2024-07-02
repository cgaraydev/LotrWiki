package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lotrwiki.adapters.ImagePagerAdapter
import com.example.lotrwiki.databinding.FragmentBookDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel

class BookDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: BookDetailsFragmentArgs by navArgs()
    private lateinit var adapter: ImagePagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetails()
        initBackButton()
    }

    private fun initDetails() {
        binding.pbBookDetailsViewPager.visibility = View.VISIBLE
        val bookId = args.bookId
        viewModel.getBookDetailsById(bookId)
        viewModel.bookDetails.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    val imageUrls = it.images
                    pbBookDetailsViewPager.visibility = View.GONE
                    tvBookDetailsName.text = it.name
                    tvBookIntroduction.text = it.introduction
                    adapter = ImagePagerAdapter(imageUrls)
                    bookDetailsViewPager.adapter = adapter
                    dotIndicatorBookDetails.attachTo(bookDetailsViewPager)
                } else {
                    pbBookDetailsViewPager.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar los detalles del libro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearBookDetails()
    }

    private fun initBackButton() {
        binding.ivBtnBackBookDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}