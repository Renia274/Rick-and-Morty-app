package com.example.rickmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.EpisodeInfo
import com.example.rickmorty.repository.RnMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RnMRepository): ViewModel() {

    val totalEpisodes = 41
    val PAGING_COUNT = 10

    var allEpisodesLivedata = MutableLiveData<List<EpisodeInfo>>()
    var allEpisodes = mutableListOf<EpisodeInfo>()
    var episodeBySearch = MutableLiveData<List<EpisodeInfo>>()


    fun getEpisodeBySearch(p0: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getEpisodeBySearch(p0)
            if(result.isSuccessful){
                val data = result.body()
                episodeBySearch.postValue(data!!.results)
            }

        }
    }

    fun fetchData(startEpisode: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val range = (startEpisode..startEpisode + PAGING_COUNT).toList()
            val fetchedEpisodesResult = repository.fetchEpisodes(range)

            if(fetchedEpisodesResult.isSuccessful) {
                allEpisodesLivedata.postValue(fetchedEpisodesResult.body())
            }
        }
    }
}