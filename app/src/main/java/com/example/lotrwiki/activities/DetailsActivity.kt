package com.example.lotrwiki.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lotrwiki.databinding.ActivityDetailsBinding
import com.example.lotrwiki.viewmodel.DetailsViewModel

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private var characterId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        getCharacterId()
        setUpObservers()
        loadCharacterDetails()
        backPressed()
    }

    private fun setUpObservers() {
        viewModel.characterDetails.observe(this, Observer { character ->
            Log.d("detailsactivity", "Character details received")
            Log.d("detailsactivity", character.poster.toString())
            Log.d("detailsactivity", character.toString())
            character?.let {
                with(binding) {
//                    val race = Race.fromString(it.race)
//                    tvDetailsName.typeface =
//                        ResourcesCompat.getFont(this@DetailsActivity, race.fontResId)
                    tvDetailsName.text = it.name
                    tvDetailsRace.text = it.race
                    tvDetailsBirth.text = it.birth
                    ivMoreDetails.setOnClickListener {
                        ivMoreDetails.visibility = View.GONE
                        tvDescriptionDetails.visibility = View.VISIBLE
                        ivLessDetails.visibility = View.VISIBLE
                    }
                    ivLessDetails.setOnClickListener {
                        ivMoreDetails.visibility = View.VISIBLE
                        tvDescriptionDetails.visibility = View.GONE
                        ivLessDetails.visibility = View.GONE
                    }
                }

                binding.tvOtherNames.text = it.otherNames
                Log.d("detailsactivity", it.poster.toString())
                Glide.with(this)
                    .load(it.poster)
                    .into(binding.ivDetailsImage)
            } ?: run {
                binding.tvDetailsName.text = "Character not found"
            }
        })
    }

    private fun getCharacterId() {
        val bundle: Bundle? = intent.extras
        characterId = bundle?.getString("character_id")
    }

    private fun loadCharacterDetails() {
        characterId?.let {
            viewModel.loadCharacterDetails(it)
        }
    }

    private fun backPressed() {
        binding.ivBtnBack.setOnClickListener {
            finish()
        }
    }
}
//                when (it.race) {
//                    "Elfos" -> binding.tvDetailsName.typeface =
//                        ResourcesCompat.getFont(this, R.font.half_elven_bold)
//
//                    "Hobbits" -> binding.tvDetailsName.typeface =
//                        ResourcesCompat.getFont(this, R.font.hobbiton_brush_hand)
//
//                    "Dragones" -> binding.tvDetailsName.typeface =
//                        ResourcesCompat.getFont(this, R.font.dragonlord)
//
//                    "Hombres" -> binding.tvDetailsName.typeface =
//                        ResourcesCompat.getFont(this, R.font.middle_earth)
//
//                    else -> binding.tvDetailsName.typeface =
//                        ResourcesCompat.getFont(this, R.font.middle_earth)
//                }

