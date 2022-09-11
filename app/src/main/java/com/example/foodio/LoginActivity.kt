package com.example.foodio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.foodio.restaurant.RestaurantMainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)


        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)


        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
        }

    }

    fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RestaurantMainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

