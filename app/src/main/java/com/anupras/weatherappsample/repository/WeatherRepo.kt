package com.anupras.weatherappsample.repository

import android.util.Log
import androidx.lifecycle.LiveData
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
    //We have data already stored in db so we can fetch from there and use query
    val sortedListByTemp: LiveData<List<Data>> = dao.getAllWeatherByTemp()
    val sortedListByLastUpdate: LiveData<List<Data>> = dao.getAllWeatherByLastUpdated()

    fun getWeatherList() = networkBoundResource(
        query = {
            dao.getAllWeatherByCity()
        },
        fetch = {
            delay(2000)
            apiService.getWeather()
        },
        saveFetchResult = {
            Log.d("Check--", it.toString())
            db.withTransaction {
                dao.deleteAllWeather()
                dao.insertWeather(it)
            }
        }
    )

}