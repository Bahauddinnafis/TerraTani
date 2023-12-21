package com.capstone.terratani.ui.login

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
import com.capstone.terratani.databinding.ActivityLoginBinding
import com.capstone.terratani.preferences.SettingPreferences
import com.capstone.terratani.preferences.dataStore
import com.capstone.terratani.ui.main.MainActivity
import com.capstone.terratani.ui.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var progressDialog: ProgressDialog
    lateinit var googleSignInClient: GoogleSignInClient

    var firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var viewModel: LoginViewModel

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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val apiService = ApiConfig.getApiService()
        val userRepository = UserRepository(apiService)

        val factory = ViewModelFactory.getInstance(applicationContext, pref, userRepository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loggin")
        progressDialog.setMessage("Silahkan Tunggu")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogin.setOnClickListener {
            if (binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString()
                    .isNotEmpty()
            ) {
                login()
            } else {
                Toast.makeText(this, "Silahkah isi email dan password telebih dahulu",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        binding.btnLoginGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                progressDialog.dismiss()
            }
    }

    private fun loginApi() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val result = viewModel.login(email, password)
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                result.observe(this) {
                    when (it) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }

                        is Resource.Success -> {
                            val data = it.data
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Resource.Error -> {
                            Toast.makeText(this, "Login Anda Tidak Berhasil!", LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                if (email.isNullOrEmpty()) binding.edtEmail.error = "Kolom email tidak boleh kosong"
                if (password.isNullOrEmpty()) binding.edtPassword.error = "Kolom password tidak boleh kosong"
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