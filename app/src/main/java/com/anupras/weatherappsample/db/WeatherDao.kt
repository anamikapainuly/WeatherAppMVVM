package com.anupras.weatherappsample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anupras.weatherappsample.model.Data
import kotlinx.coroutines.flow.Flow

/**
 * Created by anamika on 16,September,2022
 */
@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(data: List<Data>)

    @Query("DELETE FROM weather_tb")
    suspend fun deleteAllWeather()

    @Query("SELECT * FROM weather_tb ORDER BY name ASC")
    fun getAllWeatherByCity(): Flow<List<Data>>

    @Query("SELECT * FROM weather_tb ORDER BY weatherTemp ASC")
    fun getAllWeatherByTemp(): LiveData<List<Data>>

    @Query("SELECT * FROM weather_tb ORDER BY weatherLastUpdated ASC")
    fun getAllWeatherByLastUpdated(): LiveData<List<Data>>

}