package com.example.lotrwiki.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentTolkienBinding

class TolkienFragment : Fragment() {

    private lateinit var binding: FragmentTolkienBinding
    private val wikipedia = "https://es.wikipedia.org/wiki/J._R._R._Tolkien"
    private val lotrWiki = "https://www.tolkienestate.com/es/"
    private val tolkienEstate = "https://lotr.fandom.com/wiki/Main_Page"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTolkienBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons() {
        binding.apply {
            tvWikipedia.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikipedia))
                startActivity(intent)
            }
            tvLotrWiki.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(lotrWiki))
                startActivity(intent)
            }
            tvTolkienEstate.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tolkienEstate))
                startActivity(intent)
            }
            ivBtnBackBiography.setOnClickListener {
                findNavController().navigate(R.id.action_global_homeFragment)
            }
        }
    }
}