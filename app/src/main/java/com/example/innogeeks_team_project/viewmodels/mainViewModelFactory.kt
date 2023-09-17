package com.example.innogeeks_team_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.innogeeks_team_project.api.ApiService
import com.example.innogeeks_team_project.repository.itemRepo

@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}