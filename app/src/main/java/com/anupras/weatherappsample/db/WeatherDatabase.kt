package com.anupras.weatherappsample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anupras.weatherappsample.model.Data

/**
 * Created by anamika on 16,September,2022
 */
@Database(entities = [Data::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao


}