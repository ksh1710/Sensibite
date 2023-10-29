package com.example.innogeeks_team_project.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks_team_project.MainActivity
import com.example.innogeeks_team_project.api.ApiService
import com.example.innogeeks_team_project.models.Product
import com.example.innogeeks_team_project.models.itemDetails
import com.example.innogeeks_team_project.repository.itemRepo
import com.example.innogeeks_team_project.scanFrag
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext


class ProductViewModel(private val apiService: ApiService) : ViewModel() {
    // LiveData for holding the product details
    private val _productDetails = MutableLiveData<itemDetails>()
    val productDetails: LiveData<itemDetails> = _productDetails

    // Function to fetch product details by barcode
    fun fetchProductDetails(barcode: String, act: scanFrag) {
        apiService.getProduct(barcode).enqueue(object : Callback<itemDetails> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<itemDetails>, response: Response<itemDetails>) {
                if (response.isSuccessful) {
                    val details = response.body()
                    Log.d("idk",details?.product.toString())
                    if (details?.product == null) {
                        Toast.makeText(act.context, "error no data available ", Toast.LENGTH_SHORT)
                            .show()



//                        val intent = Intent(act.context,MainActivity::class.java)
//                        ContextCompat.startActivity(act.requireContext(),intent,null)
//                        _productDetails.postValue()

                    }
                    _productDetails.postValue(details!!)

                } else {
//
                }
            }

            override fun onFailure(call: Call<itemDetails>, t: Throwable) {
                // Handle network error here
            }
        })
    }
}