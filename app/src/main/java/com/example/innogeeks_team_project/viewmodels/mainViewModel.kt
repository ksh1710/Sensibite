package com.example.innogeeks_team_project.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks_team_project.api.ApiService
import com.example.innogeeks_team_project.models.Product
import com.example.innogeeks_team_project.models.itemDetails
import com.example.innogeeks_team_project.repository.itemRepo
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductViewModel(private val apiService: ApiService) : ViewModel() {
    // LiveData for holding the product details
    private val _productDetails = MutableLiveData<itemDetails>()
    val productDetails: LiveData<itemDetails> = _productDetails

    // Function to fetch product details by barcode
    fun fetchProductDetails(barcode: String) {
        apiService.getProduct(barcode).enqueue(object : Callback<itemDetails> {
            override fun onResponse(call: Call<itemDetails>, response: Response<itemDetails>) {
                if (response.isSuccessful) {
                    val details = response.body()
                    _productDetails.postValue(details!!)
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