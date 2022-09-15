package com.anupras.weatherappsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anupras.weatherappsample.repository.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by anamika on 16,September,2022
 */
@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val repo: WeatherRepo
): ViewModel() {

    val weatherList = repo.getWeatherList().asLiveData()
}