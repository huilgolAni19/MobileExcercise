package com.vrllogistics.mobileexercise.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.vrllogistics.mobileexercise.databinding.ActivityMainBinding
import com.vrllogistics.mobileexercise.utils.SessionController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler
    private lateinit var sessionController: SessionController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        init()
    }

    private fun init() {
        sessionController = SessionController.getInstance()
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            goToNextScreen()
        }, 1000)
    }

    private fun goToNextScreen() {
        var intent  = if (sessionController.isLoggedIn) {
            Intent(this, DashboardActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        this.finish()
    }

}