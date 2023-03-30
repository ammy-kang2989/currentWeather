package com.example.myweatherapp.repository

import com.example.myweatherapp.models.getByName.GetByCityNameResponse
import com.example.myweatherapp.network.ApiService
import retrofit2.Response
import retrofit2.http.QueryMap
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getWeatherByCityName(@QueryMap options: Map<String, String>) =
        apiService.getWeatherByCityName(options)

}