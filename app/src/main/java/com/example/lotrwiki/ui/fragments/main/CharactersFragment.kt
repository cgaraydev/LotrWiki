package com.example.lotrwiki.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.lotrwiki.adapters.CharacterListAdapter
import com.example.lotrwiki.databinding.FragmentCharactersBinding
import com.example.lotrwiki.utils.CharacterFilter
import com.example.lotrwiki.utils.customviews.CustomCharacterCategory
import com.example.lotrwiki.utils.setUpCustomToolbar
import com.example.lotrwiki.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout

class CharactersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)
        initCustomToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            initExpandable(tvCharactersByRace, expandableLayoutRace)
            initExpandable(tvCharactersByAge, expandableLayoutAge)
            initExpandable(tvCharactersByBook, expandableLayoutBook)
            initExpandable(tvCharactersByOthers, expandableLayoutOthers)
        }

        initCharacterCategory(binding.categoryEagles, "Aguilas", CharacterFilter.ByTag("eagles"))
        initCharacterCategory(binding.categoryAinur, "Ainur", CharacterFilter.ByTag("ainur"))
        initCharacterCategory(binding.categorySpiders, "Arañas", CharacterFilter.ByTag("spiders"))
        initCharacterCategory(binding.categoryBalrogs, "Balrogs", CharacterFilter.ByTag("balrogs"))
        initCharacterCategory(binding.categoryRavens, "Cuervos", CharacterFilter.ByTag("ravens"))
        initCharacterCategory(binding.categoryDragons, "Dragones", CharacterFilter.ByTag("dragons"))
        initCharacterCategory(binding.categoryElves, "Elfos", CharacterFilter.ByTag("elves"))
        initCharacterCategory(binding.categoryDwarves, "Enanos", CharacterFilter.ByTag("dwarves"))
        initCharacterCategory(binding.categoryEnts, "Ents", CharacterFilter.ByTag("ents"))
        initCharacterCategory(binding.categoryHobbits, "Hobbits", CharacterFilter.ByTag("hobbits"))
        initCharacterCategory(binding.categoryMen, "Hombres", CharacterFilter.ByTag("men"))
        initCharacterCategory(binding.categoryOrcs, "Orcos", CharacterFilter.ByTag("orcs"))


        initCharacterCategory(binding.categoryFirstAge, "Primera Edad", CharacterFilter.ByTag("first_age"))
        initCharacterCategory(binding.categorySecondAge, "Segunda Edad", CharacterFilter.ByTag("second_age"))
        initCharacterCategory(binding.categoryThirdAge, "Tercera Edad", CharacterFilter.ByTag("third_age"))
        initCharacterCategory(binding.categoryFourthAge, "Cuarta Edad", CharacterFilter.ByTag("fourth_age"))

        initCharacterCategory(binding.categoryTheHobbit, "El Hobbit", CharacterFilter.ByTag("the_hobbit"))
        initCharacterCategory(binding.categoryTheLordOfTheRings, "El Señor de los Anillos", CharacterFilter.ByTag("lord_of_the_rings"))
        initCharacterCategory(binding.categoryTheSilmarillion, "El Silmarillion", CharacterFilter.ByTag("silmarillion"))
        initCharacterCategory(binding.categoryBookOfLostTales, "El Libro de los Cuentos Perdidos", CharacterFilter.ByTag("book_of_lost_tales"))
        initCharacterCategory(binding.categoryChildrenOfHurin, "Los Hijos de Hurin", CharacterFilter.ByTag("children_of_hurin"))

        initCharacterCategory(binding.categoryFellowshipOfTheRing, "La Comunidad del Anillo", CharacterFilter.ByTag("fellowship_of_the_ring"))
        initCharacterCategory(binding.categoryThorinCompany, "Compañía de Thorin", CharacterFilter.ByTag("thorin_company"))
        initCharacterCategory(binding.categoryEdain, "Edain", CharacterFilter.ByTag("edain"))
        initCharacterCategory(binding.categoryNoldor, "Noldor", CharacterFilter.ByTag("noldor"))
        initCharacterCategory(binding.categoryIstari, "Istari", CharacterFilter.ByTag("istari"))

    }

    private fun initCharacterCategory(
        characterCategory: CustomCharacterCategory,
        title: String,
        filter: CharacterFilter
    ) {
        characterCategory.setTitle(title)
        val characterListAdapter = CharacterListAdapter {
            val action = CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
                characterId = it
            )
            findNavController().navigate(action)
        }
        characterCategory.setAdapter(characterListAdapter)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCharacters(filter).collectLatest { pagingData ->
                    characterListAdapter.submitData(pagingData)
                }
            }
        }
        characterListAdapter.loadStateFlow.onEach { loadState ->
            characterCategory.showLoading()
            when(loadState.refresh) {
                is LoadState.Loading -> characterCategory.showLoading()
                is LoadState.NotLoading -> characterCategory.hideLoading()
                is LoadState.Error -> characterCategory.hideLoading()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initExpandable(tv: TextView, expandableLayout: ExpandableLayout) {
        tv.setOnClickListener {
            toggleExpandable(expandableLayout)
        }
    }

    private fun initCustomToolbar() {
        setUpCustomToolbar(binding.customToolbar, "Personajes", findNavController())
    }

    private fun toggleExpandable(expandableLayout: ExpandableLayout) {
        if (expandableLayout.isExpanded) {
            expandableLayout.collapse()
        } else {
            expandableLayout.expand()
        }
    }

}


//        characterCategory.setOnHeaderClickListener {
//            if (!characterCategory.isExpanded()) {
//                characterCategory.showLoading()
//                viewLifecycleOwner.lifecycleScope.launch {
//                    repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        viewModel.getCharacters(filter).collectLatest { pagingData ->
//                            characterListAdapter.submitData(pagingData)
//                            characterCategory.hideLoading()
//                        }
//                    }
//                }
//            }
//        }


//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getCharacters(filter).collectLatest { pagingData ->
//                    characterListAdapter.submitData(pagingData)
//                }
//            }
//        }


//    private fun initRecyclerView(
//        recyclerView: RecyclerView,
//        filter: CharacterFilter
//    ) {
//        val characterListAdapter = CharacterListAdapter {
//            val action = CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                characterId = it
//            )
//            findNavController().navigate(action)
//        }
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        recyclerView.adapter = characterListAdapter
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getCharacters(filter).collectLatest {pagingData ->
//                    characterListAdapter.submitData(pagingData)
//                }
//            }
//        }
//    }
//
//    private fun initExpandableClick(layout: ConstraintLayout, expandable: ExpandableLayout) {
//        layout.setOnClickListener {
//            toggleExpandableLayouts(expandable)
//        }
//    }
//
//    private fun toggleExpandableLayouts(expandableLayout: ExpandableLayout) {
//        if (expandableLayout.isExpanded) {
//            expandableLayout.collapse()
//        } else {
//            expandableLayout.expand()
//        }
//    }


//    private fun initRecyclerView(
//        title: String,
//        filter: CharacterFilter,
//        customRecyclerProgress: CustomRecyclerProgress
//        recycler: RecyclerView,
//        tag: String,


//    ) {
//        val characterListAdapter = CharacterListAdapter {
//            val action = CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                characterId = it
//            )
//            findNavController().navigate(action)
//        }
//        val recyclerView = customRecyclerProgress.getRecyclerView()
//        customRecyclerProgress.setTitle(title)
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = characterListAdapter

//        recyclerView.adapter = characterListAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getCharacters(filter).collectLatest {
//                    characterListAdapter.submitData(it)
//                }
//            }
//        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                customRecyclerProgress.observeLoadState(characterListAdapter)
//            }
//        }
//    }


//        title: String,
//        filter: CharacterFilter,
//        customRecyclerProgress: CustomRecyclerProgress
//    ) {
//
//        val recyclerView = customRecyclerProgress.getRecyclerView()
//        customRecyclerProgress.setTitle(title)
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(MarginItemDecoration(recyclerItemMargin))
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getCharacters(filter).collectLatest {
//                    adapter.submitData(it)
//                }
//            }
//        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                customRecyclerProgress.observeLoadState(adapter)
//            }
//        }

//        initCharactersLoad(
//            "la comunidad del anillo",
//            CharacterFilter.ByTag("fellowship_of_the_ring"),
//            binding.customRecyclerProgress1
//        )
//
//        initCharactersLoad(
//            "elfos",
//            CharacterFilter.ByTag("elves"),
//            binding.customRecyclerProgress2
//        )
//
//        initCharactersLoad(
//            "villanos",
//            CharacterFilter.ByFaction("mal"),
//            binding.customRecyclerProgress3
//        )
//
//        initCharactersLoad(
//            "casa de finarfin",
//            CharacterFilter.ByTag("house_of_finarfin"),
//            binding.customRecyclerProgress4
//        )
//
//        initCharactersLoad(
//            "segunda edad",
//            CharacterFilter.ByTag("second_age"),
//            binding.customRecyclerProgress5
//        )
//
//        initCharactersLoad(
//            "compañia de thorin",
//            CharacterFilter.ByTag("thorin_company"),
//            binding.customRecyclerProgress6
//        )
//        initViewAllButton()
//    }

//    private fun initViewAllButton() {
//        binding.cvViewAllCharacters.setOnClickListener {
//            findNavController().navigate(R.id.action_charactersFragment_to_allCharactersFragment)
//        }
//    }

//    private fun initCharactersLoad(
//        title: String,
//        filter: CharacterFilter,
//        customRecyclerProgress: CustomRecyclerProgress
//    ) {
//        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                    characterId = it
//                )
//            findNavController().navigate(action)
//        }
//        val recyclerView = customRecyclerProgress.getRecyclerView()
//        customRecyclerProgress.setTitle(title)
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(MarginItemDecoration(recyclerItemMargin))
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getCharacters(filter).collectLatest {
//                    adapter.submitData(it)
//                }
//            }
//        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                customRecyclerProgress.observeLoadState(adapter)
//            }
//        }

//    private fun initCustomFilterBar() {
//        binding.customFilterbar.setViewAllClickListener {
//            binding.tvFeatured.visibility = View.GONE
//
//            binding.customFilterbar.setViewAllVisibility(View.INVISIBLE)
//            updateRecyclerView(CharacterFilter.All)
//        }
//        binding.customFilterbar.setFilterClickListener {
//            showFilterDialog()
//        }
//    }

//    private fun showFilterDialog() {
//        val bindingDialog = CharacterFilterDialogBinding.inflate(layoutInflater)
//        val factions = resources.getStringArray(R.array.factions)
//        val races = resources.getStringArray(R.array.races)
//        var enteredName: String? = null
//        var selectedRace: String? = null
//        var selectedFaction: String? = null
//
//        val adapterRaces =
//            ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, races)
//        bindingDialog.tvRaceFilter.apply {
//            setAdapter(adapterRaces)
//            dropDownHeight = 500
//            dropDownVerticalOffset = 0
//        }
//
//        val adapterFactions =
//            ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, factions)
//        bindingDialog.tvFactionFilter.apply {
//            setAdapter(adapterFactions)
//        }
//
//        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog)
//            .setView(bindingDialog.root)
//            .create()
//
//        setUpRadioGroupListener(bindingDialog)
//        setUpAutocompleteTextViewLister(bindingDialog.tvRaceFilter) { selectedRace = it }
//        setUpAutocompleteTextViewLister(bindingDialog.tvFactionFilter) { selectedFaction = it }
//        bindingDialog.etName.doOnTextChanged { text, _, _, _ ->
//            enteredName = text.toString().normalize()
//        }
//        bindingDialog.btnFilterDialog.setOnClickListener {
//            binding.tvFeatured.visibility = View.GONE
//            when {
//                !enteredName.isNullOrEmpty() -> {
//                    updateRecyclerView(CharacterFilter.ByName(enteredName!!))
//                    binding.customFilterbar.setViewAllVisibility(View.VISIBLE)
//                }
//
//                selectedRace != null -> {
//                    updateRecyclerView(CharacterFilter.ByRace(selectedRace!!))
//                    binding.customFilterbar.setViewAllVisibility(View.VISIBLE)
//                }
//
//                selectedFaction != null -> {
//                    updateRecyclerView(CharacterFilter.ByFaction(selectedFaction!!))
//                    binding.customFilterbar.setViewAllVisibility(View.VISIBLE)
//                }
//            }
//            dialog.dismiss()
//        }
//
//        bindingDialog.btnExitDialog.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
//    }

//    private fun updateRecyclerView(filter: CharacterFilter) {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.getCharacters(filter).collectLatest {
//                (binding.rvCharactersFragment.adapter as? CharacterAdapter)?.submitData(it)
//            }
//        }
//    }

//    private fun setUpRadioGroupListener(bindingDialog: CharacterFilterDialogBinding) {
//        bindingDialog.radioGroupFilter.setOnCheckedChangeListener { _, checkedId ->
//            bindingDialog.etName.visibility = when (checkedId) {
//                R.id.radioByName -> View.VISIBLE
//                else -> View.GONE
//            }
//            bindingDialog.textInputLayoutRaceFilter.visibility = when (checkedId) {
//                R.id.radioByRace -> View.VISIBLE
//                else -> View.GONE
//            }
//            bindingDialog.textInputLayoutFactionFilter.visibility = when (checkedId) {
//                R.id.radioByFaction -> View.VISIBLE
//                else -> View.GONE
//            }
//        }
//    }

//    private fun setUpAutocompleteTextViewLister(
//        autoCompleteTextView: AutoCompleteTextView,
//        onItemSelected: (String) -> Unit
//    ) {
//        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
//            onItemSelected(parent.getItemAtPosition(position) as String)
//        }
//    }


//        bindingDialog.tvRaceFilter.setOnItemClickListener { parent, _, position, _ ->
//            selectedRace = parent.getItemAtPosition(position) as String
//        }
//
//        bindingDialog.tvFactionFilter.setOnItemClickListener { parent, _, position, _ ->
//            selectedFaction = parent.getItemAtPosition(position) as String
//        }

//        bindingDialog.radioGroupFilter.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.radioByName -> {
//                    bindingDialog.etName.visibility = View.VISIBLE
//                    bindingDialog.textInputLayoutRaceFilter.visibility = View.GONE
//                    bindingDialog.textInputLayoutFactionFilter.visibility = View.GONE
//                }
//
//                R.id.radioByRace -> {
//                    bindingDialog.etName.visibility = View.GONE
//                    bindingDialog.textInputLayoutRaceFilter.visibility = View.GONE
//                    bindingDialog.textInputLayoutFactionFilter.visibility = View.VISIBLE
//                }
//
//                R.id.radioByFaction -> {
//                    bindingDialog.etName.visibility = View.GONE
//                    bindingDialog.textInputLayoutRaceFilter.visibility = View.VISIBLE
//                    bindingDialog.textInputLayoutFactionFilter.visibility = View.GONE
//                }
//            }
//        }

//            if (selectedRace != null || selectedFaction != null) {
//                selectedRace?.let { updateRecyclerView(CharacterFilter.ByRace(it)) }
//                selectedFaction?.let { updateRecyclerView(CharacterFilter.ByFaction(it)) }
//            }

//        bindingDialog.btnFilterDialog.setOnClickListener {
//            updateRecyclerView(
//                when (bindingDialog.radioGroupFilter.checkedRadioButtonId) {
//                    R.id.radioByName -> CharacterFilter.ByName(bindingDialog.etName.text.toString())
//                    R.id.radioByRace -> CharacterFilter.ByRace(bindingDialog.tvRaceFilter.text.toString())
//                    R.id.radioByFaction -> CharacterFilter.ByFaction(bindingDialog.tvFactionFilter.text.toString())
//                    else -> CharacterFilter.All
//                }
//            )
//            dialog.dismiss()
//        }

//        bindingDialog.btnFilterDialog.setOnClickListener {
//            Log.d("FILTER_LOG", "Filter button clicked")
//            binding.tvFeatured.visibility = View.GONE
//            when (bindingDialog.radioGroupFilter.checkedRadioButtonId) {
//                R.id.radioByName -> {
//                    val name = bindingDialog.etName.text.toString()
//                    if (name.isEmpty()) {
//                        return@setOnClickListener
//                    } else {
//                        updateRecyclerView(CharacterFilter.ByName(name))
//                        Log.d("FILTER_LOG", "Calling updateRecyclerView with ByName($name)")
//                    }
//                }
//
//                R.id.radioByRace -> {
////                    val selectedRace = bindingDialog.tvRaceFilter.text.toString()
//                    val selectedRace = bindingDialog.tvRaceFilter.adapter.getItem(bindingDialog.tvRaceFilter.listSelection).toString()
//
//                    Log.d("FILTER_LOG", "Selected race: $selectedRace")
//                    if (selectedRace.isNotEmpty()) {
//                        updateRecyclerView(CharacterFilter.ByRace(selectedRace))
//                        Log.d("FILTER_LOG", "Calling updateRecyclerView with ByRace($selectedRace)")
//                    } else {
//                        Log.d("FILTER_LOG", "race is empty or null, skipping filter")
//                    }
////                    if (selectedRace!!.isEmpty()) {
////                        Log.d("FILTER_LOG", "race is empty, skipping filter")
////                        return@setOnClickListener
////                    } else {
////                        updateRecyclerView(CharacterFilter.ByRace(selectedRace))
////                        Log.d("FILTER_LOG", "Calling updateRecyclerView with ByRace($selectedRace)")
////                    }
//                }
//
//                R.id.radioByFaction -> {
//                    val selectedFaction = bindingDialog.tvFactionFilter.adapter.getItem(bindingDialog.tvFactionFilter.listSelection).toString()
//
////                    val selectedFaction = bindingDialog.tvFactionFilter.text.toString()
////                    val selectedFaction =
////                        factions.getOrNull(bindingDialog.tvFactionFilter.listSelection)
//                    Log.d("FILTER_LOG", "Selected race: $selectedFaction")
//
//                    if (selectedFaction.isNotEmpty()) {
//                        updateRecyclerView(CharacterFilter.ByFaction(selectedFaction))
//                        Log.d(
//                            "FILTER_LOG",
//                            "Calling updateRecyclerView with ByFaction($selectedFaction)"
//                        )
//                    } else {
//                        Log.d("FILTER_LOG", "faction is empty or null, skipping filter")
//
//                    }
//
////                    if (selectedFaction!!.isEmpty()) {
////                        Log.d("FILTER_LOG", "faction is empty, skipping filter")
////
////                        return@setOnClickListener
////                    } else {
////                        updateRecyclerView(CharacterFilter.ByFaction(selectedFaction))
////                        Log.d(
////                            "FILTER_LOG",
////                            "Calling updateRecyclerView with ByFaction($selectedFaction)"
////                        )
////                    }
//                }
//
//                else -> updateRecyclerView(CharacterFilter.All)
//            }
//            dialog.dismiss()

//        }

//            val filter = when (bindingDialog.radioGroupFilter.checkedRadioButtonId) {
//                R.id.radioByName -> CharacterFilter.ByName(bindingDialog.etName.text.toString())
//                R.id.radioByRace -> CharacterFilter.ByRace(bindingDialog.tvRaceFilter.text.toString())
//                R.id.radioByFaction -> CharacterFilter.ByFaction(bindingDialog.tvFactionFilter.text.toString())
//                else -> CharacterFilter.All
//            }
//            Log.d("FILTER_LOG", "Race filter selected: ${bindingDialog.tvRaceFilter.text}")
//            Log.d("FILTER_LOG", "Faction filter selected: ${bindingDialog.tvFactionFilter.text}")
//
//            updateRecyclerView(filter)
//            dialog.dismiss()

//        bindingDialog.btnFilterDialog.setOnClickListener {
//            binding.tvFeatured.visibility = View.GONE
//            val filter = when (bindingDialog.radioGroupFilter.checkedRadioButtonId) {
//                R.id.radioByName -> CharacterFilter.ByName(bindingDialog.etName.text.toString())
//                R.id.radioByRace -> CharacterFilter.ByRace(bindingDialog.spinnerRace.selectedItem.toString())
//                R.id.radioByFaction -> CharacterFilter.ByFaction(bindingDialog.spinnerFaction.selectedItem.toString())
//                else -> CharacterFilter.All
//            }
//            updateRecyclerView(filter)
//            dialog.dismiss()
//
//        }


//            val selectedFaction = bindingDialog.spinnerFaction.selectedItem.toString()
//            val filter = CharacterFilter.ByFaction(selectedFaction)
//            updateRecyclerView(filter)
//            dialog.dismiss()

//    private fun initCharactersFiltered() {
//        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                    characterId = it
//                )
//            findNavController().navigate(action)
//        }
//        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.rvCharactersFragment.adapter = adapter
//        viewLifecycleOwner.lifecycleScope.launch {
//            adapter.loadStateFlow.collectLatest {
//                when (it.refresh) {
//                    is LoadState.Loading -> {
//                        binding.pbCharacters.visibility = View.VISIBLE
//                    }
//
//                    is LoadState.NotLoading -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//
//                    is LoadState.Error -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//                }
//            }
//
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.characters.observe(viewLifecycleOwner) {
//                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
//                }
//                viewModel.filterCharacters("faction", "evil")
//            }
//        }
//
//
//    }

//    private fun initCharactersLoad() {
//        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
//                    characterId = it
//                )
//            findNavController().navigate(action)
//        }
//        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(),2)
//        binding.rvCharactersFragment.adapter = adapter
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            adapter.loadStateFlow.collectLatest {
//                when(it.refresh){
//                    is LoadState.Loading -> {
//                        binding.pbCharacters.visibility = View.VISIBLE
//                    }
//                    is LoadState.NotLoading -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//                    is LoadState.Error -> {
//                        binding.pbCharacters.visibility = View.GONE
//                    }
//                }
//            }
//        }
//        if (showAll){
//            viewLifecycleOwner.lifecycleScope.launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED){
//                    viewModel.charactersFlow.collectLatest {
//                        adapter.submitData(it)
//                    }
//                }
//            }
//        } else {
//            viewModel.filterCharacters("faction", "evil")
//        }
//    }


//        val adapter = CharacterAdapter {
//            val action =
//                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(characterId = it)
//            findNavController().navigate(action)
//        }
//        binding.rvCharactersFragment.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.rvCharactersFragment.adapter = adapter
//        viewModel.characters.observe(viewLifecycleOwner){
//            Log.d("CharactersFragment", "Characters observed: ${it.toString()}")
//            adapter.submitData(viewLifecycleOwner.lifecycle, it)
//        }

//        viewModel.filterCharacters("race", "Hobbits")


//    private fun updateCharacters(filteredData: List<Character>) {
//        val adapter = binding.rvCharactersFragment.adapter as CharacterAdapterFragment
//        adapter.submitData(PagingData.from(filteredData))
//    }

//    private fun showFilterDialog() {
//        val dialog = FilterDialogFragment()
//        dialog.setFilterListener {
//            currentQuery = query
//            val filteredCharacters = viewModel.filterCharacters(query)
//            updateCharacters(filteredCharacters)
//            dialog.dismiss()
//        }
//        dialog.show(parentFragmentManager, "filter_dialog")
//    }
//
//    private fun updateCharacters(filteredData: List<Character>) {
//        val adapter = binding.rvCharactersFragment.adapter as CharacterAdapterFragment
//        adapter.submitData(PagingData.from(filteredData))
//    }

//    private fun showFilterDialog() {
//        val dialog = FilterDialogFragment()
//        dialog.show(parentFragmentManager, "FilterDialogFragment")
//    }


//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.characters.collectLatest {
//                    adapter.submitData(it)
//                }
//            }
//        }