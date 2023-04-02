package com.example.myweatherapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.models.getByName.GetByCityNameResponse
import com.example.myweatherapp.models.getByName.errorResponse.ErrorResponse
import com.example.myweatherapp.repository.MainRepository
import com.example.myweatherapp.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    val getWeatherByCityNameMutableLiveData: MutableLiveData<Resource<GetByCityNameResponse>> =
        MutableLiveData()

    fun getCurrentWeatherByName(options: HashMap<String, String>) = viewModelScope.launch {
        getWeatherInfo(options)
    }

    private suspend fun getWeatherInfo(options: HashMap<String, String>) {
        getWeatherByCityNameMutableLiveData.postValue(Resource.Loading())
        try {
            val response = mainRepository.getWeatherByCityName(options)
            if (response.isSuccessful) {
                Log.e("TAG", "getWeatherInfo: handle response here" )
                getWeatherByCityNameMutableLiveData.postValue(handleResponse(response))
            } else {
                Log.e("TAG", "getWeatherInfo: handle error response here")
                val errorResponse = parseErrorBody(response.errorBody()?.charStream()?.readText())
                Log.e("TAG", "getWeatherInfo: here is error message = $errorResponse" )
                getWeatherByCityNameMutableLiveData.postValue(Resource.Error(errorResponse))
            }

        } catch (ex: Exception) {
            when (ex) {
                is IOException -> getWeatherByCityNameMutableLiveData.postValue(Resource.Error("Network Failure"))
                else -> getWeatherByCityNameMutableLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleResponse(response: Response<GetByCityNameResponse>): Resource<GetByCityNameResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun parseErrorBody(readText: String?): String {
        if (!readText.isNullOrEmpty()) {
            val result = Gson().fromJson(readText, ErrorResponse::class.java)
            return result.message
        }
        return ""
    }

}