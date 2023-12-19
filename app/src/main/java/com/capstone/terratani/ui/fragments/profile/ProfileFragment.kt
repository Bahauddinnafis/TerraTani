package com.capstone.terratani.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.terratani.R
import com.capstone.terratani.databinding.FragmentProfileBinding
import com.capstone.terratani.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var username: TextView
    lateinit var logoutText: TextView
    lateinit var logoutImg: ImageView
    lateinit var profileImage: ImageView
    lateinit var settingsImage: ImageView
    lateinit var settingsText: TextView

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        username = binding.tvUsername
        profileImage = binding.ivProfileImage
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            username.text = firebaseUser.displayName
            val imageUrl = firebaseUser.photoUrl.toString()

            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.default_profile_image)
                .error(R.drawable.default_profile_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(profileImage)
        } else {
            showToast("Login Anda Gagal!")
        }

        // Logout
        logoutWithImg()
        logoutWithText()

        // Settings
        settingImage()
        settingText()

        // Change Password
        changePasswordImage()
        changePasswordText()

        // Change Email
        changeEmailImage()
        changeEmailText()

        return view
    }

    private fun logoutWithImg() {
        logoutImg = binding.ivLogout
        logoutImg.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun logoutWithText() {
        logoutText = binding.tvLogout
        logoutText.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun settingImage() {
        settingsImage = binding.ivSettings
        settingsImage.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun settingText() {
        settingsText = binding.tvSetting
        settingsText.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun changePasswordImage() {
        binding.ivGantiPassword.setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun changePasswordText() {
        binding.tvEditPassword.setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun changeEmailImage() {
        binding.ivEmail.setOnClickListener {
            val intent = Intent(activity, ChangeEmailActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun changeEmailText() {
        binding.tvChangeEmail.setOnClickListener {
            val intent = Intent(activity, ChangeEmailActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}