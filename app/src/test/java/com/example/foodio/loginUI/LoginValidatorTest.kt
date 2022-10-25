package com.example.foodio.loginUI


import org.junit.Test


class LoginValidatorTest {


    @Test
    fun validInput() {
        val email = "saintleeto1@gmail.com"
        val password = "open123"
        val result = LoginValidator.validateLogin(email, password)
        assert(result)
    }

    @Test
    fun invalidInput(){
        val email = ""
        val password = "open123"
        val result = LoginValidator.validateLogin(email, password)
        assert(result)
    }


}