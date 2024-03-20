package com.example.rickmorty.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickmorty.repository.RnMRepository
import com.example.rickmorty.viewModel.MainViewModel

class MainViewModelFactory(private val repository: RnMRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}