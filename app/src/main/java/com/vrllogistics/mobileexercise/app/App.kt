package com.vrllogistics.mobileexercise.app

import android.app.Application
import com.vrllogistics.mobileexercise.utils.SessionController

class App: Application() {

    companion object {
        private const val IP_ADDRESS="192.168.1.64" //http://10.0.2.2:  192.168.1.64
        private const val PORT_NUMBER = "5555"
        const val BASE_URL = "http://$IP_ADDRESS:$PORT_NUMBER/"
        var userName: String = ""
    }
    override fun onCreate() {
        super.onCreate()
        SessionController.getInstance(this)
    }
}