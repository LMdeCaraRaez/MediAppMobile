package com.example.mediapp.data.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    val token: String,
    // Debo serializar el nombre por que si no al tener _ no funciona
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class TokenRequest(
    val email: String,
    val password: String,
)