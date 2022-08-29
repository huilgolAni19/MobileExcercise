package com.vrllogistics.mobileexercise.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.kaopiz.kprogresshud.KProgressHUD
import com.vrllogistics.mobileexercise.R
import com.vrllogistics.mobileexercise.app.App
import com.vrllogistics.mobileexercise.databinding.ActivityLoginBinding
import com.vrllogistics.mobileexercise.databinding.ContentLoginBinding
import com.vrllogistics.mobileexercise.utils.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var contentLoginBinding: ContentLoginBinding
    private lateinit var sessionController: SessionController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        contentLoginBinding = binding.contentLogin
        sessionController = SessionController.getInstance()
        contentLoginBinding.buttonLogin.setOnClickListener { _ ->
            if (NetworkUtil.isConnected(this)) {
                login()
            }
        }
    }


    private fun login() {
        val userName: String = contentLoginBinding.editTextUserName.text.toString()
        val password: String = contentLoginBinding.editTextPassword.text.toString()
        if (LoginUtils.validateCredentials(userName, password)) {
            var progress: KProgressHUD = progressBar(this, "Please Wait", "Verifying Credentials...")
            progress.show()
            var url = "${App.BASE_URL}login"
            var body = HashMap<String, String>()
            body["username"] = userName
            body["password"] = password
            log("URL: $url")
            log("Params: ${body.toString()}")
            var stringRequest = createRequestWithParams(url, POST, body) { response, error ->

                progress.dismiss()
                if(error != null) {
                    log(error)
                    showAlert(this, error, "Error", "OK")
                    return@createRequestWithParams
                }

                log(response!!)
                var jsonObject: JSONObject = response!!.json
                var status:String = jsonObject.getString("Status")
                var message: String = jsonObject.getString("Message")
                if ( (status == "Success") and (message == "Login Success") ) {
                    sessionController.isLoggedIn = true
                    var userJson = jsonObject.getJSONObject("UserData")
                    sessionController.userData = userJson
                    var dashboard = Intent(this,DashboardActivity::class.java)
                    startActivity(dashboard)
                } else {
                    showAlert(this, message, "Warning", "OK")
                }

            }
            volley(this, stringRequest)
        } else {
            showAlert(this, "Invalid Credentials", "Warning", "OK")
        }
    }

}