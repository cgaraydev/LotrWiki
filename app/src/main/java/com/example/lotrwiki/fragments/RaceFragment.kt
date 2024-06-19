package com.example.lotrwiki.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotrwiki.R
import com.example.lotrwiki.adapters.RaceAdapter
import com.example.lotrwiki.databinding.FragmentRaceBinding
import com.example.lotrwiki.viewmodel.MainViewModel


class RaceFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentRaceBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.btnNavigate.setOnClickListener {
//            findNavController().navigate(R.id.actionRaceFragmentToRaceDetailsFragment)
//        }
        initRaces()
        initBackButton()
//        viewModel.getRaces()
    }

    private fun initBackButton() {
        binding.ivBtnBackDetails.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    private fun initRaces() {
        val adapter = RaceAdapter {
            val action = RaceFragmentDirections.actionRaceFragmentToRaceDetailsFragment(id = it)
            findNavController().navigate(action)

        }
        binding.rvRacesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRacesList.adapter = adapter
        viewModel.raceList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.getRaceList()
    }
}

//    private fun initRaces() {
//        viewModel.racesList.observe(viewLifecycleOwner) { races ->
//            val adapter = RaceAdapter(races) {
//                Log.d("RaceFragment", "Item clicked, id: $id")
//            }
////            val adapter = RaceAdapter(races) { id ->
////                Log.d("RaceFragment", "Item clicked, id: $id")
////                val action = RaceFragmentDirections.actionRaceFragmentToRaceDetailsFragment(id)
////                findNavController().navigate(action)
////                Log.d("RaceFragment", "Navigating to RaceDetailsFragment with ID: $id")
//
//
//            binding.rvRacesList.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
////        binding.rvRacesList.adapter = RaceAdapter()
//            binding.rvRacesList.adapter = adapter
//        }
//    }
//}

//    private fun initRaces() {
//        val adapter = RaceAdapter {
//            Log.d("RaceFragment", "Item clicked, id: $id")
//            val action = RaceFragmentDirections.actionRaceFragmentToRaceDetailsFragment(id = it)
//            findNavController().navigate(action)
//            Log.d("RaceFragment", "Navigating to RaceDetailsFragment with ID: $id")
//        }
//        binding.rvRacesList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvRacesList.adapter = adapter
//        viewModel.racesList.observe(viewLifecycleOwner) {
//            Log.d("RaceFragment", "Received races: $it")
//            adapter.differ.submitList(it)
//        }
//    }


//    private fun initRaces() {
//
//    }

//    private fun initRaces() {
//        val adapter = RaceAdapter(requireContext(), emptyList())
//        binding.raceListView.adapter = adapter
//        binding.raceListView.onItemClickListener =
//            AdapterView.OnItemClickListener { _, _, position, _ ->
//                val race = adapter.getItem(position) as Race
//                Log.d("RaceFragment", "Item clicked, id: ${race.id}")
//                Toast.makeText(
//                    context?.applicationContext,
//                    "${race.id} clicked",
//                    Toast.LENGTH_SHORT
//                ).show()
//                val action =
//                    RaceFragmentDirections.actionRaceFragmentToRaceDetailsFragment(id = race.id)
//                findNavController().navigate(action)
//                Log.d("RaceFragment", "Navigating to RaceDetailsFragment with ID: ${race.id}")
//            }
//
//        viewModel.racesList.observe(viewLifecycleOwner) {
//            Log.d("RaceViewModel", "Received races: $it")
//            adapter.setItems(it)
//        }
//        viewModel.getRaces()
//    }

