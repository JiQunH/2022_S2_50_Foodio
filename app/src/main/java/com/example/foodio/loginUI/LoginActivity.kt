package com.example.foodio.loginUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.foodio.filter.FilterActivity
import com.example.foodio.R
import com.example.foodio.databinding.LoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            finish()
        }

    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        //attempt to log the user in
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(
                this,
                "Please make sure you have entered an email and password",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    //account must be verified before user can login
                    if (!(firebaseUser!!.isEmailVerified)) {
                        Toast.makeText(
                            this,
                            "Please Verify your account before signing in",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        //log user in
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, FilterActivity::class.java)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }

}


