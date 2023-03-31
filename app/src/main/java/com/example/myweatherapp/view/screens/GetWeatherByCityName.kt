package com.example.myweatherapp.view.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myweatherapp.R
import com.example.myweatherapp.viewModel.GetWeatherByCityNameViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.myweatherapp.utils.Constants

@AndroidEntryPoint
class GetWeatherByCityName : AppCompatActivity() {

    private val viewModel: GetWeatherByCityNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_weather_by_city_name)
        getWeather()
    }

    private fun getWeather() {
        Log.e("TAG", "getWeather: ")

        val map = HashMap<String, String>().apply {
            this["q"] = "delhi"
            this["appid"] = Constants.KEY
        }


        viewModel.getWeatherByCityNameMutableLiveData.observe(this) {

            it.let {
                Log.e("TAG", "getWeather: ${it.data?.weather?.size} " )
                Log.e("TAG", "getWeather: ${it.data?.name} " )

            }

        }

        viewModel.getCurrentWeatherByName(map)

    }
}


