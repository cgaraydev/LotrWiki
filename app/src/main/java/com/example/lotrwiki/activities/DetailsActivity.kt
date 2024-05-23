package com.example.lotrwiki.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            character?.let {
                binding.tvDetailsName.text = it.name
                Glide.with(this)
                    .load(it.imageUrl)
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