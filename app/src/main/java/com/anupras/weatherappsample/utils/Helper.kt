package com.anupras.weatherappsample.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anamika on 16,September,2022
 */
class Helper {
    companion object{

        fun getDateCurrentTimeZone(timestamp: Long): String? {
            try {
                val calendar: Calendar = Calendar.getInstance()
                val tz: TimeZone = TimeZone.getDefault()
                calendar.timeInMillis = timestamp * 1000
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()))
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentTimeZone: Date = calendar.getTime() as Date
                return sdf.format(currentTimeZone)
            } catch (e: Exception) {
            }
            return ""
        }
    }
}