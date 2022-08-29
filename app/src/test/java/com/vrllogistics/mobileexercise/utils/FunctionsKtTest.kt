package com.vrllogistics.mobileexercise.utils

import com.vrllogistics.mobileexercise.dataclasses.UserData
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.*

@RunWith(JUnit4::class)
class FunctionsKtTest {

    @Test
    fun isValidJsonFromUserData() {
        var userData = UserData("morty", "1")
        val jsonData = JSONObject()
        jsonData.put("UserName", "morty")
        jsonData.put("AccountId", "1")
        assertEquals(userData.json.getString("UserName"), jsonData.getString("UserName"))
    }

    @Test
    fun isValidUserDataFromJson() {
        var userData = UserData("morty", "1")
        val jsonData = JSONObject()
        jsonData.put("UserName", "morty")
        jsonData.put("AccountId", "1")
        assertEquals(userData.userName, jsonData.userData.userName)
    }

    @Test
    fun isValidUserDataFromJson1() {
        var userData = UserData("morty", "1")
        val jsonData = JSONObject()
        jsonData.put("UserName", "morty")
        jsonData.put("AccountId", "1")
        var equals = userData == jsonData.userData
        assertEquals(true, equals)
    }

    @Test
    fun isValidJsonFromUserData1() {
        var userData = UserData("morty", "1")
        var json = JSONObject()
        json.put("UserName", "morty")
        json.put("AccountId", "1")
        var eq = json.toString() == userData.json.toString()
        assertEquals(true, eq)
    }
}