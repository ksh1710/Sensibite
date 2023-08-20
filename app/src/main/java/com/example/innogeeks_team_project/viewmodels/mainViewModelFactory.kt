package com.example.innogeeks_team_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.innogeeks_team_project.repository.itemRepo

@Suppress("UNCHECKED_CAST")
class mainViewModelFactory(private val repository:itemRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return mainViewModel(repository) as T
    }
}