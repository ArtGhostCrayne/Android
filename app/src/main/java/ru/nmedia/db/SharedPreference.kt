package ru.nmedia.db

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val prefName = "user_content"
    val sharedPref: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)


    fun setString(KEY_NAME: String, value: String) {
        with(sharedPref.edit()) {
            putString(KEY_NAME, value)
            commit()
        }
    }

    fun getString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun clear() {
        with(sharedPref.edit()) {
            clear()
            commit()
        }
    }
}