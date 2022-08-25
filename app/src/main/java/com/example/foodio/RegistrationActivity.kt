package com.example.foodio
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    lateinit var etRegisterEmail : EditText
    lateinit var etRegisterPass : EditText
    lateinit var etConfirmPass : EditText
    private lateinit var btnSignUp : Button
    lateinit var btnLog : Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)


        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterPass = findViewById(R.id.etRegisterPass)
        etConfirmPass = findViewById(R.id.etConfirmPass)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnLog = findViewById(R.id.btnLog)


        auth = FirebaseAuth.getInstance()

        btnLog.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {


        }

    }}