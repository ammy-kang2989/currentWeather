package com.example.myweatherapp.view.screens

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.example.myweatherapp.databinding.ActivityGetWeatherByCityNameBinding
import com.example.myweatherapp.models.getByName.GetByCityNameResponse
import com.example.myweatherapp.utils.*
import com.example.myweatherapp.viewModel.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity() {

    private val viewModel: WeatherInfoViewModel by viewModels()

    private lateinit var binding: ActivityGetWeatherByCityNameBinding

    var getByCityNameResponseObservableField: ObservableField<GetByCityNameResponse> =
        ObservableField()

    private lateinit var prefHelper: SharedPreferenceHelper

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
        prefHelper = SharedPreferenceHelper(this)
        initObserver()
        setWeatherCardVisibility(false)
        getSavedCityName()
    }

    fun searchButtonClick() {
        if (!binding.etCity.text.toString().isNullOrEmpty()) {
            getWeather(binding.etCity.text.toString())
            hideKeyboard(binding.etCity)
        } else {
            showToastMessage("Please enter city name")
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
                        binding.temp.text = viewModel.getTempSymbol(
                            getByCityNameResponseObservableField.get()!!.main.temp
                        )
                        saveCityName(binding.etCity.text.toString())
                        hideProgress()
                    }

                    is Resource.Loading -> {
                        showProgressExtension()
                        setWeatherCardVisibility(false)

                    }

                    is Resource.Error -> {
                        hideProgress()
                        setWeatherCardVisibility(false)
                        showToastMessage(getByCityNameResponse.message!!)
                    }

                }
            }
    }


    private fun getWeather(cityName: String) {
        val map = HashMap<String, String>().apply {
            this[Constants.QUERY] = cityName
            this[Constants.API_KEY] = Constants.KEY
        }
        viewModel.getCurrentWeatherByName(map)
    }

    private fun setWeatherCardVisibility(isVisible: Boolean) {
        if (isVisible)
            binding.weatherCard.visibility = View.VISIBLE
        else
            binding.weatherCard.visibility = View.GONE
    }

    private fun saveCityName(cityName: String) {
        prefHelper.putValue(Constants.CITY_NAME_KEY, cityName)
    }

    private fun getSavedCityName() {
        val cityName = prefHelper.getValue(Constants.CITY_NAME_KEY).toString()
        if (!cityName.isNullOrEmpty()) {
            getWeather(cityName)
        }

    }
}


