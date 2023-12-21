package com.capstone.terratani.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val username: String? = null
)
