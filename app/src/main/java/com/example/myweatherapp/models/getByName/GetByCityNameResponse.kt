package com.example.myweatherapp.models.getByName

data class GetByCityNameResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
){
    fun getIconLink() : String {
        return "https://openweathermap.org/img/w/$icon.png"
    }
}

