package com.example.foodio

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodio.databinding.PasschangeScreenBinding
import com.google.firebase.auth.FirebaseAuth

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var tvMinDescription: TextView
    private lateinit var etNewPass: EditText
    private lateinit var etCurrentPass: EditText
    private lateinit var etConfirmNew: EditText
    private lateinit var btnUpdatePass: Button
    private lateinit var btnReturn: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: PasschangeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PasschangeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        tvMinDescription = findViewById<TextView>(R.id.tvMinDescription)
        etNewPass = findViewById(R.id.etNewPass)
        etCurrentPass = findViewById(R.id.etCurrentPass)
        etConfirmNew = findViewById(R.id.etConfirmNew)
        btnReturn = findViewById(R.id.btnReturn)
        btnUpdatePass = findViewById(R.id.btnUpdatePass)
    }
}