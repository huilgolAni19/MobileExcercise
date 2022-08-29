package com.vrllogistics.mobileexercise.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class LoginUtilsTest {

    @Test
    fun checkInValidInputOnBothUserNameAndPassword() {
        val result = LoginUtils.validateCredentials("", "")
        assertEquals(false, result)
    }

    @Test
    fun checkInValidPassword() {
        val result = LoginUtils.validateCredentials("monty", "")
        assertEquals(false, result)
    }

    @Test
    fun checkInValidUserName() {
        val result = LoginUtils.validateCredentials("", "smith")
        assertEquals(false, result)
    }

    @Test
    fun checkValidInputs() {
        val result = LoginUtils.validateCredentials("morty", "smith")
        assertEquals(true, result)
    }


    @Test
    fun checkInValidUserNameLessThanEql2Chars() {
        val result = LoginUtils.validateCredentials("mo", "smith")
        assertEquals(false, result)
    }

    @Test
    fun checkInValidPasswordLessThanEql3Chars() {
        val result = LoginUtils.validateCredentials("morty", "sm")
        assertEquals(false, result)
    }
}