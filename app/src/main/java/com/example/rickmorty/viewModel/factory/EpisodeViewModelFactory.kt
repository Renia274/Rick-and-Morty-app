package com.example.rickmorty.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickmorty.repository.RnMRepository
import com.example.rickmorty.viewModel.EpisodeViewModel

class EpisodeViewModelFactory(private val repository: RnMRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(repository) as T
    }
}