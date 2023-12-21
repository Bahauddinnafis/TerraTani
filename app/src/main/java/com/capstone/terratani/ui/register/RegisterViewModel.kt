package com.capstone.terratani.ui.register

import androidx.lifecycle.ViewModel
import com.capstone.terratani.data.remote.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {
    fun register(email: String, password: String) = userRepository.register(email, password)
}