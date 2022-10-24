package com.example.foodio.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.foodio.R
import com.example.foodio.databinding.FragmentSettingBinding
import com.example.foodio.loginUI.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SettingFragment : Fragment() {

    // private lateinit var displayEmail: TextView
    private var _binding: FragmentSettingBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnChangePass: Button? = view?.findViewById(R.id.btnChangePass)
        btnChangePass?.setOnClickListener() {
            val intent = Intent(context, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }

        val btnSignOut: Button? = view?.findViewById(R.id.btnSignOut)

        btnSignOut?.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val displayEmail: TextView? = view?.findViewById(R.id.tvDisplayEmail)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        val user = Firebase.auth.currentUser
        user?.let {
            for (profile in it.providerData) {

                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val userEmail = user.email
                    binding.tvDisplayEmail.setText(userEmail)
                } else {
                    // No user is signed in
                }

            }

        }
        return binding.root
    }
}