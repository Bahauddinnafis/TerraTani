package com.capstone.terratani

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.terratani.data.remote.repository.UserRepository
import com.capstone.terratani.preferences.SettingPreferences
import com.capstone.terratani.ui.fragments.profile.SettingViewModel
import com.capstone.terratani.ui.login.LoginViewModel
import com.capstone.terratani.ui.register.RegisterViewModel

class ViewModelFactory(private val context: Context, private val pref: SettingPreferences, private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(pref) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory ?= null

        @JvmStatic
        fun getInstance(context: Context, pref: SettingPreferences, userRepository: UserRepository): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(context, pref, userRepository)
            }
    }
}