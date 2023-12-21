package com.capstone.terratani.ui.login

import androidx.lifecycle.ViewModel
import com.capstone.terratani.data.remote.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    fun login(email: String, password: String) = userRepository.login(email, password)
}