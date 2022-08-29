package com.vrllogistics.mobileexercise.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.Volley

const val POST: Int = Request.Method.POST
const val GET: Int = Request.Method.GET

object NetworkUtil {

    @SuppressLint("MissingPermission")
    fun isConnected(context: AppCompatActivity): Boolean {
        val isConnected: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isConnectedNewApi(context)
        } else{
            isConnectedOld(context)
        }
        if(isConnected) {
            return true
        } else {
            showAlert(context, "Connectivity Warning", "Please Check Internet Connectivity", "OK")
        }
        return false
    }
}

object LoginUtils {
    fun validateCredentials(userName: String, password: String): Boolean {
        if(userName.isNullOrEmpty() || userName.isNullOrBlank()) return false
        if(password.isNullOrEmpty() || password.isNullOrBlank()) return false
        if(userName.length <= 2) return false
        if(password.length <= 3) return false
        return true
    }
}

