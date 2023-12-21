package com.capstone.terratani.ui.register

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import com.capstone.terratani.R
import com.capstone.terratani.ViewModelFactory
import com.capstone.terratani.data.remote.repository.UserRepository
import com.capstone.terratani.data.remote.response.Resource
import com.capstone.terratani.data.remote.service.ApiConfig
import com.capstone.terratani.databinding.ActivityRegisterBinding
import com.capstone.terratani.preferences.SettingPreferences
import com.capstone.terratani.preferences.dataStore
import com.capstone.terratani.ui.login.LoginActivity
import com.capstone.terratani.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var progressDialog: ProgressDialog
    lateinit var googleSignInClient: GoogleSignInClient

    var firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var viewModel: RegisterViewModel

    companion object {
        private const val RC_SIGN_IN = 10001
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val apiService = ApiConfig.getApiService()
        val userRepository = UserRepository(apiService)

        val factory = ViewModelFactory.getInstance(applicationContext, pref, userRepository)
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loggin")
        progressDialog.setMessage("Silahkan Tunggu")

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnRegister.setOnClickListener {
            if (binding.edtName.text.toString().isNotEmpty() && binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()) {
                // Register
                register()
                if (binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()) {
                    registerApi()
                } else {
                    Toast.makeText(this, "Silahkan isi dulu semua data anda", LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Silahkah isi dulu semua data anda", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLoginGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    private fun register() {
        val username = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = username
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        .addOnFailureListener { error2 ->
                            Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                } else {
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun registerApi() {
        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val result = viewModel.register(email, password)
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                result.observe(this) {
                    when (it) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }

                        is Resource.Success -> {
                            Toast.makeText(this, "Akun berhasil dibuat", LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }

                        is Resource.Error -> {
                            Toast.makeText(this, "Akun gagal dibuat", LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                if(email.isNullOrEmpty()) binding.edtEmail.error = "Kolom email tidak boleh kosong"
                if(password.isNullOrEmpty()) binding.edtPassword.error = "Kolom password tidak boleh kosong"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}