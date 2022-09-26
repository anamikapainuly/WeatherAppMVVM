package com.anupras.weatherappsample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.room.withTransaction
import com.anupras.weatherappsample.api.ApiService
import com.anupras.weatherappsample.db.WeatherDao
import com.anupras.weatherappsample.db.WeatherDatabase
import com.anupras.weatherappsample.model.Data
import com.anupras.weatherappsample.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by anamika on 16,September,2022
 */
class WeatherRepo  @Inject constructor(
    private val apiService: ApiService,
    private val db: WeatherDatabase,
    private val dao: WeatherDao
) {

    fun getWeatherDetails(id:Int) : LiveData<Data> {
        return dao.getWeatherById(id)
    }

    fun getWeatherByTemp(country: String) : LiveData<List<Data>> {
        return if(country=="")
            dao.getAllWeatherByTemp()
        else
            dao.getAllWeatherByTemp(country)
    }

    fun getWeatherByCountry() : LiveData<List<Data>> {
        return dao.getAllWeatherByCountry()
    }

    fun getWeatherByLastUpdated(country: String) : LiveData<List<Data>> {
        return if(country=="")
            dao.getAllWeatherByLastUpdated()
        else
            dao.getAllWeatherByLastUpdated(country)
    }

    fun getWeatherList(country: String) = networkBoundResource(
        query = {
            Log.d("Check--","Check1")
            if (country == ""){
            dao.getAllWeatherByCity()}
            else dao.getAllWeatherByCity(country)
        },
        fetch = {
            Log.d("Check--","Check2")
            apiService.getWeather()
        },
        saveFetchResult = { weatherList ->
            Log.d("Check--","Check3")
            db.withTransaction {
                dao.deleteAllWeather()
                dao.insertWeather(weatherList.dataList)
            }
        }
    )

}