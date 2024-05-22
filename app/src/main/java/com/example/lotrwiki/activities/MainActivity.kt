package com.example.lotrwiki.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.CharacterAdapter
import com.example.lotrwiki.databinding.ActivityMainBinding
import com.example.lotrwiki.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCharacters()
        configSwipe()
        loadAllCharacters()
    }

    private fun configSwipe() {
        binding.swipe.setColorSchemeResources(R.color.black, R.color.red)
        binding.swipe.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(this, R.color.green)
        )

        binding.swipe.setOnRefreshListener {
            viewModel.loadRandomCharacters()
            binding.swipe.isRefreshing = false
        }
    }

    private fun initCharacters() {
        viewModel.characters.observe(this, Observer {
            binding.rvMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvMain.adapter = CharacterAdapter(it)
        })
        viewModel.loadRandomCharacters()
    }

    private fun loadAllCharacters() {
        binding.tvSeeAll.setOnClickListener {
            viewModel.characters.observe(this, Observer {
                binding.rvMain.layoutManager = GridLayoutManager(this, 2)
                binding.rvMain.adapter = CharacterAdapter(it)
            })
            viewModel.loadCharacters()
            binding.tvSeeAll.visibility = View.GONE
        }
    }
}