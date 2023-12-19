package com.capstone.terratani.ui.fragments.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityChangePasswordBinding
import com.capstone.terratani.ui.login.LoginActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var currentPassword: EditText
    lateinit var newPassword: EditText
    lateinit var confirmPassword: EditText

    lateinit var binding: ActivityChangePasswordBinding

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {
        currentPassword = binding.edtCurrentPassword
        newPassword = binding.edtNewPassword
        confirmPassword = binding.edtConfirmPassword

        if (currentPassword.text.isNotEmpty() && newPassword.text.isNotEmpty() && confirmPassword.text.isNotEmpty()) {

            if (newPassword.text.toString().equals(confirmPassword.text.toString())) {
                val user = firebaseAuth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currentPassword.text.toString())

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Re-Authentication Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                user!!.updatePassword(newPassword.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this, "Password telah berhasil diganti",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            firebaseAuth.signOut()
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            finish()
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "Re-Authentication Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Tolong isi semua kolom!", Toast.LENGTH_SHORT).show()
        }
    }
}