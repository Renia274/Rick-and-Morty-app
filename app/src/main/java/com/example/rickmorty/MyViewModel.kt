package com.example.rickmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel:ViewModel(){

    private val repository = SharedRepository()
    //live data for network response
    private val _characterByIdLiveData=MutableLiveData<ApiCharacterResponse?>()
    //regular live data
    val characterLiveData:LiveData<ApiCharacterResponse?> =_characterByIdLiveData

    fun refreshCharacter(characterid:Int){
        viewModelScope.launch {
            val response=repository.getCharacterById(characterid)

            //post response to the live data
            _characterByIdLiveData.postValue(response)
        }
    }

}