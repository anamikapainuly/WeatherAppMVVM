package com.anupras.weatherappsample.viewmodel

import androidx.lifecycle.*
import com.anupras.weatherappsample.model.Data
import com.anupras.weatherappsample.repository.WeatherRepo
import com.anupras.weatherappsample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by anamika on 16,September,2022
 */
@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val repo: WeatherRepo,
    stateStateHandle: SavedStateHandle
): ViewModel() {

    var weatherList = repo.getWeatherList(stateStateHandle.get<String>("id").toString()).asLiveData()
    val weatherListTemp = repo.getWeatherByTemp(stateStateHandle.get<String>("id").toString())
    val weatherListLastUpdated = repo.getWeatherByLastUpdated(stateStateHandle.get<String>("id").toString())
    val weatherByCountry = repo.getWeatherByCountry()

    fun getWeatherDetails(id: Int): LiveData<Data>{
        return repo.getWeatherDetails(id)
    }
    fun getWeatherList(country: String) {
        viewModelScope.launch {
            repo.getWeatherList(country).collect { resultState ->
                //For further development
                }
            }
    }
}