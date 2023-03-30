package com.example.myweatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.models.getByName.GetByCityNameResponse
import com.example.myweatherapp.repository.MainRepository
import com.example.myweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GetWeatherByCityNameViewModel @Inject constructor(
   private var mainRepository: MainRepository
) : ViewModel() {

    val getWeatherByCityNameMutableLiveData: MutableLiveData<Resource<GetByCityNameResponse>> = MutableLiveData()
    var getByCityNameResponse: GetByCityNameResponse? = null

    fun getCurrentWeatherByName(options: HashMap<String, String >) = viewModelScope.launch {
        getWeatherInfo(options)
    }

    private suspend fun getWeatherInfo(options: HashMap<String, String >){
        getWeatherByCityNameMutableLiveData.postValue(Resource.Loading())
        try{
                val response = mainRepository.getWeatherByCityName(options)
            getWeatherByCityNameMutableLiveData.postValue(handleResponse(response))
        }
        catch (ex : Exception){
            when(ex){
                is IOException -> getWeatherByCityNameMutableLiveData.postValue(Resource.Error("Network Failure"))
                else -> getWeatherByCityNameMutableLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleResponse(response: Response<GetByCityNameResponse>): Resource<GetByCityNameResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (getByCityNameResponse == null)
                    getByCityNameResponse = resultResponse
                return Resource.Success(getByCityNameResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}