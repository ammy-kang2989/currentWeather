package com.example.myweatherapp.view.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myweatherapp.databinding.ActivityGetWeatherByCityNameBinding
import com.example.myweatherapp.utils.Constants
import com.example.myweatherapp.utils.Resource
import com.example.myweatherapp.utils.TempUnitConverter
import com.example.myweatherapp.viewModel.GetWeatherByCityNameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetWeatherByCityName : AppCompatActivity() {

    private val viewModel: GetWeatherByCityNameViewModel by viewModels()

    lateinit var binding: ActivityGetWeatherByCityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetWeatherByCityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWeather()
        init()

    }

    private fun init() {
        binding.activity = this
        binding.viewModel = viewModel
        initObserver()
    }

    private fun initObserver() {
        viewModel.getWeatherByCityNameMutableLiveData
            .observe(this) { getByCityNameResponse ->

                when (getByCityNameResponse) {
                    is Resource.Success -> {

                        Log.e("TAG", "getWeather: Success response here")
                        binding.getWeatherResponse = getByCityNameResponse.data

                    }

                    is Resource.Loading -> {

                        Log.e("TAG", "getWeather: Loading.....")
                    }

                    is Resource.Error -> {

                        //Log.e("TAG", "getWeather: error is${getByCityNameResponse.data.}" )
                        Log.e(
                            "TAG",
                            "getWeather: error response here ${getByCityNameResponse.message}"
                        )
                    }

                    else -> {

                    }
                }


            }

    }

    private fun getWeather() {
        Log.e("TAG", "getWeather: ")

        val map = HashMap<String, String>().apply {
            this["q"] = "delhi"
            this["appid"] = Constants.KEY
        }

        viewModel.getCurrentWeatherByName(map)

    }

    fun getTempSymbol(temp : String) : String {
        return TempUnitConverter.convertToCelsius(temp).toString().also {
            "degree "

        }
    }
}


