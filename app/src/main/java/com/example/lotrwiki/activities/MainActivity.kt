package com.example.lotrwiki.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.CharacterAdapter
import com.example.lotrwiki.adapters.LocationAdapter
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
        initLocations()
        initUpdate()
        initMenu()
    }

    private fun initMenu() {
        binding.ivMenuButton.setOnClickListener {
            if(binding.drawerLayout.isDrawerOpen(GravityCompat.END)){
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            } else{
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        binding.ivMenuArrowForward.setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
        }
//        binding.navView.setNavigationItemSelectedListener {
//
//        }
    }

    private fun initLocations() {
        val adapter = LocationAdapter()
        binding.rvMainLocations.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMainLocations.adapter = adapter

        viewModel.locations.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.loadLocations()
    }

    private fun initUpdate() {
        binding.ivUpdate.setOnClickListener {
            viewModel.loadRandomCharacters()
        }
    }

    private fun initCharacters() {
        val adapter = CharacterAdapter()
        binding.rvMainCharacters.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMainCharacters.adapter = adapter

        viewModel.characters.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.loadRandomCharacters()
    }

//    private fun initCharacters() {
//        viewModel.characters.observe(this, Observer {
//            binding.rvMainCharacters.layoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            binding.rvMainCharacters.adapter = CharacterAdapter(it)
//        })
//        viewModel.loadRandomCharacters()
//    }


//    private fun loadAllCharacters() {
//        binding.tvSeeAll.setOnClickListener {
//            viewModel.characters.observe(this, Observer {
//                binding.rvMain.layoutManager = GridLayoutManager(this, 2)
//                binding.rvMain.adapter = CharacterAdapter(it)
//            })
//            viewModel.loadCharacters()
//            binding.tvSeeAll.visibility = View.GONE
//        }
//    }
}