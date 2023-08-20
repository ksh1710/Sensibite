package com.example.innogeeks_team_project.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object helper {
private const val BASE_URL = "https://www.openfoodfacts.org/api/v0/product/"
    fun getInstance():Retrofit{
      return Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
    }
}