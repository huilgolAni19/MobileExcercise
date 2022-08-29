package com.vrllogistics.mobileexercise.utils

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

class SessionController  {

    private val context: Context
    private val sharedPreferences: SharedPreferences

    private constructor(context: Context) {
        this.context = context
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        private var sessionController: SessionController? = null

        private const val PREF_NAME = "MobileExercisePref"
        private const val KEY_LOGIN_STATUS = "LoginStatus"
        private const val KEY_USER_DATA = "UserData"

        fun  getInstance(context: Context): SessionController {
            if (sessionController == null) {
                sessionController = SessionController(context)
            }
            return sessionController!!
        }

        fun getInstance(): SessionController {
            return sessionController!!
        }
    }

    var isLoggedIn: Boolean get() = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false)
    set(value) = sharedPreferences.edit().putBoolean(KEY_LOGIN_STATUS, value).apply()

    var userData: JSONObject get() = JSONObject(sharedPreferences.getString(KEY_USER_DATA, "{}"))
    set(value) = sharedPreferences.edit().putString(KEY_USER_DATA, value.toString()).apply()

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}