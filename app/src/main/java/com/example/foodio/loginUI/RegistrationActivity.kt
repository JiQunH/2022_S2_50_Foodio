package com.example.foodio.loginUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.foodio.R
import com.example.foodio.databinding.RegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var etRegisterEmail: EditText
    private lateinit var etRegisterPass: EditText
    private lateinit var etConfirmPass: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnLog: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : RegisterScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterPass = findViewById(R.id.etRegisterPass)
        etConfirmPass = findViewById(R.id.etConfirmPass)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnLog = findViewById(R.id.btnLog)

        auth = FirebaseAuth.getInstance()

        //button to return to login
        btnLog.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            val email = etRegisterEmail.text.toString()
            val pass = etRegisterPass.text.toString()
            val confirmPass = etConfirmPass.text.toString()

            if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
                Toast.makeText(
                    this,
                    "Please make sure you have entered an email and password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (pass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Account has been created successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Error, Account could not be created",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            }
        }

    }
}