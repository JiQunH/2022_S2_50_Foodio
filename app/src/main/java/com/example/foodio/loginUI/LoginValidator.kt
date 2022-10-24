package com.example.foodio.loginUI

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

object LoginValidator {
    fun validateLogin(email: String, password: String): Boolean {
        var result = true
        if (email.isBlank() || password.isBlank()) {
            result = false
        }
        return result
    }

}