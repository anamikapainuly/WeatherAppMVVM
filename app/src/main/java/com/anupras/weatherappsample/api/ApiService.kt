package com.anupras.weatherappsample.api

import com.anupras.weatherappsample.model.Data
import retrofit2.http.GET

/**
 * Created by anamika on 16,September,2022
 */
interface ApiService {
    @GET("venues/weather.json")
    suspend fun getWeather(): List<Data>
}