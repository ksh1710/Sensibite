package com.example.innogeeks_team_project.api

import com.example.innogeeks_team_project.models.itemDetails
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{barcode}.json")
     fun getProduct(@Path("barcode") barcode: String): Call<itemDetails>
}
