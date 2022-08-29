package com.vrllogistics.mobileexercise.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vrllogistics.mobileexercise.R
import com.vrllogistics.mobileexercise.adapters.MenuAdapter
import com.vrllogistics.mobileexercise.databinding.ActivityDashboardBinding
import com.vrllogistics.mobileexercise.databinding.ContentDashboardBinding
import com.vrllogistics.mobileexercise.dataclasses.MenuItem
import com.vrllogistics.mobileexercise.utils.GridSpacingItemDecoration
import com.vrllogistics.mobileexercise.utils.SessionController
import com.vrllogistics.mobileexercise.utils.dpToPx

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var contentDashboardBinding: ContentDashboardBinding
    private lateinit var mainMenu: ArrayList<MenuItem>
    private lateinit var adapter: MenuAdapter
    private lateinit var sessionController: SessionController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        init()
        preparedRecycleView()
        prepareMenu()

        adapter.setOnMenuItemClickListener { _, position ->
            when(position) {

                0 -> {
                    var intentAccount = Intent(this, AccountsActivity::class.java)
                    startActivity(intentAccount)
                }

                1 -> {

                }

                2 -> {
                    sessionController.logout()
                    var intentLogout = Intent(this, LoginActivity::class.java)
                    startActivity(intentLogout)
                    this.finish()
                }
            }
        }
    }

    private fun init() {
        contentDashboardBinding = binding.contentDashboard
        mainMenu = ArrayList()
        sessionController = SessionController.getInstance()
        adapter = MenuAdapter(this, mainMenu)
        contentDashboardBinding.textViewUserName.text = "Hello User Welcome !"
    }

    private fun prepareMenu() {
        mainMenu.add(MenuItem(R.drawable.ic_baseline_account_balance_wallet_24, "View Accounts"))
        mainMenu.add(MenuItem(R.drawable.ic_baseline_info_24, "Terms and Conditions"))
        mainMenu.add(MenuItem(R.drawable.ic_baseline_login_24, "Logout"))
        adapter.notifyDataSetChanged()
    }

    private fun preparedRecycleView() {
        val layoutManager = GridLayoutManager(this, 3)
        var itemDecorator = GridSpacingItemDecoration(3, dpToPx(this, 2), true)
        contentDashboardBinding.recyclerViewMainMenu.setHasFixedSize(true)
        contentDashboardBinding.recyclerViewMainMenu.layoutManager = layoutManager
        contentDashboardBinding.recyclerViewMainMenu.addItemDecoration(itemDecorator)
        contentDashboardBinding.recyclerViewMainMenu.adapter = adapter

    }


}