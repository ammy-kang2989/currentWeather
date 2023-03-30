package com.example.myweatherapp.network

import com.example.myweatherapp.models.getByName.GetByCityNameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET()
    suspend fun getWeatherByCityName(@QueryMap options :  Map<String, String> ): Response<GetByCityNameResponse>
}