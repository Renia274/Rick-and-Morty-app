package com.example.rickmorty.activities


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.adapters.CharacterAdapter
import com.example.rickmorty.api.NetworkLayer
import com.example.rickmorty.api.services.RnMService
import com.example.rickmorty.databinding.ActivityEpisodeBinding
import com.example.rickmorty.repository.RnMRepository
import com.example.rickmorty.viewModel.EpisodeViewModel
import com.example.rickmorty.viewModel.factory.EpisodeViewModelFactory
import org.w3c.dom.Text


class EpisodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEpisodeBinding
    private var isInitial = true
    private lateinit var episodeViewModel: EpisodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val episodeId = intent.getIntExtra("id", 1)

        val rnmService = NetworkLayer.getInstance().create(RnMService::class.java)
        val rnmRepository = RnMRepository(rnmService)

        episodeViewModel = ViewModelProvider(this, EpisodeViewModelFactory(rnmRepository))[EpisodeViewModel::class.java]
        episodeViewModel.fetchEpisodeData(episodeId)
        binding.characterRV.layoutManager = LinearLayoutManager(this)

        episodeViewModel.episodeData.observe(this) { episodeInfo ->
            binding.episodeNumber.text = episodeInfo.episode
            binding.episodeTitle.text = episodeInfo.name
            binding.releaseDate.text = episodeInfo.airDate
            binding.characterCount.text = episodeInfo.characters.size.toString()
            episodeViewModel.charactersOfEpisode.addAll(episodeInfo.characters)
            episodeViewModel.characterUrlLiveData.value = episodeInfo.characters
        }

        binding.progressBar2.visibility = View.VISIBLE
        binding.backButton.setOnClickListener {
            super.onBackPressed()
        }

        binding.genderSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            episodeViewModel.genders
        )

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (isInitial) {
                    isInitial = false
                } else {
                    episodeViewModel.filterCharacters(
                        p0!!.getItemAtPosition(p2).toString(),
                        ""
                    )
                    binding.progressBar2.visibility = View.VISIBLE
                }
                binding.searchText.text.clear()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    binding.progressBar2.visibility = View.VISIBLE
                    episodeViewModel.filterCharacters(
                        binding.genderSpinner.selectedItem.toString(),
                        binding.searchText.text.toString()
                    )
                } else {
                    episodeViewModel.filterCharacters(
                        binding.genderSpinner.selectedItem.toString(),
                        binding.searchText.text.toString()
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        episodeViewModel.characterUrlLiveData.observe(this) {
            episodeViewModel.filterCharacters(
                binding.genderSpinner.selectedItem.toString(),
                binding.searchText.text.toString()
            )
        }

        episodeViewModel.characterLiveData.observe(this) { characterList ->
            if (characterList.results.isEmpty()) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
            }
            binding.characterRV.adapter = CharacterAdapter(characterList.results)
            binding.progressBar2.visibility = View.GONE
        }



        // Load the GIF using Glide into the ImageView
        val gifImageView = findViewById<ImageView>(com.example.rickmorty.R.id.animatedGifImageView)
        gifImageView.setVisibility(View.VISIBLE) // To make it visible




    }
}
