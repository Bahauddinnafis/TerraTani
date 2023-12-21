package com.capstone.terratani.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.capstone.terratani.data.remote.response.LoginResponse
import com.capstone.terratani.data.remote.response.RegisterResponse
import com.capstone.terratani.data.remote.response.Resource
import com.capstone.terratani.data.remote.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (private val apiService: ApiService){

    private val register = MediatorLiveData<Resource<RegisterResponse>>()
    private val login = MediatorLiveData<Resource<LoginResponse>>()

    fun register(email: String, password: String): LiveData<Resource<RegisterResponse>> {
        register.value = Resource.Loading()
        val client = apiService.register(
            email, password
        )
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResult = response.body()
                    if (registerResult != null) {
                        register.value = Resource.Success(registerResult)
                    } else {
                        register.value = Resource.Error(REGISTER_ERROR_MESSAGE)
                        Log.e(TAG, "Register info berisi null")
                    }
                } else {
                    register.value = Resource.Error(REGISTER_ERROR_MESSAGE)
                    Log.e(TAG, "Response tidak berhasil - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                register.value = Resource.Error(REGISTER_ERROR_MESSAGE)
                Log.e(TAG, "Response Failure - ${t.message.toString()}")
            }

        })

        return  register
    }

    fun login(email: String, password: String): LiveData<Resource<LoginResponse>> {
        login.value = Resource.Loading()
        val client = apiService.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResult = response.body()
                    if (loginResult != null) {
                        login.value = Resource.Success(loginResult)
                    } else {
                        login.value = Resource.Error(LOGIN_ERROR_MESSAGE)
                        Log.e(TAG, "Login info berisi null", )
                    }
                } else {
                    login.value = Resource.Error(LOGIN_ERROR_MESSAGE)
                    Log.e(TAG, "Response tidak berhasil - ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                login.value = Resource.Error(LOGIN_ERROR_MESSAGE)
                Log.e(TAG, "Response Failure - ${t.message.toString()}", )
            }

        })

        return login
    }

    companion object {
        private val TAG = UserRepository::class.java.simpleName
        private const val REGISTER_ERROR_MESSAGE = "Pendaftaran tidak berhasil, tolong coba sekali lagi"
        private const val LOGIN_ERROR_MESSAGE = "Login tidak berhasil, tolong coba sekali lagi"

        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(apiService: ApiService) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UserRepository(apiService)
        }.also { INSTANCE = it }
    }
}