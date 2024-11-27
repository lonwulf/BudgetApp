package com.lonwulf.budgetapp.data.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedPrefs(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

    fun put(key: String, value: Any) {
        val editor = sharedPreferences.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }

        editor.apply()
    }

    fun <T> get(key: String, defaultValue: T): T {
        @Suppress("UNCHECKED_CAST")
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun putAsync(key: String, value: Any, onComplete: () -> Unit = {}) {
        GlobalScope.launch(Dispatchers.IO) {
            put(key, value)
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }
}