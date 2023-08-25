package com.example.innogeeks_team_project.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.innogeeks_team_project.api.ApiService
import com.example.innogeeks_team_project.models.itemDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class itemRepo(private val apiservice: apiservice) {
//
//    private val foodLiveData = MutableLiveData<itemDetails>()
//
//    val fooditem: LiveData<itemDetails>
//        get() = foodLiveData
//      fun getFoodItem(barcode: String, param: Callback<itemDetails>){
//        val result = apiservice.getProduct(barcode)
//        if (result.body() != null){
//           foodLiveData.postValue(result.body())
//        }
//    }
//}
//

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class itemRepo(private val apiservice: ApiService) {

    private val foodLiveData = MutableLiveData<itemDetails>()

    val fooditem: LiveData<itemDetails>
        get() = foodLiveData

    fun getFoodItem(barcode: String) {
        apiservice.getProduct(barcode).enqueue(object : Callback<itemDetails> {
            override fun onResponse(call: Call<itemDetails>, response: Response<itemDetails>) {
                if (response.isSuccessful) {
                    val item = response.body()
                    foodLiveData.postValue(item!!)
                } else {
                    // Handle API response error here
                }
            }

            override fun onFailure(call: Call<itemDetails>, t: Throwable) {
                // Handle network error here
            }
        })
    }
}
