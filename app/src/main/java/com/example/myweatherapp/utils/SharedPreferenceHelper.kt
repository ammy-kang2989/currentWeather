package com.example.myweatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor(@ApplicationContext val activity: Context) {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "WEATHER_APP_DATA"
    private var sharedPreferences: SharedPreferences? = null
    private var prefEditor: SharedPreferences.Editor? = null
    init {
        sharedPreferences = activity.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        prefEditor = sharedPreferences?.edit()
    }

    fun putValue(key: String, value: Any?) {
        when (value) {
            is String -> {
                prefEditor?.putString(key, value)
            }
            is Int -> {
                prefEditor?.putInt(key, value)
            }
            is Boolean -> {
                prefEditor?.putBoolean(key, value)
            }
            is Long -> {
                prefEditor?.putLong(key, value)
            }
            is Float -> {
                prefEditor?.putFloat(key, value)
            }
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
        prefEditor?.commit()
    }

    fun getValue(key: String):String? {
        return sharedPreferences?.getString(key, "")
    }

    fun getIntValue(key: String):Int? {
        return sharedPreferences?.getInt(key, 0)
    }

    fun getBool(key:String):Boolean?{
        return sharedPreferences?.getBoolean(key, false)
    }

    fun contains(key: String): Boolean? {
        return sharedPreferences?.contains(key)
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun clear(){
        prefEditor!!.clear().commit()
        prefEditor!!.apply()
    }

}