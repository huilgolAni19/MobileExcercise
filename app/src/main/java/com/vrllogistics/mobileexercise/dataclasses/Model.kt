package com.vrllogistics.mobileexercise.dataclasses


data class MenuItem(var icon: Int, var itemName: String)

data class UserData(var userName: String, var accountId: String)

data class AccountsData(var id: String, var name: String, var balance: Int)

data class TransactionData(var id: String, var title: String, var balance: Int)


