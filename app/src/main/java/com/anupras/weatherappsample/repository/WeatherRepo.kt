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
    private val dao: WeatherDao,
    stateStateHandle: SavedStateHandle
) {

    fun getWeatherDetails(id:Int) : LiveData<Data> {
        return dao.getWeatherById(id)
    }

    fun getWeatherByTemp() : LiveData<List<Data>> {
        return dao.getAllWeatherByTemp()
    }

    fun getWeatherByCountry() : LiveData<List<Data>> {
        return dao.getAllWeatherByCountry()
    }

    fun getWeatherByLastUpdated() : LiveData<List<Data>> {
        return dao.getAllWeatherByLastUpdated()
    }

    fun getWeatherList(id: String) = networkBoundResource(

        query = {
            dao.getAllWeatherByCity(id)
        },
        fetch = {
            apiService.getWeather()
        },
        saveFetchResult = { weatherList ->
            db.withTransaction {
                dao.deleteAllWeather()
                dao.insertWeather(weatherList.dataList)
            }
        },
        //pass in the logic to determine if the networking call should be made
        shouldFetch = { true }
    )

}