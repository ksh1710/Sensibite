package com.example.innogeeks_team_project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks_team_project.models.itemDetails
import com.example.innogeeks_team_project.repository.itemRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class mainViewModel(private val repository:itemRepo): ViewModel() {
    private val barcode = MutableLiveData<String>()
    fun selectItem(Barcode: String) {
        barcode.value = Barcode
    }
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFoodItem(barcode.value.toString())
        }
    }
    val fooditem:LiveData<itemDetails>
        get()=repository.fooditem
}