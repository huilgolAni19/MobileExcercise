package com.vrllogistics.mobileexercise.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaopiz.kprogresshud.KProgressHUD
import com.vrllogistics.mobileexercise.adapters.AccountAdapter
import com.vrllogistics.mobileexercise.app.App
import com.vrllogistics.mobileexercise.databinding.ActivityAccountsBinding
import com.vrllogistics.mobileexercise.databinding.ContentAccountsBinding
import com.vrllogistics.mobileexercise.dataclasses.AccountsData
import com.vrllogistics.mobileexercise.utils.*
import org.json.JSONArray
import java.lang.Exception

class AccountsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountsBinding
    private lateinit var contentAccountsBinding: ContentAccountsBinding
    private lateinit var accountsList: ArrayList<AccountsData>
    private lateinit var accountAdapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        contentAccountsBinding = binding.contentAccounts
        showHomeButton()
        init()
        initRecycleView()
        accountAdapter.setOnItemClickListener { position ->
            var accountId: String = accountsList[position].id
            var bundle = Bundle()
            bundle.putString("AccountId", accountId)
            var intent = Intent(this, TransactionsActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)


        }
        fetchAccounts()
    }

    private fun init() {
        accountsList = ArrayList()
        accountAdapter = AccountAdapter(this, accountsList)
    }

    private fun initRecycleView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        contentAccountsBinding.recyclerViewAccounts.layoutManager = layoutManager
        contentAccountsBinding.recyclerViewAccounts.itemAnimator = DefaultItemAnimator()
        contentAccountsBinding.recyclerViewAccounts.adapter = accountAdapter
    }

    private fun fetchAccounts() {
        if(NetworkUtil.isConnected(this)) {
            var url = "${App.BASE_URL}accounts"
            log(url)

            var progress: KProgressHUD = progressBar(this, "Please Wait..", "Fetching Accounts")
            progress.show()

            var stringRequest = createRequest(url, GET) {response, error ->
                progress.dismiss()
                if(error != null) {
                    showAlert(this, error, "Error", "OK")
                    return@createRequest
                }
                log(response!!)
                try {
                    var jsonArray = JSONArray(response)
                    accountsList.addAll(jsonArray.toAccountsArray())
                    accountAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    log(e.message!!)
                }
            }
            volley(this, stringRequest)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}