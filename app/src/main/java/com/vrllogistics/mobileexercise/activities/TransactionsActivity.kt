package com.vrllogistics.mobileexercise.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaopiz.kprogresshud.KProgressHUD
import com.vrllogistics.mobileexercise.adapters.AccountAdapter
import com.vrllogistics.mobileexercise.adapters.TransactionsAdapter
import com.vrllogistics.mobileexercise.app.App
import com.vrllogistics.mobileexercise.databinding.ActivityTransactionsBinding
import com.vrllogistics.mobileexercise.databinding.ContentTransactionsBinding
import com.vrllogistics.mobileexercise.dataclasses.AccountsData
import com.vrllogistics.mobileexercise.dataclasses.TransactionData
import com.vrllogistics.mobileexercise.utils.*
import org.json.JSONArray
import java.lang.Exception

class TransactionsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTransactionsBinding
    private lateinit var contentTransactionsBinding: ContentTransactionsBinding
    private lateinit var transactionsList: ArrayList<TransactionData>
    private lateinit var transactionAdapter: TransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        this.supportActionBar!!.setHomeButtonEnabled(true)
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        contentTransactionsBinding = binding.contentTransactions
        var bundle = intent.extras
        var accountId: String = bundle!!.getString("AccountId")!!
        init()
        initRecycleView()
        fetchTransactionsBy(accountId)
    }

    private fun initRecycleView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        contentTransactionsBinding.recyclerViewTransactions.layoutManager = layoutManager
        contentTransactionsBinding.recyclerViewTransactions.itemAnimator = DefaultItemAnimator()
        contentTransactionsBinding.recyclerViewTransactions.adapter = transactionAdapter
    }


    private fun init() {
        transactionsList = ArrayList()
        transactionAdapter = TransactionsAdapter(this, transactionsList)
    }


    private fun fetchTransactionsBy(accountId: String) {
        if(NetworkUtil.isConnected(this)) {
            var url = "${App.BASE_URL}transactions?accountId=$accountId"
            log(url)
            var progress: KProgressHUD = progressBar(this, "Please Wait..", "Fetching Transactions of account $accountId")
            progress.show()
            var stringRequest = createRequest(url, GET) {response, error ->
                progress.dismiss()
                if(error != null) {
                    showAlert(this, error, "Error", "Ok")
                    return@createRequest
                }
                log(response!!)
               try {
                   var jsonArray = JSONArray(response)
                   this.transactionsList.addAll(jsonArray.toTransactionsArray())
                   transactionAdapter.notifyDataSetChanged()
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