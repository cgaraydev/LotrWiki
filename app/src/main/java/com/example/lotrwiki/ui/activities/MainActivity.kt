package com.example.lotrwiki.ui.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.lotrwiki.NavGraphDirections
import com.example.lotrwiki.R
import com.example.lotrwiki.databinding.ActivityMainBinding
import com.example.lotrwiki.ui.fragments.main.HomeFragment


class MainActivity : AppCompatActivity(), HomeFragment.MenuClickListener {

    //    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
            window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            window.statusBarColor = Color.BLACK
        } else {
            window.decorView.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
            window.statusBarColor = Color.BLACK
        }

        initNav()
        initMenu()
        showMenuHint()
    }

    private fun initNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController = navHostFragment.navController
        try {
            navController.navigate(R.id.homeFragment)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error navigating to homeFragment: ${e.message}")
        }
    }

    private fun initMenu() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            handleNavMenu(menuItem.itemId)
            closeDrawer()
            true
        }
    }

    private fun handleNavMenu(itemId: Int) {
        val navController = findNavController(R.id.mainContainer)
        when (itemId) {
            R.id.characters -> navController.navigate(NavGraphDirections.actionGlobalCharactersFragment())
            R.id.locations -> navController.navigate(NavGraphDirections.actionGlobalLocationsFragment())
            R.id.others -> navController.navigate(NavGraphDirections.actionGlobalOthersFragment())
            R.id.movies -> navController.navigate(NavGraphDirections.actionGlobalMoviesFragment())
            R.id.tolkien -> navController.navigate(NavGraphDirections.actionGlobalTolkienFragment())
            R.id.maps -> navController.navigate(NavGraphDirections.actionGlobalMapsFragment())
            R.id.games -> navController.navigate(NavGraphDirections.actionGlobalGamesFragment())
            R.id.books -> navController.navigate(NavGraphDirections.actionGlobalBooksFragment())
            R.id.races -> navController.navigate(NavGraphDirections.actionGlobalRaceFragment())
            R.id.trivia -> {
                val intent = Intent(this, TriviaActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.END)
    }

    override fun onMenuClicked() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    private fun showMenuHint() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val hasShownMenuHint = sharedPreferences.getBoolean("has_shown_menu_hint", false)
        if (!hasShownMenuHint) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_menu_hint, null)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create()

            dialog.window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setDimAmount(0.8f)
            }
            val window = dialog.window
            window?.let {
                val gravity = Gravity.BOTTOM or Gravity.END
                it.attributes.gravity = gravity
                it.attributes.apply {
                    y = 100
                }
            }
            dialog.show()
            sharedPreferences.edit().putBoolean("has_shown_menu_hint", true).apply()
        }
    }
}

//        if (hasShownMenuHint) {
//            val builder = AlertDialog.Builder(this)
//            builder.setMessage("Puedes deslizar para ver el menú desde cualquier sección")
//                .setPositiveButton("OK") { dialog, _ ->
//                    dialog.dismiss()
//                    sharedPreferences.edit().putBoolean("has_shown_menu_hint", true).apply()
//                }
//            builder.create().show()
//        }
//        if (hasShownMenuHint) {
//            Snackbar.make(
//                binding.root,
//                "Puedes deslizar para ver el menu desde desde cualquier seccion",
//                Snackbar.LENGTH_INDEFINITE
//            ).setBackgroundTint(getColor(R.color.white)).setTextColor(getColor(R.color.black))
//                .show()
//            sharedPreferences.edit().putBoolean("has_shown_menu_hint", true).apply()
//        }
//    }
//}

//    private fun handleNavMenu(itemId: Int) {
//        val navController = findNavController(R.id.mainContainer)
//        val currentDestination = navController.currentDestination?.id
//
//        if (currentDestination != null && navController.graph.findNode(currentDestination).isStartDestination == true)
//    }


//    when (menuItem.itemId) {
//                R.id.characters -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_charactersFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.locations -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_locationsFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.movies -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_moviesFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.tolkien -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_tolkienFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.maps -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_mapsFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.weapons -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_weaponsFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.games -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_gamesFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.books -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_booksFragment)
//                    closeDrawer()
//                    true
//                }
//                R.id.races -> {
//                    findNavController(R.id.mainContainer).navigate(R.id.action_homeFragment_to_raceFragment)
//                    closeDrawer()
//                    true
//                }
//                else -> false
//
//            }
//        }
//    }


//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
//        val navController = navHostFragment.navController
//        try {
//            navController.navigate(R.id.homeFragment)
//        } catch (e: Exception) {
//            Log.e("MainActivity", "Error navigating to homeFragment: ${e.message}")
//        }


//    private fun initMenu() {
//        binding.ivMenuButton.setOnClickListener {
//            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                binding.drawerLayout.closeDrawer(GravityCompat.END)
//            } else {
//                binding.drawerLayout.openDrawer(GravityCompat.END)
//            }
//        }
//        binding.navView.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.characters -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.locations -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_locationsFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.movies -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_moviesFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.maps -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.games -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_gamesFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.races -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_raceFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.tolkien -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_tolkienFragment)
//                    closeDrawer()
//                    true
//                }
//
//                R.id.weapons -> {
//                    findNavController().navigate(R.id.action_homeFragment_to_weaponsFragment)
//                    closeDrawer()
//                    true
//                }
//
//                else -> false
//            }
//        }
//    }
//
//    private fun closeDrawer() {
//        binding.drawerLayout.closeDrawer(GravityCompat.END)
//    }

//    override fun onMenuClicked() {
//        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
//            binding.drawerLayout.closeDrawer(GravityCompat.END)
//        } else {
//            binding.drawerLayout.openDrawer(GravityCompat.END)
//        }
//    }


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

//            R.id.characters -> navController.navigate(R.id.action_homeFragment_to_charactersFragment)
//            R.id.locations -> navController.navigate(R.id.action_homeFragment_to_locationsFragment)
//            R.id.movies -> navController.navigate(R.id.action_homeFragment_to_moviesFragment)
//            R.id.tolkien -> navController.navigate(R.id.action_homeFragment_to_tolkienFragment)
//            R.id.maps -> navController.navigate(R.id.action_homeFragment_to_mapsFragment)
//            R.id.weapons -> navController.navigate(R.id.action_homeFragment_to_weaponsFragment)
//            R.id.games -> navController.navigate(R.id.action_homeFragment_to_gamesFragment)
//            R.id.books -> navController.navigate(R.id.action_homeFragment_to_booksFragment)
//            R.id.races -> navController.navigate(R.id.action_homeFragment_to_raceFragment)
//}