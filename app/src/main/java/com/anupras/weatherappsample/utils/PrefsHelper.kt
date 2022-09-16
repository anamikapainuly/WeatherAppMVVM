package com.anupras.weatherappsample.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by anamika on 16,September,2022
 */
object PrefsHelper {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private const val PREFS_NAME = "Pref_Weather"
    private const val LAST_SYNC_DATE = "last_sync_date"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setLastSyncDate(date: String?)
    {
        write(LAST_SYNC_DATE, date!!)
    }

    fun getLastSyncDate():String?
    {
        return read(LAST_SYNC_DATE, "")
    }

    private fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    private fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }


}