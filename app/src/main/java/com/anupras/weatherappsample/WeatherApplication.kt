package com.anupras.weatherappsample

import android.app.Application
import com.anupras.weatherappsample.utils.PrefsHelper
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by anamika on 16,September,2022
 */

@HiltAndroidApp
class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(this)
    }

}