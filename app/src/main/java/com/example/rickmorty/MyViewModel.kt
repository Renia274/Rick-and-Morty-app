package com.example.rickmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel:ViewModel(){

    // Creation of a SharedRepository object to get data from the data source
    private val repository = SharedRepository()
    //live data for network response
    private val _characterByIdLiveData=MutableLiveData<ApiCharacterResponse?>()
    //regular live data
    val characterLiveData:LiveData<ApiCharacterResponse?> =_characterByIdLiveData

    // This function refreshes the character data for a given character ID
    fun refreshCharacter(characterid:Int){
        viewModelScope.launch {

            // Get the character data for the given ID from the repository
            val response=repository.getCharacterById(characterid)

            //post response to the live data object
            _characterByIdLiveData.postValue(response)
        }
    }

}