package com.example.lotrwiki.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.FragmentHomeBinding
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        initQuotes()
        initChangingQuotes()
        viewModel.getRandomQuote()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }

    private fun initChangingQuotes() {
        job?.cancel()
        job = lifecycleScope.launch {
            while (isActive) {
                delay(7000)
                viewModel.getRandomQuote()
            }
        }
    }

    private fun initQuotes() {
        binding.tvQuotes.setFactory {
            val textView = TextView(requireContext())
            textView.gravity = Gravity.CENTER
            textView.textSize = 14f
            textView.typeface = ResourcesCompat.getFont(requireContext(), R.font.aniron)
            textView.setTextColor(Color.WHITE)
            textView
        }
        viewModel.quotes.observe(viewLifecycleOwner) {
            binding.tvQuotes.setText(it)
        }
    }

    private fun initMenu() {
        binding.ivMenuButton.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
                    closeDrawer()
                    true
                }

                R.id.locations -> {
                    findNavController().navigate(R.id.action_homeFragment_to_locationsFragment)
                    closeDrawer()
                    true
                }

                R.id.movies -> {
                    findNavController().navigate(R.id.action_homeFragment_to_moviesFragment)
                    closeDrawer()
                    true
                }

                R.id.maps -> {
                    findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
                    closeDrawer()
                    true
                }

                R.id.games -> {
                    findNavController().navigate(R.id.action_homeFragment_to_gamesFragment)
                    closeDrawer()
                    true
                }

                R.id.races -> {
                    findNavController().navigate(R.id.action_homeFragment_to_raceFragment)
                    closeDrawer()
                    true
                }

                R.id.tolkien -> {
                    findNavController().navigate(R.id.action_homeFragment_to_tolkienFragment)
                    closeDrawer()
                    true
                }

                R.id.weapons -> {
                    findNavController().navigate(R.id.action_homeFragment_to_weaponsFragment)
                    closeDrawer()
                    true
                }

                else -> false
            }
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.END)
    }

}