package com.anupras.weatherappsample.di

import android.app.Application
import androidx.room.Room
import com.anupras.weatherappsample.api.ApiService
import com.anupras.weatherappsample.db.WeatherDatabase
import com.anupras.weatherappsample.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by anamika on 16,September,2022
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String):ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, WeatherDatabase::class.java,"weather_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideWeatherDao(db: WeatherDatabase) = db.weatherDao()

    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())        //To avoid using GlobalScope

}