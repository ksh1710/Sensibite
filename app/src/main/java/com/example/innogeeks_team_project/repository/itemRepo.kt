package com.example.innogeeks_team_project.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.innogeeks_team_project.api.apiservice
import com.example.innogeeks_team_project.models.itemDetails

class itemRepo(private val apiservice: apiservice) {

    private val foodLiveData = MutableLiveData<itemDetails>()

    val fooditem: LiveData<itemDetails>
        get() = foodLiveData
    suspend fun getFoodItem(barcode:String){
        val result = apiservice.getProduct(barcode)
        if (result.body() != null){
           foodLiveData.postValue(result.body())
        }
    }
}