package com.anupras.weatherappsample.model

import com.anupras.weatherappsample.model.Data
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("data")
    val dataList: List<Data>,
    @SerializedName("isOkay")
    var isOkay: Boolean,
    @SerializedName("ret")
    var ret: Boolean
)