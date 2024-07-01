package com.example.rickmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.CharacterInfo
import com.example.rickmorty.data.CharacterList
import com.example.rickmorty.data.EpisodeInfo
import com.example.rickmorty.data.Info
import com.example.rickmorty.repository.RnMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch





class EpisodeViewModel(private val repository: RnMRepository) : ViewModel() {
    var characterLiveData = MutableLiveData<CharacterList>()
    var episodeData = MutableLiveData<EpisodeInfo>()
    val genders = listOf("-", "Female", "Male")
    val charactersOfEpisode = mutableListOf<String>()
    val characterUrlLiveData = MutableLiveData<List<String>>()
    var filteredCharacter = mutableListOf<CharacterInfo>()
    val characterLocationLiveData = MutableLiveData<String>()

    fun fetchCharacterLocation(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getCharacterLocation(characterId)
            if (result.isSuccessful) {
                val location = result.body()?.name ?: "Unknown"
                characterLocationLiveData.postValue(location)
            } else {
                characterLocationLiveData.postValue("Unknown")
            }
        }
    }

    fun fetchEpisodeData(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getSingleEpisode(episodeId)
            if (result.isSuccessful) {
                episodeData.postValue(result.body())
            }
        }
    }

    fun filterCharacters(selectedItem: String, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedItem == "-") {
                filterCharacterByName(text)
            } else {
                filterCharacterByGenderAndName(selectedItem, text)
            }
        }
    }
    private fun filterCharacterByName(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filteredCharacter.clear()
            for (url in charactersOfEpisode) {
                val id = url.split("/").last().toInt()
                val result = repository.getCharacter(id)
                if (result.isSuccessful) {
                    val character = result.body()!!
                    // Check if the typed text is contained within the character's name (case insensitive)
                    if (character.name!!.contains(text, ignoreCase = true)) {
                        filteredCharacter.add(character)
                    }
                }
            }
            filteredCharacter.sortBy { it.name }
            characterLiveData.postValue(CharacterList(Info(0, "", 0, ""), filteredCharacter))
        }
    }
    private fun filterCharacterByGenderAndName(gender: String, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filteredCharacter.clear()
            for (url in charactersOfEpisode) {
                val id = url.split("/").last().toInt()
                val result = repository.getCharacter(id)
                if (result.isSuccessful) {
                    val character = result.body()!!
                    if (character.gender == gender && character.name!!.contains(text, ignoreCase = true)) {
                        filteredCharacter.add(character)
                    }
                }
            }
            filteredCharacter.sortBy { it.name }
            characterLiveData.postValue(CharacterList(Info(0, "", 0, ""), filteredCharacter))
        }
    }
}