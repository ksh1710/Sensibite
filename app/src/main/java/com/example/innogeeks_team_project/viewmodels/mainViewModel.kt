package com.example.innogeeks_team_project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks_team_project.models.Product
import com.example.innogeeks_team_project.models.itemDetails
import com.example.innogeeks_team_project.repository.itemRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class mainViewModel(private val repository: itemRepo) : ViewModel() {
    private var barcode = MutableLiveData<String>()
    fun selectItem(Barcode: String) {
        barcode.value = Barcode
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFoodItem(barcode.toString())
        }
    }

    //    val fooditem: LiveData<itemDetails> = repository.fooditem.javaClass.newInstance()
    val fooditem: LiveData<String> = barcode


}