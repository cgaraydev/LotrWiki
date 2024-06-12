package com.example.lotrwiki.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.lotrwiki.R
import com.example.lotrwiki.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.navigationBars())
            window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            window.statusBarColor = Color.BLACK
        } else {
            window.decorView.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
            window.statusBarColor = Color.BLACK
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController = navHostFragment.navController
        try {
            navController.navigate(R.id.homeFragment)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error navigating to homeFragment: ${e.message}")
        }
    }
}
//class MainActivity : BaseActivity() {
//
//    private val viewModel = MainViewModel()
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        navController = findNavController(R.id.nav_host_fragment)
//
//        initMenu()
//        initCharactersLoad()
//    }
//
//    private fun initCharactersLoad() {
//        val adapter = MyNewAdapter()
//        binding.rvMainCharacters.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvMainCharacters.adapter = adapter
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.characters.collectLatest {
//                    adapter.submitData(it)
//                }
//            }
//        }
//    }
//
//    private fun initMenu() {
//        binding.ivMenuButton.setOnClickListener {
//            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                binding.drawerLayout.closeDrawer(GravityCompat.END)
//            } else {
//                binding.drawerLayout.openDrawer(GravityCompat.END)
//            }
//        }
//        binding.ivMenuArrowForward.setOnClickListener {
//            binding.drawerLayout.closeDrawer(GravityCompat.END)
//        }
//        binding.navView.setNavigationItemSelectedListener {
//
//            when (it.itemId) {
//                R.id.characters -> { }
//            }
//
//        }
//    }

//    private fun initLocations() {
//        val adapter = LocationAdapter()
//        binding.rvMainLocations.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvMainLocations.adapter = adapter
//
//        viewModel.locations.observe(this, Observer {
//            adapter.submitList(it)
//        })
//        viewModel.loadLocations()
//    }

//    private fun initUpdate() {
//        binding.ivUpdate.setOnClickListener {
//            viewModel.loadRandomCharacters()
//        }
//    }

//    private fun initCharacters() {
//        val adapter = CharacterAdapter()
//        binding.rvMainCharacters.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvMainCharacters.adapter = adapter
//
//        viewModel.characters.observe(this, Observer {
//            adapter.submitList(it)
//        })
//        viewModel.loadRandomCharacters()
//    }

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
//}