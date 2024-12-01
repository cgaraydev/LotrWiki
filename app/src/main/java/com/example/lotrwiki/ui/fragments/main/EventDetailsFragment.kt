package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.lotrwiki.databinding.FragmentEventDetailsBinding
import com.example.lotrwiki.viewmodel.MainViewModel

class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: EventDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetails()
        initBackButton()

    }

    private fun initDetails() {
        binding.pbEventDetails.visibility = View.VISIBLE
        val eventId = args.eventId
        viewModel.clearEventDetails()
        viewModel.getEventsById(eventId)
        viewModel.eventDetails.observe(viewLifecycleOwner) {
            with(binding) {
                if (it != null) {
                    if (!it.poster.isNullOrEmpty()) {
                        Glide.with(requireContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + it.poster)
                            .into(ivEventDetailsPoster)
                    }
                    pbEventDetails.visibility = View.GONE
                    tvEventDetailsName.text = it.name
                    tvEventCategory.text = it.category
                    tvEventDate.setHtmlText(it.date)
                    tvEventDetailsHistory.setHtmlText(it.history)
                    tvEventDetailsEventConflict.setTextOrHide("Conflicto", it.conflict)
                    tvEventDetailsOtherNames.setTextOrHide("Otros nombres", it.otherNames)
                    tvEventDetailsLocations.setTextOrHide("Ubicaciones", it.location)
                    tvEventDetailsOutcome.setTextOrHide("Consecuencias", it.outcome)
                    tvEventDetailsInvolved.setTextOrHide("Involucrados", it.involved)
                    tvEventDetailsBattles.setTextOrHide("Batallas", it.battles)
                    tvEventDetailsEtymology.setTextOrHide("Etimologia", it.etymology)

                    tvGoodCombatants.setHtmlText(it.combatants?.good)
                    tvEvilCombatants.setHtmlText(it.combatants?.evil)
                    tvGoodCommanders.setHtmlText(it.commanders?.good)
                    tvEvilCommanders.setHtmlText(it.commanders?.evil)
                    tvGoodStrength.setHtmlText(it.strength?.good)
                    tvEvilStrength.setHtmlText(it.strength?.evil)
                    tvGoodCasualties.setHtmlText(it.casualties?.good)
                    tvEvilCasualties.setHtmlText(it.casualties?.evil)

                }
            }
        }
    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearEventDetails()
    }
}

/*
 *  OPCIONAL CHATGPT:
 *
 *  private fun initDetails() {
 *     binding.pbEventDetails.visibility = View.VISIBLE
 *     clearViews() // Limpia las vistas antes de observar nuevos datos.
 *
 *     val eventId = args.eventId
 *     viewModel.clearEventDetails() // Limpia los datos en el ViewModel.
 *     viewModel.getEventsById(eventId)
 *     observeEventDetails() // Observa los cambios después de limpiar.
 * }
 *
 * private fun clearViews() {
 *     with(binding) {
 *         ivEventDetailsPoster.setImageDrawable(null)
 *         tvEventDetailsName.text = ""
 *         tvEventCategory.text = ""
 *         tvEventDate.text = ""
 *         tvEventDetailsHistory.text = ""
 *         tvEventDetailsEventConflict.visibility = View.GONE
 *         tvEventDetailsOtherNames.visibility = View.GONE
 *         tvEventDetailsLocations.visibility = View.GONE
 *         tvEventDetailsOutcome.visibility = View.GONE
 *         tvEventDetailsCombatants.visibility = View.GONE
 *         // Repite para todas las vistas que puedan mostrar datos previos.
 *     }
 * }
        */