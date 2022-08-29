package com.vrllogistics.mobileexercise.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Message
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.vrllogistics.mobileexercise.dataclasses.AccountsData
import com.vrllogistics.mobileexercise.dataclasses.MenuItem
import com.vrllogistics.mobileexercise.dataclasses.TransactionData
import com.vrllogistics.mobileexercise.dataclasses.UserData
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt

@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.M)
fun isConnectedNewApi(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)!!
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@SuppressLint("MissingPermission")
@Suppress("DEPRECATION")
fun isConnectedOld(context: Context): Boolean {
    val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connManager.activeNetworkInfo!!
    return networkInfo.isConnected
}

fun dpToPx(context: Context, dp: Int): Int {
    val r = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
        .roundToInt()
}

inline fun showToast(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

inline fun showAlert(context: AppCompatActivity, message: String, title: String, positiveButton: String) = AlertDialog.Builder(context)
    .setMessage(message)
    .setTitle(title)
    .setCancelable(true)
    .setPositiveButton(positiveButton) { d, _ ->
       d.dismiss()
    }
    .create().show()

inline fun snackBar(view: View, text: String, duration: Int) = Snackbar.make(view, text, duration)

inline fun buildCustomAlert(context: AppCompatActivity, message: String, title: String): AlertDialog.Builder = AlertDialog.Builder(context)
    .setMessage(message)
    .setTitle(title)
    .setCancelable(true)

inline fun volley(context: Context, request: StringRequest): Request<String> = Volley.newRequestQueue(context).add(request)

fun createRequestWithParams(url: String, method: Int, body: HashMap<String, String>, onComplete: (response: String?, error: String?) -> Unit): StringRequest {

    val request: StringRequest =  object: StringRequest(
        method, url,
        { resp: String -> onComplete.invoke(resp, null) },
        { error: VolleyError -> onComplete.invoke(null, error.message) }
    ) {

        override fun getBodyContentType(): String {
            return "application/x-www-form-urlencoded; charset=utf-8"
        }

        override fun getParams(): MutableMap<String, String>? {
            return body
        }

        override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
            var responseString = ""
            if (response != null) {
                responseString = String(response.data)
            }
            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response))
        }
    }
    request.retryPolicy = defaultRetryPolicy()
    return request
}

inline fun defaultRetryPolicy() = DefaultRetryPolicy(100 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

inline fun progressBar(context: Context, labelText: String, detailText: String): KProgressHUD = KProgressHUD.create(context)
    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    .setLabel(labelText)
    .setDetailsLabel(detailText)

fun createRequest(url: String, method: Int, onComplete: (response: String?, error: String?) -> Unit): StringRequest = StringRequest(
    method, url, { resp: String -> onComplete.invoke(resp, null) },
    { error: VolleyError -> onComplete.invoke(null, error.message) }
)

fun AppCompatActivity.showHomeButton() {
    this.supportActionBar!!.setHomeButtonEnabled(true)
    this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}

var AppCompatActivity.Title  get() = this.supportActionBar!!.title.toString(); set(value) {
    this.supportActionBar!!.title = value
}

inline fun and(b1: Boolean, b2: Boolean) = b1 && b2


typealias OnMenuItemClickListener = (item: MenuItem, position: Int) -> Unit

typealias OnItemClickListener = (position: Int) -> Unit



/****************** Utility Function For Data Data Classes ***************************************************/

val String.json get() = JSONObject(this)

val UserData.json: JSONObject get() {
    val jsonObject = JSONObject()
    jsonObject.put("UserName", this.userName)
    jsonObject.put("AccountId", this.accountId)
    return jsonObject
}

val JSONObject.userData: UserData get() {
    return UserData(this.getString("UserName"), this.getString("AccountId"))
}


fun log(message: String) {
    Log.e("TAG", message)
}

fun JSONArray.toAccountsArray(): ArrayList<AccountsData> {
    var accountsArray: ArrayList<AccountsData> = ArrayList()
    for(i: Int in 0 until this.length()) {
        var jobj = this.getJSONObject(i)
        var id: String = jobj.getString("id")
        var name: String = jobj.getString("name")
        var balance: Int = jobj.getInt("balance")
        var accountsData = AccountsData(id, name, balance)
        accountsArray.add(accountsData)
    }
    return accountsArray
}

fun JSONArray.toTransactionsArray(): ArrayList<TransactionData> {
    var transactionsArray: ArrayList<TransactionData> = ArrayList()
    for(i: Int in 0 until this.length()) {
        var jobj = this.getJSONObject(i)
        var id: String = jobj.getString("id")
        var name: String = jobj.getString("title")
        var balance: Int = jobj.getInt("balance")
        var transactionData = TransactionData(id, name, balance)
        transactionsArray.add(transactionData)
    }
    return transactionsArray
}


