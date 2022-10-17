package com.example.foodio.loginUI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodio.R
import com.example.foodio.databinding.LoginScreenBinding
import com.example.foodio.databinding.PassresetScreenBinding
import com.example.foodio.databinding.RegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var tvPassReset: TextView
    private lateinit var etResetEmail: EditText
    private lateinit var tvDescribe: TextView
    private lateinit var btnSend: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: PassresetScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PassresetScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        tvPassReset = findViewById<TextView>(R.id.tvPassReset)
        etResetEmail = findViewById(R.id.etResetEmail)
        tvDescribe = findViewById(R.id.tvDescribe)
        btnSend = findViewById(R.id.btnSend)

        btnSend.setOnClickListener {
            var email = etResetEmail.text.toString()

            if (email.isBlank()) {
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "Please enter an email address",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //Sending password reset email
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "Email has been sent",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }


    }
}