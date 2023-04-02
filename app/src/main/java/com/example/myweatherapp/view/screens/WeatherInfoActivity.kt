package com.example.myweatherapp.view.screens

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.example.myweatherapp.databinding.ActivityGetWeatherByCityNameBinding
import com.example.myweatherapp.models.getByName.GetByCityNameResponse
import com.example.myweatherapp.utils.Constants
import com.example.myweatherapp.utils.Resource
import com.example.myweatherapp.utils.TempUnitConverter
import com.example.myweatherapp.viewModel.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.HashMap
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity() {

    private val viewModel: WeatherInfoViewModel by viewModels()

    private lateinit var binding: ActivityGetWeatherByCityNameBinding

    var getByCityNameResponseObservableField: ObservableField<GetByCityNameResponse> =
        ObservableField()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetWeatherByCityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initObserver()
        setWeatherCardVisibility(false)
    }

    fun searchButtonClick() {
        if (binding.etCity.text != null) {
            getWeather(binding.etCity.text.toString())
        } else {

        }

    }

    private fun initObserver() {
        viewModel.getWeatherByCityNameMutableLiveData
            .observe(this) { getByCityNameResponse ->

                when (getByCityNameResponse) {
                    is Resource.Success -> {
                        setWeatherCardVisibility(true)
                        binding.weatherCard.visibility = View.VISIBLE
                        getByCityNameResponseObservableField.set(getByCityNameResponse.data)
                        binding.temp.text = getTempSymbol(
                            getByCityNameResponseObservableField.get()!!.main.temp
                        )
                    }

                    is Resource.Loading -> {
                        setWeatherCardVisibility(false)
                    }

                    is Resource.Error -> {
                        setWeatherCardVisibility(false)
                    }

                    else -> {

                    }
                }


            }

    }


    private fun getWeather(cityName: String) {
        val map = HashMap<String, String>().apply {
            this["q"] = cityName
            this["appid"] = Constants.KEY
        }
        viewModel.getCurrentWeatherByName(map)
    }

    private fun setWeatherCardVisibility(isVisible: Boolean) {
        if (isVisible)
            binding.weatherCard.visibility = View.VISIBLE
        else
            binding.weatherCard.visibility = View.GONE
    }

    private fun getTempSymbol(temp: Double): String {
        return TempUnitConverter.convertToCelsius(temp.roundToInt().toString())
            .toString()
    }
}


