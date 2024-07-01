package com.example.rickmorty.activities

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rickmorty.R
import com.example.rickmorty.adapters.CharacterAdapter
import com.example.rickmorty.api.NetworkLayer
import com.example.rickmorty.api.services.RnMService
import com.example.rickmorty.data.CharacterInfo
import com.example.rickmorty.databinding.ActivityEpisodeBinding
import com.example.rickmorty.repository.RnMRepository
import com.example.rickmorty.viewModel.EpisodeViewModel
import com.example.rickmorty.viewModel.factory.EpisodeViewModelFactory

class EpisodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEpisodeBinding
    private lateinit var episodeViewModel: EpisodeViewModel
    private var isInitial = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val episodeId = intent.getIntExtra("id", 1)

        val rnmService = NetworkLayer.getInstance().create(RnMService::class.java)
        val rnmRepository = RnMRepository(rnmService)

        episodeViewModel = ViewModelProvider(this, EpisodeViewModelFactory(rnmRepository))
            .get(EpisodeViewModel::class.java)

        setupViews()
        observeViewModel(episodeId)
        setupListeners()
    }

    private fun setupViews() {
        binding.characterRV.layoutManager = LinearLayoutManager(this)

        binding.genderSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            episodeViewModel.genders
        )
    }

    private fun observeViewModel(episodeId: Int) {
        episodeViewModel.fetchEpisodeData(episodeId)
        episodeViewModel.episodeData.observe(this) { episodeInfo ->
            binding.episodeNumber.text = episodeInfo.episode
            binding.episodeTitle.text = episodeInfo.name
            binding.releaseDate.text = episodeInfo.airDate
            binding.characterCount.text = episodeInfo.characters.size.toString()
            episodeViewModel.charactersOfEpisode.addAll(episodeInfo.characters)
            episodeViewModel.characterUrlLiveData.value = episodeInfo.characters
        }

        episodeViewModel.characterLiveData.observe(this) { characterList ->
            if (characterList.results.isEmpty()) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
            }
            binding.characterRV.adapter = CharacterAdapter(characterList.results) { character ->
                showCharacterDetails(character)
            }
            binding.progressBar2.visibility = View.GONE
        }

        episodeViewModel.characterUrlLiveData.observe(this) {
            episodeViewModel.filterCharacters(
                binding.genderSpinner.selectedItem.toString(),
                binding.searchText.text.toString()
            )
        }
    }

    private fun setupListeners() {
        binding.searchText.setOnClickListener {
            binding.searchText.text?.clear()
        }

        binding.backButton.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
        }

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!isInitial) {
                    episodeViewModel.filterCharacters(
                        p0!!.getItemAtPosition(p2).toString(),
                        binding.searchText.text.toString()
                    )
                    binding.progressBar2.visibility = View.VISIBLE
                } else {
                    isInitial = false
                }
                binding.searchText.text?.clear()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.progressBar2.visibility = View.VISIBLE
                episodeViewModel.filterCharacters(
                    binding.genderSpinner.selectedItem.toString(),
                    binding.searchText.text.toString()
                )
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun showCharacterDetails(character: CharacterInfo) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.character_details_popup)

        val characterNameTextView = dialog.findViewById<TextView>(R.id.charName)
        val characterOriginTextView = dialog.findViewById<TextView>(R.id.charOrigin)
        val characterSpeciesTextView = dialog.findViewById<TextView>(R.id.charSpecies)
        val characterLocationTextView = dialog.findViewById<TextView>(R.id.charLocation)
        val characterImageView = dialog.findViewById<ImageView>(R.id.charImage)

        characterNameTextView.text = character.name
        characterOriginTextView.text = "Origin: ${character.origin?.name ?: "Unknown"}"
        characterSpeciesTextView.text = "Species: ${character.species}"
        characterLocationTextView.text = "Location: ${character.location?.name ?: "Unknown"}"
        Glide.with(this)
            .load(character.image)
            .into(characterImageView)

        dialog.show()
    }
}