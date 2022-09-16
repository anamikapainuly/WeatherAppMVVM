package com.anupras.weatherappsample.model


import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

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
    @Embedded(prefix = "country")
    @SerializedName("_country")
    var country: Country?,
    @SerializedName("_weatherCondition")
    var weatherCondition: String?,
    @SerializedName("_weatherConditionIcon")
    var weatherConditionIcon: String?,
    @SerializedName("_weatherFeelsLike")
    var weatherFeelsLike: String?,
    @SerializedName("_weatherHumidity")
    var weatherHumidity: String?,
    @TypeConverters(Converters::class)
    @SerializedName("_weatherLastUpdated")
    var weatherLastUpdated: Long?,
    @SerializedName("_weatherTemp")
    var weatherTemp: Int?,
    @SerializedName("_weatherWind")
    var weatherWind: String?
)

object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}