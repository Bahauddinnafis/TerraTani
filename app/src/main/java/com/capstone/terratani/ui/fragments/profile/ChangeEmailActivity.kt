package com.capstone.terratani.ui.fragments.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityChangeEmailBinding
import com.google.firebase.auth.FirebaseAuth

class ChangeEmailActivity : AppCompatActivity() {
    lateinit var newEmail: EditText

    lateinit var binding : ActivityChangeEmailBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnChangeEmail.setOnClickListener {
            val user = firebaseAuth.currentUser
            val email = binding.edtChangeEmail.text.toString()
            if (checkEmailField()) {
                @Suppress("DEPRECATION")
                user!!.updateEmail(email)
                    .addOnCompleteListener{
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Email berhasil diupdate!", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("error", it.exception.toString())
                        }
                    }
            }
        }

    }

    private fun checkEmailField(): Boolean {
        newEmail = binding.edtChangeEmail
        val email = newEmail.text.toString()
        if (email == "") {
            binding.tilChangeEmail.error = "This is required field"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilChangeEmail.error = "Check email format"
            return false
        }
        return true
    }
}