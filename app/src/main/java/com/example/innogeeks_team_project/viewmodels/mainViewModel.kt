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

//class mainViewModel(private val repository: itemRepo) : ViewModel() {
//    private var barcode = MutableLiveData<String>()
//    private val _fooditem = MutableLiveData<itemDetails>()
//    val fooditem: LiveData<itemDetails> = _fooditem
//
//    //    private var Strbarcode = MutableLiveData<String>()
//
//    fun selectItem(Barcode: String) {
//        barcode.value = Barcode
//    }
//
//    }

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getFoodItem(barcode.toString())
//            Log.d("idk", fooditem.toString())
//        }
//    }

//class mainViewModel(private val repository: itemRepo) : ViewModel() {
//    // LiveData to hold the barcode
//    private var barcode = MutableLiveData<String>()
//
//    // LiveData to hold the item details
//    private val _fooditem = MutableLiveData<itemDetails>()
//    val fooditem: LiveData<itemDetails> = _fooditem
//
//    // Function to update the barcode LiveData
//    fun updateBarcode(newBarcode: String) {
//        barcode.value = newBarcode
//    }
//
//    fun fetchItemDetails() {
//        val barcodeValue = barcode.value
//        if (!barcodeValue.isNullOrEmpty()) {
//            // Make an API request here using your repository or API service
//            repository.getFoodItem(barcodeValue, object : Callback<itemDetails> {
//                override fun onResponse(
//                    call: Call<itemDetails>,
//                    response: Response<itemDetails>
//                ) {
//                    if (response.isSuccessful) {
//                        val item = response.body()
//                        // Update the LiveData with the fetched item details
//                        _fooditem.postValue(item!!)
//                    } else {
//                    Log.d("idk","kuch toh")
//                    }
//                }
//
//                override fun onFailure(call: Call<itemDetails>, t: Throwable) {
//                    Log.d("idk", t.toString())
//                }
//            })
//        } else {
//            // Handle the case where the barcodeValue is empty or null
//        }
//    }
//}
//


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