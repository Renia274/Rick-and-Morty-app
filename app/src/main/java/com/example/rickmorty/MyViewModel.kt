package com.example.rickmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {

    // Creation of a SharedRepository object to get data from the data source
    private val repository = SharedRepository()
    // Live data for network response
    private val characterByIdLiveData = MutableLiveData<ApiCharacterResponse?>()
    // Regular live data
    val characterLiveData: LiveData<ApiCharacterResponse?> = characterByIdLiveData

    // refreshes the character data for a given character ID
    fun refreshCharacter(characterid: Int) {
        viewModelScope.launch {

            // Get the character data for the given ID from the repository
            val response = repository.getCharacterById(characterid)

            // Post response to the live data object
            characterByIdLiveData.postValue(response)
        }
    }
}
