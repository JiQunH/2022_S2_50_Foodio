package com.example.foodio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodio.databinding.PasschangeScreenBinding
import com.google.firebase.auth.FirebaseAuth

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var tvMinDescription: TextView
    private lateinit var etNewPass: EditText
    private lateinit var etConfirmNew: EditText
    private lateinit var btnUpdatePass: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: PasschangeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PasschangeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        tvMinDescription = findViewById<TextView>(R.id.tvMinDescription)
        etNewPass = findViewById(R.id.etNewPass)
        etConfirmNew = findViewById(R.id.etConfirmNew)
        btnUpdatePass = findViewById(R.id.btnUpdatePass)


        btnUpdatePass.setOnClickListener {
            val updatedPass = etNewPass.text.toString()
            val confirmNew = etConfirmNew.text.toString()
            if (updatedPass.isBlank() || confirmNew.isBlank()) {
                Toast.makeText(
                    this,
                    "Please make sure you have filled in the required fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (updatedPass != confirmNew) {
                Toast.makeText(
                    this,
                    "Please make sure passwords match",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                passwordUpdate()
            }
        }

    }

    private fun passwordUpdate() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val newPass = etNewPass.text.toString()

        firebaseUser!!.updatePassword(newPass).addOnSuccessListener {
            Toast.makeText(
                this,
                "Password Updated!",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Error Password Update Failed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
