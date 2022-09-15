package com.anupras.weatherappsample.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_tb")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("_venueID")
    var venueID: String?,
    @SerializedName("_name")
    var name: String?,
    @Embedded(prefix = "sport")
    @SerializedName("_sport")
    var sport: Sport?,
    @SerializedName("_weatherCondition")
    var weatherCondition: String?,
    @SerializedName("_weatherConditionIcon")
    var weatherConditionIcon: String?,
    @SerializedName("_weatherFeelsLike")
    var weatherFeelsLike: String?,
    @SerializedName("_weatherHumidity")
    var weatherHumidity: String?,
    @SerializedName("_weatherLastUpdated")
    var weatherLastUpdated: Int?,
    @SerializedName("_weatherTemp")
    var weatherTemp: String?,
    @SerializedName("_weatherWind")
    var weatherWind: String?
)